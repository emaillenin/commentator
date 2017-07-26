package commentator.twitter.scheduler

import akka.actor.{Actor, ActorSystem, Props}
import commentator.actions.{SendScheduledTweet, StatusUpdateAction}
import commentator.twitter.client.TwitterClient

import scala.collection.mutable
import scala.concurrent.duration._
import scala.util.Random

class TweetsQueue(system: ActorSystem) extends Actor {
  val scheduledTweets: mutable.Queue[String] = mutable.Queue.empty

  def scheduleTweet(tweet: String) = {
    scheduledTweets += tweet
  }

  override def receive = {
    case SendScheduledTweet =>
      // Schedule a tweet every 20 to 30 minutes
      system.actorOf(Props[TwitterClient]) ! StatusUpdateAction(scheduledTweets.dequeue)
      system.scheduler.scheduleOnce(FiniteDuration(20 + Random.nextInt(10), MINUTES), self, SendScheduledTweet)
  }

}
