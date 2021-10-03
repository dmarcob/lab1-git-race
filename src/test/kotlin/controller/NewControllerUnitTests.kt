package es.unizar.webeng.hello.controller

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach

class NewControllerUnitTests {

    private lateinit var controller: HelloController

    /**
     *   This function is used to initialize the controller variable
     *   with its constructor. Contains [BeforeEach] annotation since it
     *   must be executed before the test.
     */
    @BeforeEach
    fun setup() {
        controller = HelloController()
    }

    /*
     *   Test that checks if the 'welcome' method is executed
     *   successfully.
     */
    @Test
    fun testUnitMessage() {
        // Map used for 'new' controller method
        val map = mutableMapOf<String, Any>()

        // Executes 'new' controller method and saves returned
        // value in variable 'view'
        val view = controller.new("andoni",map)

        // Check if the return value of the welcome method is equal to "welcome"
        assertThat(view).isEqualTo("new")

        // Check if the map inside the controller contains the key "message"
        assertThat(map.containsKey("message")).isTrue

        // Check if value for key "message" in map is "Hello World"
        assertThat(map["message"]).isEqualTo("Hello andoni")
    }

}
