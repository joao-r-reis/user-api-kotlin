package userapikotlin.data

import userapikotlin.domain.Jedi

interface HelloRepository {
    suspend fun saveHello(dummy: Jedi)
}