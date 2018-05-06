package io.github.joaorreis.userapikotlin.presentation.api

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.pipeline.PipelineContext
import io.ktor.response.respondText
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import org.litote.kmongo.coroutine.json
import io.github.joaorreis.userapikotlin.application.services.HelloService

class HelloController(
        context: PipelineContext<Unit, ApplicationCall>,
        private val helloService: HelloService)
    : BaseController(context) {

    companion object {

        fun setupRoutes(routingBuilder: Routing) {
            routingBuilder.route("") {
                get("/") {
                    createController<HelloController>(this).index()
                }
                get("/bye") {
                    createController<HelloController>(this).bye()
                }
            }
        }
    }

    suspend fun index() {
        val jedi = helloService.generateAndStore()
        context.call.respondText(
                "Hello, world! ${jedi.json}<br><a href=\"/bye\">Say bye?</a>",
                ContentType.Text.Html)
    }

    suspend fun bye() {
        context.call.respondText("""Good bye!""", ContentType.Text.Html)
    }
}