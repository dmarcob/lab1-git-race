/*
 * File: GameControllerUnitTests.kt
 * Author: Diego Marco 
*/
package es.unizar.webeng.hello.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mock.web.MockHttpServletRequest


class GameControllerUnitTests {

    private val MOCK_SCORE: String = "0"
    
    @Autowired
    private lateinit var controller: GameController
    

    /**
     * This test checks for both get and post endpoints, that the
     * name of the template served is correct.
     * It checks that the parameter passed to the template is named 
     * correctly for both casses.
     */
    @Test
    fun testMessage() {
        //mocked request to invoke correctly the controllers
        var request : MockHttpServletRequest = MockHttpServletRequest()
        //this map will store the variables to be passed by the controller to
        //the template
        val map = mutableMapOf<String,Any>()
        if (::controller.isInitialized) {
            var lview =
                listOf(controller.getMaxScore(map,request),
                controller.postMaxScore(map, request, MOCK_SCORE))
            for (view in lview) {
                assertThat(view).isEqualTo("game")
                assertThat(map.containsKey("maxScore")).isTrue
            }
        }
    }
}
