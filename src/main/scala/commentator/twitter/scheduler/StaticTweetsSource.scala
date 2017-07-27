package commentator.twitter.scheduler

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.util.ByteString
import com.typesafe.scalalogging.LazyLogging
import akka.stream.ActorMaterializer
import scala.concurrent.ExecutionContext.Implicits.global
import spray.json._
import DefaultJsonProtocol._

import scala.concurrent.Future

case class MarketingCampaign(link: String, body: List[String])
case class MarketingSource(campaigns: List[MarketingCampaign])

class StaticTweetsSource()(implicit system: ActorSystem, fm: ActorMaterializer) extends LazyLogging {

  def getMarketingSource(sourceUrl: String): Future[MarketingSource] = {
    object MyJsonProtocol extends DefaultJsonProtocol {
      implicit val MarketingCampaignFormat = jsonFormat2(MarketingCampaign)
      implicit val MarketingSourceFormat = jsonFormat1(MarketingSource)
    }

    import MyJsonProtocol._

    val responseFuture: Future[HttpResponse] =
      Http().singleRequest(HttpRequest(uri = sourceUrl))

    responseFuture flatMap {
      case HttpResponse(StatusCodes.OK, headers, entity, _) =>
        entity.dataBytes.runFold(ByteString(""))(_ ++ _).map { body =>
          body.utf8String.parseJson.convertTo[MarketingSource]
        }
      case resp@HttpResponse(code, _, _, _) =>
        logger.info("Request failed, response code: " + code)
        resp.discardEntityBytes()
        throw new Exception("Request failed, response code: " + code)
    }
  }
}
