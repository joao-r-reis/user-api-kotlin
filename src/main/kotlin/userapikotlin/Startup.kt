package userapikotlin

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun Application.module() {
    install(CallLogging)
    install(Routing) {
        get("/") {
            call.respondText("""Hello, world!<br><a href="/bye">Say bye?</a>""", ContentType.Text.Html)
        }
        get("/bye") {
            call.respondText("""Good bye!""", ContentType.Text.Html)
        }
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080) { this.module() }.start(wait = true)
}