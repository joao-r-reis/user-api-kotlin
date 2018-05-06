package io.github.joaorreis.userapikotlin.presentation.api

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.routing.Routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.dsl.context.Context
import org.koin.standalone.StandAloneContext.startKoin
import io.github.joaorreis.userapikotlin.infrastructure.dependencyinjection.KoinModuleFactory
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.pipeline.PipelineContext
import org.koin.ktor.ext.inject

const val CONTEXT_PARAM: String = "context"

fun Context.addControllersToKoinModule() {

    factory { params -> HelloController(params[CONTEXT_PARAM], get()) }
}

inline fun <reified T: BaseController> createController(context: PipelineContext<Unit, ApplicationCall>): T {

    val controller: T by context.call.application.inject { mapOf(CONTEXT_PARAM to context) }
    return controller
}

val helloAppModule = KoinModuleFactory(Context::addControllersToKoinModule).create()

fun Application.module() {
    install(CallLogging)
    install(Routing) {
        HelloController.setupRoutes(this)
    }
}

fun main(args: Array<String>) {
    startKoin(listOf(helloAppModule))
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}