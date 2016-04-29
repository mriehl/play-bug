package company

import javax.inject.Inject
import akka.stream.Materializer
import play.api.mvc.{Result, RequestHeader, Filter}
import play.api.Logger
import play.api.routing.Router.Tags
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import javax.inject.Inject
import play.api.http.HttpFilters
import play.filters.gzip.GzipFilter
import filters.CorrelationIdFilter

class Filters @Inject() (
  cf: CorrelationIdFilter
) extends HttpFilters {

  val filters = Seq(cf)

}
