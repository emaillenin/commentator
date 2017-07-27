package commentator.campaign

import akka.actor.{ActorRef, ActorSystem}
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging
import commentator.actions.ScheduleTweet
import commentator.twitter.redis.Repository
import commentator.twitter.scheduler.{StaticTweetsSource, TweetAssembler}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.duration._
import scala.util.Random

class CampaignRunner(repository: Repository, tweetQueue: ActorRef)(implicit system: ActorSystem, fm: ActorMaterializer) extends LazyLogging {

  def runCampaign(campaignSource: String, commentatorName: String): Unit = {
    val marketingSource = (new StaticTweetsSource).getMarketingSource(campaignSource)
    marketingSource.map(marketingSource => {
      val marketingCampaign = Random.shuffle(marketingSource.campaigns).head
      val body = Random.shuffle(marketingCampaign.body).head
      repository.trendingTags(commentatorName).map(trendingTags => {
        val tag = Random.shuffle(trendingTags).head
        val tweet = TweetAssembler.assembleTweet(body, tag.utf8String, marketingCampaign.link)

        logger.info(s"Scheduling tweet: $tweet")
        tweetQueue ! ScheduleTweet(tweet)
        system.scheduler.scheduleOnce(6 hours) {
          runCampaign(campaignSource, commentatorName)
        }
      })
    })
  }
}
