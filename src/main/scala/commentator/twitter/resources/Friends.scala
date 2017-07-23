package commentator.twitter.resources

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import commentator.actions.TrackFriends
import commentator.factory.TwitterInstance

class Friends  extends Actor with TwitterInstance with LazyLogging {
  override def receive = {
    case TrackFriends =>
      friendIds(-1).foreach(id => {
        logger.info(id.toString)
      })
  }

  private def friendIds(cursor: Int) = {
    twitter.getFriendsIDs(cursor).getIDs
  }
}
