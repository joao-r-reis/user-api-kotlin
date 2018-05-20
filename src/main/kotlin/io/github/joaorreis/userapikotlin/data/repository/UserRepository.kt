package io.github.joaorreis.userapikotlin.data.repository

import io.github.joaorreis.userapikotlin.domain.model.User

interface UserRepository {
    suspend fun save(userModel: User)
    suspend fun getAll(): List<User>
}