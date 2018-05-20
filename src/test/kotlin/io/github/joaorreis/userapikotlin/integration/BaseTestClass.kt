package io.github.joaorreis.userapikotlin.integration

import com.typesafe.config.ConfigFactory
import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodProcess
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import io.github.joaorreis.userapikotlin.presentation.api.module
import io.ktor.application.Application
import io.ktor.config.HoconApplicationConfig
import io.ktor.config.MapApplicationConfig
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.withTestApplication
import org.junit.AfterClass
import org.junit.BeforeClass
import java.util.*

open class BaseTestClass {
    companion object {
        val starter = MongodStarter.getDefaultInstance()
        val rawConfig = ConfigFactory.load()
        val config = HoconApplicationConfig(ConfigFactory.load()) // Provide a Hocon config file

        val bindIp = config.property("InMemoryMongo.Host").getString()
        val port = config.property("InMemoryMongo.Port").getString().toInt()
        val mongodConfig = MongodConfigBuilder()
                .version(Version.Main.DEVELOPMENT)
                .net(Net(bindIp, port, Network.localhostIsIPv6()))
                .build()

        val mongodExecutable: MongodExecutable? = starter.prepare(mongodConfig)
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
        return withTestApplication({
            (environment.config as MapApplicationConfig).apply {
                rawConfig.entrySet().forEach({ entry -> put(entry.key, entry.value.unwrapped().toString())})
                // Set here the properties
                put("MongoDb.DatabaseName", "tests-"+ UUID.randomUUID().toString())
            }
            module() // Call here your application's module
        }, test)
    }
}