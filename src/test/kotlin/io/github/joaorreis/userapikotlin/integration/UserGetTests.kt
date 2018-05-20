package io.github.joaorreis.userapikotlin.integration

import com.google.gson.Gson
import io.github.joaorreis.userapikotlin.application.dto.UserDto
import io.github.joaorreis.userapikotlin.presentation.api.module
import io.ktor.application.Application
import io.ktor.config.MapApplicationConfig
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class UserGetTests : BaseTestClass() {
    @Test fun getAll_EmptyDatabase_ReturnsEmptyArray(): Unit = integrationTest {
        // Arrange
        // Act
        val act =
            with(
                handleRequest(
                    HttpMethod.Get,
                    "/api/v1/users",
                    { addHeader(HttpHeaders.Accept, ContentType.Application.Json.toString()) }),
                {
                    response.awaitCompletion()
                    response
                })

        // Arrange
        val users = Gson().fromJson(act.content.toString(), Array<UserDto>::class.java)
        assertEquals(0, users.size)
    }

    @Test fun getAll_EmptyDatabase_ReturnsOk(): Unit = integrationTest {
        // Arrange
        // Act
        val act =
            with(
                handleRequest(
                        HttpMethod.Get,
                        "/api/v1/users",
                        { addHeader(HttpHeaders.Accept, ContentType.Application.Json.toString()) }),
                {
                    response.awaitCompletion()
                    response
                })

        // Arrange
        assertEquals(HttpStatusCode.OK, act.status())
    }
}

