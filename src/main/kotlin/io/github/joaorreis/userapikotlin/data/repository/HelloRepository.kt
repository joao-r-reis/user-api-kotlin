package io.github.joaorreis.userapikotlin.data.repository

import io.github.joaorreis.userapikotlin.domain.model.Jedi

interface HelloRepository {
    suspend fun saveHello(dummy: Jedi)
}