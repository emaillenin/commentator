package commentator.twitter.scheduler

import scala.util.Random

object TweetAssembler {
  def assembleTweet(body: String, hashTag: String, link: String) = {
    val separator = Random.shuffle(List(":", "|", "=>", "->", "~", "@", "")).head
    util.Random.nextBoolean() match {
      case true => s"$body $separator $link #$hashTag"
      case false => s"$link $separator $body #$hashTag"
    }
  }
}
