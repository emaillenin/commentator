package commentator

import akka.actor.ActorSystem
import commentator.twitter.scheduler.{TweetScheduler, StaticTweets}

object Main extends App {
  val system = ActorSystem("commentator-system")

  private val tweetSource = new StaticTweets(system, new TweetScheduler(system))
  tweetSource.randomTweets()
}
