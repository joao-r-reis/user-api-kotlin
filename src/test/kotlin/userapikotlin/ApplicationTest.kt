package userapikotlin

import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.withTestApplication
import org.junit.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test fun testRequest() = withTestApplication(Application::module) {
        with(
            handleRequest
            {
                this.method = HttpMethod.Get
                this.uri = "/"
            })
        {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals("Hello, world!<br><a href=\"/bye\">Say bye?</a>", response.content)
        }

        with(
            handleRequest
            {
                this.method = HttpMethod.Get
                this.uri = "/bye"
            })
        {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals("Good bye!", response.content)
        }
    }
}

