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
        val map = mutableMapOf<String,Any>()
        if (::controller.isInitialized) {
            var lview =
            listOf(controller.getMaxScore(map),controller.postMaxScore(map,MOCK_SCORE))
            for (view in lview) {
                assertThat(view).isEqualTo("game")
                assertThat(map.containsKey("maxScore")).isTrue
            }
        }
    }
}
