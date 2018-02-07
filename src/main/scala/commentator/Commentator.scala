package commentator

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.Config
import commentator.actions.{SendScheduledTweet, TrackFriends, TrackTrendingTags}
import commentator.campaign.CampaignRunner
import commentator.twitter.redis.Repository
import commentator.twitter.resources.{Friends, HomeTimeline}
import commentator.twitter.scheduler.TweetsQueue
import twitter4j.conf.Configuration

class Commentator(commentatorConf: Config, twitterConf: Configuration)(implicit system: ActorSystem) {

  def startCommenting(): Unit = {
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val conf: Configuration = twitterConf
    val tweetsQueue = system.actorOf(TweetsQueue.props())

    system.actorOf(Friends.props()) ! TrackFriends(commentatorConf.getString("name"))
    system.actorOf(HomeTimeline.props()) ! TrackTrendingTags(commentatorConf.getString("name"))

    tweetsQueue ! SendScheduledTweet
    new CampaignRunner(new Repository(), tweetsQueue).runCampaign(commentatorConf.getString("comments"), commentatorConf.getString("name"))
  }
}
