package commentator.twitter.resources

import akka.actor.{Actor, ActorSystem}
import com.typesafe.scalalogging.LazyLogging
import commentator.actions.TrackFriends
import commentator.factory.{RedisInstance, TwitterInstance}

class Friends(implicit _system: ActorSystem) extends Actor with TwitterInstance with RedisInstance with LazyLogging {
  override def receive = {
    case TrackFriends(commentatorName) =>
      redis.sadd(s"commentator/$commentatorName/twitter/friends", friendIds(-1).map(id => {
        id.toString
      }):_*)
  }

  private def friendIds(cursor: Int) = {
    twitter.getFriendsIDs(cursor).getIDs
  }
}
