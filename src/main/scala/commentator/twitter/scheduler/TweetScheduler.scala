package commentator.twitter.scheduler

import akka.actor.{ActorSystem, Props}
import commentator.actions.StatusUpdateAction
import commentator.twitter.client.TwitterClient

class TweetScheduler(system: ActorSystem) {

  def scheduleTweet(text: String) = {
    system.actorOf(Props[TwitterClient]) ! StatusUpdateAction(text)
  }
}
