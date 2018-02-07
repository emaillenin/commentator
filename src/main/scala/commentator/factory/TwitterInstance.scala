package commentator.factory

import twitter4j.conf.Configuration
import twitter4j.{Twitter, TwitterFactory}

trait TwitterInstance {

  val _twitter: Twitter = null

  def twitter(implicit conf: Configuration): Twitter = _twitter match {
    case null => new TwitterFactory(conf).getInstance
    case _ => _twitter
  }

}