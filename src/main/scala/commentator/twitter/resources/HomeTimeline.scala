package commentator.twitter.resources

import akka.actor.{Actor, ActorSystem}
import com.typesafe.scalalogging.LazyLogging
import commentator.actions.TrackTrendingTags
import commentator.factory.{RedisInstance, TwitterInstance}
import twitter4j.{Paging, ResponseList, Status}

import scala.collection.JavaConversions._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class HomeTimeline(implicit system: ActorSystem) extends Actor with TwitterInstance with RedisInstance with LazyLogging {
  val maxPages = 10
  val maxTrendingTags = 10

  override def receive = {
    case TrackTrendingTags(commentatorName: String) =>
      val trendingTagsKey = s"commentator/$commentatorName/twitter/trending_tags"

      redis.lpush(trendingTagsKey, trendingTags: _*)
      redis.ltrim(trendingTagsKey, 0, maxTrendingTags - 1)
      system.scheduler.scheduleOnce(2 hours, self, TrackTrendingTags(commentatorName))
  }

  private def trendingTags = {
    tagsFromTimeline.groupBy(identity).toList.sortBy(-1 * _._2.size).map(_._1).take(maxTrendingTags)
  }

  private def tagsFromTimeline = {
    Range(1, maxPages).flatMap(page => {
      Thread.sleep(20000)
      tagsFromPage(page)
    })
  }

  private def tagsFromPage(page: Int) = {
    val tweets: ResponseList[Status] = twitter.getHomeTimeline(new Paging(page))
    tweets.flatMap(tweet => {
      tagsFromTweet(tweet)
    })
  }

  private def tagsFromTweet(tweet: Status) = {
    tweet.getHashtagEntities.map(hashtag => {
      hashtag.getText
    })
  }
}
