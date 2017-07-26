package commentator

import akka.actor.{ActorSystem, Props}
import commentator.actions.{TrackTrendingTags, TrackFriends}
import commentator.twitter.scheduler.{StaticTweets, TweetsQueue}
import commentator.twitter.resources.{Friends, HomeTimeline}
import com.typesafe.config.ConfigFactory

object Main extends App {
  val system = ActorSystem("commentator-system")
  val config = ConfigFactory.load

  private val tweetSource = new StaticTweets(system, system.actorOf(Props(classOf[HomeTimeline], system)))
  //  system.actorOf(Props(classOf[Friends], system)) ! TrackFriends(config.getString("commentator.name"))
  system.actorOf(Props(classOf[HomeTimeline], system)) ! TrackTrendingTags(config.getString("commentator.name"))
//  tweetSource.randomTweets()
}
