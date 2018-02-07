package commentator

import com.typesafe.config.Config
import twitter4j.conf.{Configuration, ConfigurationBuilder}

package object TwitterConfigFactory {

  def fromConfig(config: Config): Configuration = {
    (new ConfigurationBuilder).setOAuthConsumerKey(config.getString("consumerKey"))
      .setOAuthConsumerSecret(config.getString("consumerSecret"))
      .setOAuthAccessToken(config.getString("accessToken"))
      .setOAuthAccessTokenSecret(config.getString("accessTokenSecret")).build()
  }

}
