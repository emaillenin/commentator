package commentator.twitter.scheduler

object TweetAssembler {
  def assembleTweet(body: String, hashTag: String, link: String) = {
    util.Random.nextBoolean() match {
      case true => s"$body $link $hashTag"
      case false => s"$link $body $hashTag"
    }
  }
}
