package es.unizar.webeng.hello

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

/*
 *   Class using JUnit and SpringBootTest for running tests. These test will make sure
 *   the different modules of the system work together correctly.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class IntegrationTest {
    @LocalServerPort private var port: Int = 0

    @Autowired private lateinit var restTemplate: TestRestTemplate

    /*
     *   Test that checks if the local server is running, then checks the content
     *   of the home page's body. If it contains the title "Hello" the test passes.
     *   Otherwise the test fails.
     */
    @Test
    fun testHome() {
        // Creates synchronous client to perform requests
        with(restTemplate.getForEntity("http://localhost:$port", String::class.java)) {
            // Checks the server is running correctly (returns code 200: "OK")
            assertThat(statusCode).isEqualTo(HttpStatus.OK)
            // Checks the body contains the title "Hello"
            assertThat(body).contains("<title>Hello")
        }
    }

    /*
     *   Test that checks if the headers of the bootstrap are css-based. For that,
     *   if the headers have a MIME datatype "text" and a subtype "css", the test passes.
     *   Otherwise the test fails.
     */
    @Test
    fun testCss() {
        // Creates synchronous client to perform requests
        with(
                restTemplate.getForEntity(
                        "http://localhost:$port/webjars/bootstrap/5.1.0/css/bootstrap.min.css",
                        String::class.java
                )
        ) {
            // Checks the server is running correctly (returns code 200: "OK")
            assertThat(statusCode).isEqualTo(HttpStatus.OK)
            // Checks the body contains the keyword "body"
            assertThat(body).contains("body")
            // Checks that the headers have the MIME type "text" and the subtype "css"
            assertThat(headers.contentType).isEqualTo(MediaType.valueOf("text/css"))
        }
    }
}
