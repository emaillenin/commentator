package commentator.twitter.client

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import commentator.actions.StatusUpdateAction
import commentator.factory.TwitterInstance
import twitter4j.StatusUpdate

class TwitterClient extends Actor with TwitterInstance with LazyLogging {

  override def receive: Receive = {
    case StatusUpdateAction(text) =>
      val status = twitter.updateStatus(new StatusUpdate(text))
      logger.info(s"Tweeted $text and got ID: ${status.getId}")
  }

}