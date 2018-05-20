package io.github.joaorreis.userapikotlin.unit.application.services

import io.github.joaorreis.userapikotlin.application.services.UserServiceImpl
import io.github.joaorreis.userapikotlin.data.repository.UserRepository
import io.github.joaorreis.userapikotlin.domain.model.User
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import kotlin.test.assertEquals

class UserServiceImplTests {
    @Test fun getAllUsers_repoReturnsEmptyList_returnsEmptyList(): Unit = runBlocking {
        // Arrange
        val mock = mock(UserRepository::class.java)
        `when`(mock.getAll()).thenReturn(listOf<User>())
        val classUnderTest = UserServiceImpl(mock)

        // Act
        val users = classUnderTest.getAllUsers()

        // Assert
        assertEquals(0, users.size)
    }

    @Test fun getAllUsers_repoReturnsEmptyList_callsRepoGetAll(): Unit = runBlocking {
        // Arrange
        val mock = mock(UserRepository::class.java)
        `when`(mock.getAll()).thenReturn(listOf<User>())
        val classUnderTest = UserServiceImpl(mock)

        // Act
        classUnderTest.getAllUsers()

        // Assert
        verify(mock).getAll()
        Unit
    }

    @Test fun getAllUsers_repoReturnsTwoUsers_returnsTwoDtos(): Unit = runBlocking {
        // Arrange
        val mock = mock(UserRepository::class.java)
        `when`(mock.getAll()).thenReturn(listOf<User>(User("n", 1, "i"), User("as", 10, "d")))
        val classUnderTest = UserServiceImpl(mock)

        // Act
        val users = classUnderTest.getAllUsers()

        // Assert
        assertEquals(2, users.size)
    }
}