package io.github.joaorreis.userapikotlin.unit.application.services

import io.github.joaorreis.userapikotlin.application.dto.UserCreationRequest
import io.github.joaorreis.userapikotlin.application.dto.UserDto
import io.github.joaorreis.userapikotlin.application.services.toDomainModel
import io.github.joaorreis.userapikotlin.application.services.toDto
import io.github.joaorreis.userapikotlin.domain.model.User
import org.junit.Test
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UserMappingsTests {
    @Test fun toDto_validUserModel_mapsFieldsCorrectly() {
        // Arrange
        val user = User("testname", 10, UUID.randomUUID().toString())
        val expected = UserDto(user.name, user.age, user.id)

        // Act
        val act = user.toDto()

        // Assert
        assertEquals(expected, act)
    }

    @Test fun toDomainModel_validDto_mapsFieldsCorrectly() {
        // Arrange
        val requestDto = UserCreationRequest("testname123", 105)

        // Act
        val act = requestDto.toDomainModel()

        // Assert
        assertEquals(requestDto.name, act.name)
        assertEquals(requestDto.age, act.age)
        assertNotNull(UUID.fromString(act.id))
    }
}