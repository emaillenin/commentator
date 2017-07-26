package commentator.twitter.scheduler

import akka.actor.ActorSystem
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.Random

class StaticTweets(system: ActorSystem, tweetScheduler: TweetsQueue) extends LazyLogging {
  val messages: List[String] = List(
    "Want #Flipkart vouchers? Use your cricket knowledge and win vouchers: https://goo.gl/bPN1cF #WWC17",
    "Want #Flipkart vouchers? Use your cricket knowledge and win vouchers: https://goo.gl/bPN1cF #EngVSA",
    "Want #Paytm cash? Use your cricket knowledge and win vouchers: https://goo.gl/bPN1cF #WWC17",
    "Low on #Paytm cash? Use your cricket knowledge and win vouchers: https://goo.gl/bPN1cF #EngVSA",
    "Want #FreeCharge cash 💰? Use your cricket knowledge and win vouchers: https://goo.gl/bPN1cF #WWC17",
    "Low on #FreeCharge cash 🎫? Use your cricket knowledge and win vouchers: https://goo.gl/bPN1cF #EngVSA",
    "Excited for today's match? Play fantasy cricket and win cash! https://goo.gl/bPN1cF #EngVSA",
    "Free leagues available from App. Sign up and play fantasy cricket today https://goo.gl/bPN1cF #EngVSA",
    "Free leagues available for all international matches. Win upto ₹100 in every match cash! https://goo.gl/bPN1cF #EngVSA",
    "Fantasy Cricket: Create your own private group and play with your friends. Win cash too! https://goo.gl/bPN1cF #EngVSA",
    "Join mini leagues from ₹5 and win upto ₹100 in every match. https://duggout.app.link/2wi0vfspGE",
    "Want to try something like #Dream11? Join DuggOut. 1 vs 1 battles, private leagues and much more. Download now: https://goo.gl/bPN1cF",
    "Do you know that you can earn using your #cricket knowledge 🏏? Play #FantasyCricket & win cash now: https://goo.gl/bPN1cF",
    "Can you predict #cricket match outcomes? Challenges available from ₹1. Download the app now: https://duggout.app.link/2wi0vfspGE",
    "Do you have the skills to predict the best players in an upcoming match? Then play #FantasyCricket now: https://duggout.app.link/2wi0vfspGE",
    //    "SA vs Ind starts in an hour. Choose your 11 players and win cash!",
    "Over 1000 users have chosen their fantasy teams in #DuggOut. Choose yours and win real money 💰. Join us now: https://goo.gl/bPN1cF"
    //    "SA vs Ind 3rd Match has started. Follow there live action with social commentary here"
  )

  def randomTweets(): Unit = {
    val tweet = Random.shuffle(messages).head
    logger.info(s"Scheduling tweet: $tweet")
    tweetScheduler.scheduleTweet(tweet)
    system.scheduler.scheduleOnce(15 minutes) {
      randomTweets()
    }
  }
}
