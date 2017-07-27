package commentator

import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer
import commentator.actions.{SendScheduledTweet, TrackFriends, TrackTrendingTags}
import commentator.twitter.scheduler.{StaticTweetsSource, TweetsQueue}
import commentator.twitter.resources.{Friends, HomeTimeline}
import com.typesafe.config.ConfigFactory
import commentator.campaign.CampaignRunner
import commentator.twitter.redis.Repository

object Main extends App {
  implicit val system = ActorSystem("commentator-system")
  implicit val materializer = ActorMaterializer()

  val config = ConfigFactory.load

  //  system.actorOf(Props(classOf[Friends], system)) ! TrackFriends(config.getString("commentator.name"))
//  system.actorOf(Props(classOf[HomeTimeline], system)) ! TrackTrendingTags(config.getString("commentator.name"))
  private val tweetsQueue = system.actorOf(Props(classOf[TweetsQueue], system))
  tweetsQueue ! SendScheduledTweet
  new CampaignRunner(new Repository(), tweetsQueue).runCampaign("https://private-8343-duggout.apiary-mock.com/commentator/comments.json", config.getString("commentator.name"))
}
