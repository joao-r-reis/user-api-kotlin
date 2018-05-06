package io.github.joaorreis.userapikotlin.data.repository

import com.mongodb.async.client.MongoCollection
import org.litote.kmongo.coroutine.insertOne
import io.github.joaorreis.userapikotlin.domain.model.Jedi

class HelloRepositoryImpl(private val databaseContext: MongoCollection<Jedi>) : HelloRepository {

    override suspend fun saveHello(dummy: Jedi) {
        databaseContext.insertOne(dummy)
    }
}