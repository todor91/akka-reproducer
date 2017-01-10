import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.MediaTypes.`application/json`
import akka.stream.ActorMaterializer
import play.api.libs.json._
import play.api.libs.json.Json
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshaller}
import com.typesafe.scalalogging.LazyLogging

object Main extends App with LazyLogging {

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val route = path("testRoute") {
    post {
      entity(as[SearchRequest]) { request =>
        complete {
          "Processing single request"
        }
      } ~ entity(as[Seq[SearchRequest]]) { request =>
        complete {
          "Processing multiple requests"
        }
      }
    }
  }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  implicit def playJsonUnmarshaller[A](implicit reads: Reads[A]): FromEntityUnmarshaller[A] = {
    def read(json: JsValue) = reads.reads(json).recoverTotal(error => throw new IllegalArgumentException(JsError.toJson(error).toString))
    Unmarshaller
      .byteStringUnmarshaller
      .forContentTypes(`application/json`)
      .mapWithCharset((data, charset) => read(Json.parse(data.decodeString(charset.nioCharset.name))))
  }
}

case class SearchRequest(name: String, params: JsObject)
object SearchRequest {
  implicit val AddSearchRequestFormat: Reads[SearchRequest] = Json.reads[SearchRequest]
}