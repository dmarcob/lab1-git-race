package es.unizar.webeng.hello.controller

import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

/**
 * **Note:**
 * [WebMvcTest] is an annotation used to apply only configuration relevant to MVC test.
 */
@WebMvcTest(HelloController::class)
class HelloControllerMVCTests {
    /**
     * Assigns the property `app.message` in `application.properties` to [message]
     * 
     * **Note:**
     * We use [Value] to inicialize the properties.
     * We take advantage of Spring´s expression language to access the values using `${value}`.
     */ 
    @Value("\${app.message}") private lateinit var message: String

    /**
     * Mocks the Spring controller.file
     * 
     * **Note:**
     * Spring can enable dependency injection in an automatic mode. 
     * This can be done thanks to this [Autowired] annotation.
     * Great option if you want to mantain thecode because it updates automatically.
     */
    @Autowired private lateinit var mockMvc: MockMvc

    /**
     * With the controller [HelloController] mocked, test performs a GET request to server-side
     * endpoint "/" and:
     * 
     * - print the response
     * - expect to receive an OK status (code 200)
     * - expect the atributte "message" of the model to be [message]
     * 
     * **Note:**
     * Annotation [Test] is used to specify the body of a unit test.
     */
    @Test
    fun testMessage() {
        // Method .perfom executes a get operation to the URL specified. 
        // In this case, to the root dir "/".
        mockMvc.perform(get("/"))
            // Executes the print method (using the mock)
            .andDo(print())
            // Expects that no error occurred (isOk proves it)
            .andExpect(status().isOk)
            // And also expects that the attribute message is equal to the one 
            // previously initialized (the app.message value)
            .andExpect(model().attribute("message", equalTo(message)))
    }
}
