package io.github.joaorreis.userapikotlin.presentation.api

import io.github.joaorreis.userapikotlin.infrastructure.dependencyinjection.KoinModuleFactory
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.routing.Routing
import io.ktor.routing.route
import org.koin.standalone.StandAloneContext

fun Application.module() {

    val userApiModule = KoinModuleFactory({ _ -> addControllersToKoinModule() }).create(this.environment.config)
    StandAloneContext.loadKoinModules(userApiModule)

    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    install(Routing) {
        route("api/v1") {
            UserController.setupRoutes(this)
        }
    }
}