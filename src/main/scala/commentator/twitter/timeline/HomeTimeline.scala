package commentator.twitter.timeline

import akka.actor.{Actor, ActorSystem}
import com.typesafe.scalalogging.LazyLogging
import commentator.actions.FindTrendingTags
import commentator.factory.TwitterInstance
import twitter4j.{Paging, ResponseList, Status}

import scala.collection.JavaConversions._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class HomeTimeline(system: ActorSystem) extends Actor with TwitterInstance with LazyLogging {
  val maxPages = 20

  override def receive = {
    case FindTrendingTags =>
      Range(1, maxPages).forEach(page => {
        val tweets: ResponseList[Status] = twitter.getHomeTimeline(new Paging(page))
        tweets.foreach(tweet => {
          tweet.getUserMentionEntities.foreach(mention => {
            logger.info(mention.getScreenName)
          })
          tweet.getHashtagEntities.foreach(hashtag => {
            logger.info(hashtag.getText)
          })
        })
      })
      system.scheduler.scheduleOnce(2 hours, self, FindTrendingTags)
  }
}
