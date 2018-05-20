package io.github.joaorreis.userapikotlin.integration

import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import io.ktor.config.ApplicationConfig

fun SetupInMemoryMongoServer(config : ApplicationConfig) : MongodExecutable {
    val starter = MongodStarter.getDefaultInstance()

    val bindIp = config.property("InMemoryMongo.Host").getString()
    val port = config.property("InMemoryMongo.Port").getString().toInt()
    val mongodConfig = MongodConfigBuilder()
            .version(Version.Main.DEVELOPMENT)
            .net(Net(bindIp, port, Network.localhostIsIPv6()))
            .build()

    return starter.prepare(mongodConfig)
}