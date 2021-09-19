package es.unizar.webeng.hello.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/*
 *    This class contains a test that checks if the 'welcome' method
 *    for class HelloController in file 'HelloController.kt' is
 *    executed successfully.
 */
class HelloControllerUnitTests {
    /*
     *   Variable 'controller' that will allocate an instance of the
     *   HelloController class. 'lateinit' implies it must be
     *   initiallized later.
     */
    private lateinit var controller: HelloController

    /*
     *   This function is used to initiallize the controller variable
     *   with its constructor. Cointains @BeforeEach tag since it
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
    fun testMessage() {
        // Map used for 'welcome' controller method
        val map = mutableMapOf<String, Any>()

        // Executes 'welcome' controller method and saves returned
        // value in variable 'view'
        val view = controller.welcome(map)

        // Check if the return value of the welcome method is equal to "welcome"
        assertThat(view).isEqualTo("welcome")

        // Check if the map inside the controller contains the key "message"
        assertThat(map.containsKey("message")).isTrue

        // Check if value for key "message" in map is "Hello World"
        assertThat(map["message"]).isEqualTo("Hello World")
    }
}
