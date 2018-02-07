package commentator

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

object Main extends App {
  implicit val system: ActorSystem = ActorSystem("commentator-system")

  ConfigFactory.load.getObjectList("commentators").stream
    .filter(_.toConfig.getBoolean("enabled"))
    .forEach(commentator => new Commentator(commentator.toConfig, TwitterConfigFactory.fromConfig(commentator.toConfig.getConfig("twitter"))).startCommenting())

}
