package userapikotlin.data

import com.mongodb.async.client.MongoCollection
import org.litote.kmongo.coroutine.insertOne
import userapikotlin.domain.Jedi

class HelloRepositoryImpl(private val databaseContext: MongoCollection<Jedi>) : HelloRepository {

    override suspend fun saveHello(dummy: Jedi) {
        databaseContext.insertOne(dummy)
    }
}