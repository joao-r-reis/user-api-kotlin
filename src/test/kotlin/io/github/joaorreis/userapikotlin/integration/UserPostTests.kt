package io.github.joaorreis.userapikotlin.integration

import com.google.gson.Gson
import io.github.joaorreis.userapikotlin.application.dto.UserCreationRequest
import io.github.joaorreis.userapikotlin.application.dto.UserDto
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationRequest
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UserPostTests : BaseTestClass() {

    @Test
    fun post_validRequest_returnsOk(): Unit = integrationTest {

        // Arrange
        val requestBodyDto = UserCreationRequest("testname123", 25)
        val setupRequest: TestApplicationRequest.() -> Unit = {
            this.method = HttpMethod.Post
            this.uri = "/api/v1/users"
            this.body = Gson().toJson(requestBodyDto)
            addHeader(HttpHeaders.Accept, ContentType.Application.Json.toString())
        }

        // Act
        val act = executeRequest(this, setupRequest)

        // Arrange
        assertEquals(HttpStatusCode.OK, act.status())
    }

    @Test
    fun post_validRequest_returnsCorrectDto(): Unit = integrationTest {

        // Arrange
        val expectedName = "testname12345"
        val expectedAge = 45

        val requestBodyDto = UserCreationRequest(expectedName, expectedAge)
        val setupRequest: TestApplicationRequest.() -> Unit = {
            this.method = HttpMethod.Post
            this.uri = "/api/v1/users"
            this.body = Gson().toJson(requestBodyDto)
            addHeader(HttpHeaders.Accept, ContentType.Application.Json.toString())
        }

        // Act
        val act = executeRequest(this, setupRequest)
        val responseDto = Gson().fromJson(act.content, UserDto::class.java)

        // Arrange
        assertEquals(expectedName, responseDto.name)
        assertEquals(expectedAge, responseDto.age)
        assertNotNull(UUID.fromString(responseDto.id))
    }
}