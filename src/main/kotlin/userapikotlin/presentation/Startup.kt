package userapikotlin.presentation

import com.mongodb.async.client.MongoClient
import com.mongodb.async.client.MongoCollection
import com.mongodb.async.client.MongoDatabase
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.routing.Routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.dsl.module.applicationContext
import org.koin.ktor.ext.inject
import org.koin.standalone.StandAloneContext.startKoin
import org.litote.kmongo.async.KMongo
import userapikotlin.application.HelloService
import userapikotlin.application.HelloServiceImpl
import userapikotlin.data.HelloRepository
import userapikotlin.data.HelloRepositoryImpl
import userapikotlin.domain.Jedi

val helloAppModule = applicationContext {
    bean { HelloServiceImpl(get()) as HelloService } // get() Will resolve HelloRepository
    bean { HelloRepositoryImpl(get()) as HelloRepository }
    bean { KMongo.createClient() as MongoClient }
    bean { get<MongoClient>().getDatabase("test") as MongoDatabase }
    bean { get<MongoDatabase>().getCollection("jedis", Jedi::class.java) as MongoCollection<Jedi> }
}

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