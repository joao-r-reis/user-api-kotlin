package io.github.joaorreis.userapikotlin.integration.users

import com.google.gson.Gson
import io.github.joaorreis.userapikotlin.application.dto.UserDto
import io.github.joaorreis.userapikotlin.integration.helpers.BaseTestClass
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationRequest
import org.junit.Test
import kotlin.test.assertEquals

class UserGetTests : BaseTestClass() {

    @Test fun getAll_emptyDatabase_returnsEmptyArray(): Unit = integrationTest {

        // Arrange
        val setupRequest: TestApplicationRequest.() -> Unit = {
            this.method = HttpMethod.Get
            this.uri = "/api/v1/users"
            addHeader(HttpHeaders.Accept, ContentType.Application.Json.toString())
        }

        // Act
        val act = executeRequest(this, setupRequest)

        // Arrange
        val users = Gson().fromJson(act.content.toString(), Array<UserDto>::class.java)
        assertEquals(0, users.size)
    }

    @Test fun getAll_emptyDatabase_returnsOk(): Unit = integrationTest {

        // Arrange
        val setupRequest: TestApplicationRequest.() -> Unit = {
            this.method = HttpMethod.Get
            this.uri = "/api/v1/users"
            addHeader(HttpHeaders.Accept, ContentType.Application.Json.toString())
        }

        // Act
        val act = executeRequest(this, setupRequest)

        // Arrange
        assertEquals(HttpStatusCode.OK, act.status())
    }
}
