package io.github.joaorreis.userapikotlin.infrastructure.dependencyinjection

import com.mongodb.async.client.MongoClient
import com.mongodb.async.client.MongoCollection
import com.mongodb.async.client.MongoDatabase
import io.github.joaorreis.userapikotlin.application.services.UserService
import io.github.joaorreis.userapikotlin.application.services.UserServiceImpl
import io.github.joaorreis.userapikotlin.data.repository.UserRepository
import io.github.joaorreis.userapikotlin.data.repository.UserRepositoryImpl
import io.github.joaorreis.userapikotlin.domain.model.User
import io.ktor.config.ApplicationConfig
import org.koin.dsl.context.Context
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import org.litote.kmongo.async.KMongo

class KoinModuleFactory(private val setupPresentationDependencies: Context.(ApplicationConfig) -> Unit) {

    fun create(config: ApplicationConfig): Module = applicationContext {

        val mongoDbConString: String = config.property("MongoDb.ConnectionString").getString()
        val mongoDbDatabaseName: String = config.property("MongoDb.DatabaseName").getString()

        bean { UserServiceImpl(get()) as UserService }
        bean { UserRepositoryImpl(get()) as UserRepository }
        bean { KMongo.createClient(mongoDbConString) }
        bean { get<MongoClient>().getDatabase(mongoDbDatabaseName) as MongoDatabase }
        bean { get<MongoDatabase>().getCollection("users", User::class.java) as MongoCollection<User> }

        setupPresentationDependencies(config)
    }
}