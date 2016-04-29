package filters

import java.util.UUID

import play.api.mvc.RequestHeader

case class CorrelationContext(correlationId: String = CorrelationContext.newCorrelationId)

object CorrelationContext {
  val correlationIdTag: String = "correlationId"

  def fromRequest(request: RequestHeader): CorrelationContext = CorrelationContext(
    correlationId = correlationId(request).getOrElse(newCorrelationId)
  )

  def newCorrelationId: String = {
    UUID.randomUUID().toString.toLowerCase
  }

  private def correlationId(request: RequestHeader): Option[String] = {
    if (request.tags.contains(correlationIdTag))
      Some(request.tags(correlationIdTag))
    else
      None
  }
}
