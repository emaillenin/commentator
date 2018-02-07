package commentator.twitter.redis

import akka.actor.ActorSystem
import commentator.factory.RedisInstance
import commentator.twitter.resources.HomeTimeline

class Repository(implicit system: ActorSystem) extends RedisInstance {

  def trendingTags(commentatorName: String) = {
    redis.lrange(s"commentator/$commentatorName/twitter/trending_tags", 0, HomeTimeline.maxTrendingTags - 1)
  }

}
