/*
 * File: GameControllerUnitTests.kt
 * Author: Diego Marco
 * Coms: Unit tests with JUnit 5 and Mockito for GameController
 */
package es.unizar.webeng.hello.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.data.redis.core.ValueOperations
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.BDDMockito.reset

@WebMvcTest(GameController::class)
class GameControllerUnitTests {
    @Autowired
    private lateinit var controller: GameController

    @MockBean
    private lateinit var redisTemplate: StringRedisTemplate

    @MockBean
    private lateinit var valueOperations: ValueOperations<String, String>

    companion object {
        //Mocked values
        const val SCORE: String = "20"
        const val MAXSCORE: String = "500"
        const val IP = "127.0.0.1"
        //Template's name returned by GameController
        const val TEMPLATE : String = "game"
        //Atributte's name passed by GameController to the template
        const val MAXSCORE_ATTR : String = "maxScore"
    }

    @Test
    fun testGame() {

        // The request is faked with a mocked IP
        val request: MockHttpServletRequest = MockHttpServletRequest()
        request.remoteAddr = IP

        // this map will store the variables to be passed by the 
        // controller to the template
        val model = mutableMapOf<String, Any>()

        //All Redis operations are mocked
        given(redisTemplate.opsForValue()).willReturn(valueOperations)


        // Check for both GET and POST endpoints:
        // - the template's name is correct.
        // - the parameter's name passed by the controller to the template is correct.
        listOf(
            controller.getMaxScore(model, request),
            controller.postMaxScore(model, request, SCORE)
        ).forEach { view ->
            assertThat(view).isEqualTo(TEMPLATE)
            assertThat(model.containsKey(MAXSCORE_ATTR)).isTrue
        }

        // Checks for GET endpoint:
        // - when redisTemplate.opsForValue().get(request.remoteAddr) returns null
        // model.maxScore is "You haven't played yet"

        //Mock redis get method to return null when remoteAddr is passed.
        given(redisTemplate.opsForValue().get(request.remoteAddr)).willReturn(null)
        controller.getMaxScore(model, request)
        assertThat(model[MAXSCORE_ATTR]).isEqualTo("You haven't played yet")
 
        // - when redisTemplate.opsForValue().get(request.remoteAddr) returns N 
        // model.maxScore is N
        
        //Mocked redis get method to return SCORE when remoteAddr is passed
        given(redisTemplate.opsForValue().get(request.remoteAddr)).willReturn(SCORE)
        controller.getMaxScore(model, request)
        assertThat(model[MAXSCORE_ATTR]).isEqualTo(SCORE)

        

        //Checks for POST endpoint:
        // - when redisTemplate.opsForValue().get(request.remoteAddr) returns null, 
        // model.maxScore is score and redisTemplate.set(request.remoteAddr, score) is invoked

        // Reset redis operations invoke's counter
        reset(valueOperations)
        given(redisTemplate.opsForValue().get(request.remoteAddr)).willReturn(null)
        controller.postMaxScore(model, request, SCORE)
        assertThat(model[MAXSCORE_ATTR]).isEqualTo(SCORE)
        //Check that redis set method was invoked only once.
        verify(valueOperations).set(request.remoteAddr,SCORE)

        // - when redisTemplate.opsForValue().get(request.remoteAddr) returns a value 
        // lower than score, model.maxScore is score and 
        // redisTemplate.set(request.remoteAddr, score) is invoked

        given(redisTemplate.opsForValue().get(request.remoteAddr)).willReturn(SCORE)
        controller.postMaxScore(model, request, MAXSCORE)
        assertThat(model[MAXSCORE_ATTR]).isEqualTo(MAXSCORE)
        verify(valueOperations).set(request.remoteAddr, MAXSCORE)

        // - when redisTemplate.opsForValue().get(request.remoteAddr) returns a value 
        // greater or equal to score, model.maxScore is such value

        given(redisTemplate.opsForValue().get(request.remoteAddr)).willReturn(MAXSCORE)
        controller.postMaxScore(model, request, SCORE)
        assertThat(model["maxScore"]).isEqualTo(MAXSCORE)
        
    }
}
