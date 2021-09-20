/*
 * File: GameControllerUnitTests.kt
 * Author: Diego Marco
 */
package es.unizar.webeng.hello.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.mock.web.MockHttpServletRequest

@WebMvcTest(GameController::class)
class GameControllerUnitTests {

    private val MOCK_SCORE: String = "0"

    @Autowired
    private lateinit var controller: GameController

    @MockBean
    private lateinit var redisTemplate: StringRedisTemplate

    /**
     * This test checks for both get and post endpoints, that the name of the template served is
     * correct. It checks that the parameter passed to the template is named correctly for both
     * cases.
     */
    @Test
    fun testMessage() {
        // mocked request to invoke correctly the controllers
        val request: MockHttpServletRequest = MockHttpServletRequest()

        // this map will store the variables to be passed by the controller to
        // the template
        val map = mutableMapOf<String, Any>()

        // Elements to mock:
        // For getMaxScore
        // - request.remoteAddr
        // - redisTemplate.opsForValue().get(ip)
        // What to check:
        // - when redisTemplate.opsForValue().get(request.remoteAddr) returns null model.maxScore is  "You haven't played yet"
        // - when redisTemplate.opsForValue().get(request.remoteAddr) returns N model.maxScore is N

        // For postMaxScore
        // - request.remoteAddr
        // - redisTemplate.opsForValue().get(ip)
        // - redisTemplate.set("127.0.0.1", score) is spied
        // What to check:
        // - when redisTemplate.opsForValue().get(request.remoteAddr) returns null, model.maxScore is score and redisTemplate.set("127.0.0.1", score) is invoked
        // - when redisTemplate.opsForValue().get(request.remoteAddr) returns a value lower than score, model.maxScore is score and redisTemplate.set("127.0.0.1", score) is invoked
        // - when redisTemplate.opsForValue().get(request.remoteAddr) returns a value greater or equal to score, model.maxScore is such value

        listOf(
            controller.getMaxScore(map, request),
            controller.postMaxScore(map, request, MOCK_SCORE)
        ).forEach { view ->
            assertThat(view).isEqualTo("game")
            assertThat(map.containsKey("maxScore")).isTrue
        }
    }
}
