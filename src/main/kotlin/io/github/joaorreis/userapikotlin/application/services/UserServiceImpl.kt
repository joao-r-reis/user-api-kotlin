package io.github.joaorreis.userapikotlin.application.services

import io.github.joaorreis.userapikotlin.application.dto.UserCreationRequest
import io.github.joaorreis.userapikotlin.application.dto.UserDto
import io.github.joaorreis.userapikotlin.data.repository.UserRepository

class UserServiceImpl(private val userRepo: UserRepository) : UserService {

    override suspend fun getAllUsers(): List<UserDto> {
        return userRepo.getAll().map { user -> user.toDto() }
    }

    override suspend fun createUser(request: UserCreationRequest): UserDto {
        val user = request.toDomainModel()
        userRepo.save(user)
        return user.toDto()
    }
}