package commentator

import akka.actor.{ActorSystem, Props}
import commentator.actions.FindTrendingTags
import commentator.twitter.scheduler.{StaticTweets, TweetScheduler}
import commentator.twitter.timeline.HomeTimeline

object Main extends App {
  val system = ActorSystem("commentator-system")

  private val tweetSource = new StaticTweets(system, new TweetScheduler(system))
  system.actorOf(Props(classOf[HomeTimeline], system)) ! FindTrendingTags
  tweetSource.randomTweets()
}
