package io.github.joaorreis.userapikotlin.infrastructure.dependencyinjection

import com.mongodb.async.client.MongoClient
import com.mongodb.async.client.MongoCollection
import com.mongodb.async.client.MongoDatabase
import org.koin.dsl.context.Context
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import org.litote.kmongo.async.KMongo
import io.github.joaorreis.userapikotlin.application.services.HelloService
import io.github.joaorreis.userapikotlin.application.services.HelloServiceImpl
import io.github.joaorreis.userapikotlin.data.repository.HelloRepository
import io.github.joaorreis.userapikotlin.data.repository.HelloRepositoryImpl
import io.github.joaorreis.userapikotlin.domain.model.Jedi

class KoinModuleFactory(private val setupPresentationDependencies: Context.() -> Unit) {

    fun create(): Module = applicationContext {

        bean { HelloServiceImpl(get()) as HelloService } // get() Will resolve HelloRepository
        bean { HelloRepositoryImpl(get()) as HelloRepository }
        bean { KMongo.createClient() }
        bean { get<MongoClient>().getDatabase("test") as MongoDatabase }
        bean { get<MongoDatabase>().getCollection("jedis", Jedi::class.java) as MongoCollection<Jedi> }

        setupPresentationDependencies()
    }
}