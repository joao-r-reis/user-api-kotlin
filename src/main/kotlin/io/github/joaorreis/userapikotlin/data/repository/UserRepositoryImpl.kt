package io.github.joaorreis.userapikotlin.data.repository

import com.mongodb.async.client.MongoCollection
import org.litote.kmongo.coroutine.insertOne
import io.github.joaorreis.userapikotlin.domain.model.User
import org.litote.kmongo.coroutine.toList

class UserRepositoryImpl(private val databaseContext: MongoCollection<User>) : UserRepository {

    override suspend fun getAll(): List<User> {
        return databaseContext.find().toList()
    }

    override suspend fun save(userModel: User) {
        databaseContext.insertOne(userModel)
    }
}