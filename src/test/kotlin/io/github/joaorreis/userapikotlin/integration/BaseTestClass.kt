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
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.litote.kmongo.async.KMongo
import org.litote.kmongo.coroutine.drop

open class BaseTestClass {
    companion object {
        val rawConfig = ConfigFactory.load()!!

        private val config = HoconApplicationConfig(rawConfig) // Provide a Hocon config file
        private val mongodExecutable = SetupInMemoryMongoServer(config)

        val mongodb: MongodProcess? = mongodExecutable.start()
        val mongoClient = KMongo.createClient(config.property("MongoDb.ConnectionString").getString())
        val mongoDatabaseName = config.property("MongoDb.DatabaseName").getString()
    }

    @Before fun setup() {
        runBlocking { mongoClient.getDatabase(mongoDatabaseName).drop() }
    }

    fun <R> integrationTest(test: TestApplicationEngine.() -> R): R {
        return withTestApplication(
            {
                (environment.config as MapApplicationConfig).apply {
                    rawConfig.entrySet().forEach({ entry -> put(entry.key, entry.value.unwrapped().toString()) })
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