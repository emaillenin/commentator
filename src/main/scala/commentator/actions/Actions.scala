package commentator.actions

case class StatusUpdateAction(text: String)
case class ScheduleTweet(tweet: String)
case class SendScheduledTweet()
case class TrackTrendingTags(commentatorName: String)
case class TrackFriends(commentatorName: String)