package io.github.joaorreis.userapikotlin.application.services

import io.github.joaorreis.userapikotlin.application.dto.UserCreationRequest
import io.github.joaorreis.userapikotlin.application.dto.UserDto

interface UserService {
    suspend fun createUser(request: UserCreationRequest): UserDto
    suspend fun getAllUsers(): List<UserDto>
}