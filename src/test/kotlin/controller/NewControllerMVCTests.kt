package es.unizar.webeng.hello.controller

import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(HelloController::class)
class NewControllerMVCTests {

    var name: String = "Hello andoni"
    /**
     * Mocks the Spring controller
     */
    @Autowired private lateinit var mockMvc: MockMvc

    /**
     * With the controller [HelloController] mocked, test performs a GET request to server-side
     * endpoint "/" and:
     * 
     * - print the response
     * - expect to receive an OK status (code 200)
     * - expect the attribute "message" of the model to be [name]
     */
    @Test
    fun testMVCMessage() {
        mockMvc.perform(get("/name/andoni"))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(model().attribute("message", equalTo(name)))
    }

}
