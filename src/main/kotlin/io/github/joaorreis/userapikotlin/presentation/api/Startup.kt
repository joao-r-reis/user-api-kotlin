package io.github.joaorreis.userapikotlin.presentation.api

import com.typesafe.config.ConfigFactory
import io.ktor.application.Application
import io.ktor.config.ApplicationConfig
import io.ktor.config.HoconApplicationConfig
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.slf4j.LoggerFactory

fun embeddedServer(appConfig: ApplicationConfig): ApplicationEngine {

    val environment = applicationEngineEnvironment {

        this.log = LoggerFactory.getLogger("ktor.application")
        this.watchPaths = emptyList()
        this.module(Application::module)
        this.config = appConfig

        connector {
            this.port = appConfig.propertyOrNull("ktor.deployment.port")?.getString()?.toInt() ?: 80
            this.host = "0.0.0.0"
        }
    }

    return embeddedServer(Netty, environment, {})
}

fun main(args: Array<String>) {
    embeddedServer(HoconApplicationConfig(ConfigFactory.load())).start(true)
}