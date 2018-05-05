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
import org.litote.kmongo.coroutine.* //NEEDED! import KMongo extensions
import org.litote.kmongo.async.KMongo

data class Jedi(val name: String, val age: Int)

fun Application.module() {
    install(CallLogging)
    install(Routing) {
        get("/") {
            val client = KMongo.createClient() //get com.mongodb.async.client.MongoClient new instance

            val database = client.getDatabase("test") //normal java driver usage

            val collection = database.getCollection<Jedi>() //KMongo extension method
//here the name of the collection by convention is "jedi"
//you can use getCollection<Jedi>("otherjedi") if the collection name is different


            call.respondText(
                    "Hello, world! ${collection.findOne()?.json ?:
                        "No results found."}<br><a href=\"/bye\">Say bye?</a>",
                    ContentType.Text.Html)

            client.close()
        }
        get("/bye") {
            val client = KMongo.createClient() //get com.mongodb.async.client.MongoClient new instance

            val database = client.getDatabase("test") //normal java driver usage

            val collection = database.getCollection<Jedi>() //KMongo extension method
//here the name of the collection by convention is "jedi"
//you can use getCollection<Jedi>("otherjedi") if the collection name is different

            collection.insertOne(Jedi("nome1", 10))
            call.respondText("""Good bye!""", ContentType.Text.Html)

            client.close()
        }
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080) { this.module() }.start(wait = true)
}