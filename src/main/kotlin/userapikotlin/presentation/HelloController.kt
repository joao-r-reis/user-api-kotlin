package userapikotlin.presentation

import io.ktor.application.ApplicationCall
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.pipeline.PipelineContext
import io.ktor.response.respondText
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import org.litote.kmongo.coroutine.json
import userapikotlin.application.HelloService

class HelloController(private val context: PipelineContext<Unit, ApplicationCall>) {

    private val helloService: HelloService by context.application.inject()

    companion object {
        fun setupRoutes(routingBuilder: Routing) {
            routingBuilder.route("") {
                get("/") {
                    HelloController(this).Index()
                }
                get("/bye") {
                    HelloController(this).Bye()
                }
            }
        }
    }

    suspend fun Index() {
        val jedi = helloService.generateAndStore()
        context.call.respondText(
                "Hello, world! ${jedi.json}<br><a href=\"/bye\">Say bye?</a>",
                ContentType.Text.Html)
    }

    suspend fun Bye() {
        context.call.respondText("""Good bye!""", ContentType.Text.Html)
    }
}