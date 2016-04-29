package filters

import akka.actor.ActorSystem
import akka.stream.{ ActorMaterializer, Materializer }
import javax.inject.Inject
import play.api.mvc.{ Filter, RequestHeader, Result }

import scala.concurrent.{ ExecutionContext, Future }

class CorrelationIdFilter @Inject() (implicit val executionContext: ExecutionContext, implicit val actorSystem: ActorSystem) extends Filter {

  val mat: Materializer = ActorMaterializer()
  def apply(nextFilter: (RequestHeader) => Future[Result])(requestHeader: RequestHeader): Future[Result] = {
    nextFilter(requestHeader.copy(
      tags = requestHeader.tags +
      (CorrelationContext.correlationIdTag ->
        requestRefNum(requestHeader).map(_.toString.toLowerCase).getOrElse(CorrelationContext.newCorrelationId))
    ))
  }

  private def requestRefNum(requestHeader: RequestHeader): Option[String] = {
    requestHeader.headers.get("L-IS24-RequestRefnum").map(_.trim).filter(!_.isEmpty)
  }
}
