package commentator.factory

import akka.actor.ActorSystem
import redis.RedisClient

trait RedisInstance {
  def redis(implicit system: ActorSystem): RedisClient = RedisClient()(system)
}