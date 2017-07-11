package commentator.factory

import twitter4j.{Twitter, TwitterFactory}

trait TwitterInstance {
val twitter: Twitter = new TwitterFactory().getInstance
}