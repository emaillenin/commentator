package commentator.twitter.scheduler

import akka.actor.{Actor, ActorSystem, Props}
import commentator.actions.{ScheduleTweet, SendScheduledTweet, StatusUpdateAction}
import commentator.twitter.client.TwitterClient
import twitter4j.conf.Configuration

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.Random

class TweetsQueue(implicit conf: Configuration, system: ActorSystem) extends Actor {
  val scheduledTweets: mutable.Queue[String] = mutable.Queue.empty

  override def receive = {
    case ScheduleTweet(tweet: String) =>
      scheduledTweets += tweet

    case SendScheduledTweet =>
      // Schedule a tweet every 1 or 2 hours
      if (scheduledTweets.nonEmpty) {
        system.actorOf(Props[TwitterClient]) ! StatusUpdateAction(scheduledTweets.dequeue)
      }

      system.scheduler.scheduleOnce(FiniteDuration(60 + Random.nextInt(60), MINUTES), self, SendScheduledTweet)
  }

}

object TweetsQueue {
  def props()(implicit conf: Configuration, system: ActorSystem) = Props(new TweetsQueue)
}
