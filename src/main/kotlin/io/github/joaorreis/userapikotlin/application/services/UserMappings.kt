package io.github.joaorreis.userapikotlin.application.services

import io.github.joaorreis.userapikotlin.application.dto.UserCreationRequest
import io.github.joaorreis.userapikotlin.application.dto.UserDto
import io.github.joaorreis.userapikotlin.domain.model.User
import java.util.UUID

fun User.toDto(): UserDto {
    return UserDto(this.name, this.age, this.id)
}

fun UserCreationRequest.toDomainModel(): User {
    return User(this.name, this.age, UUID.randomUUID().toString())
}