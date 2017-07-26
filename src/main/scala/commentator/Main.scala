package commentator

import akka.actor.{ActorSystem, Props}
import commentator.actions.{FindTrendingTags, TrackFriends}
import commentator.twitter.scheduler.{StaticTweets, TweetsQueue}
import commentator.twitter.resources.{Friends, HomeTimeline}
import com.typesafe.config.ConfigFactory

object Main extends App {
  val system = ActorSystem("commentator-system")
  val config = ConfigFactory.load

  private val tweetSource = new StaticTweets(system, new TweetsQueue(system))
  system.actorOf(Props(classOf[Friends], system)) ! TrackFriends(config.getString("commentator.name"))
//  system.actorOf(Props(classOf[HomeTimeline], system)) ! FindTrendingTags
//  tweetSource.randomTweets()
}
