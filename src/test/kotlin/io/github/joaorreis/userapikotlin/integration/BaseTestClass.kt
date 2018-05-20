package io.github.joaorreis.userapikotlin.integration

import com.typesafe.config.ConfigFactory
import de.flapdoodle.embed.mongo.MongodProcess
import io.github.joaorreis.userapikotlin.presentation.api.module
import io.ktor.config.HoconApplicationConfig
import io.ktor.config.MapApplicationConfig
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.TestApplicationRequest
import io.ktor.server.testing.TestApplicationResponse
import io.ktor.server.testing.withTestApplication
import org.junit.AfterClass
import org.junit.BeforeClass
import java.util.*

open class BaseTestClass {
    companion object {
        val rawConfig = ConfigFactory.load()!!

        private val config = HoconApplicationConfig(rawConfig) // Provide a Hocon config file
        private val mongodExecutable = SetupInMemoryMongoServer(config)

        var mongodb: MongodProcess? = null

        @BeforeClass
        @JvmStatic fun setup() {
            mongodb = mongodExecutable!!.start()
        }

        @AfterClass
        @JvmStatic fun teardown() {
            mongodb!!.stop()
        }
    }

    fun <R> integrationTest(test: TestApplicationEngine.() -> R): R {
        return withTestApplication(
            {
                (environment.config as MapApplicationConfig).apply {

                    rawConfig.entrySet().forEach({ entry -> put(entry.key, entry.value.unwrapped().toString()) })
                    put("MongoDb.DatabaseName", "tests-" + UUID.randomUUID().toString())
                }

                module() // Call here your application's module
            },
            test)
    }

    fun executeRequest(engine : TestApplicationEngine, setupRequest: TestApplicationRequest.() -> Unit)
            : TestApplicationResponse {

        val req : TestApplicationEngine.() -> TestApplicationResponse =
                { with(handleRequest(setupRequest), { response.awaitCompletion(); response }) }

        return engine.req()
    }
}