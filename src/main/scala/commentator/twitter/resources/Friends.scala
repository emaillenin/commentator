package commentator.twitter.resources

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.scalalogging.LazyLogging
import commentator.actions.TrackFriends
import commentator.factory.{RedisInstance, TwitterInstance}
import twitter4j.conf.Configuration

class Friends(implicit _system: ActorSystem, conf: Configuration) extends Actor with TwitterInstance with RedisInstance with LazyLogging {
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


object Friends {
  def props()(implicit conf: Configuration, system: ActorSystem) = Props(new Friends)
}
