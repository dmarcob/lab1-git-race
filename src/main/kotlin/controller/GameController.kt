/*
 * File: GameController
 * Author: Diego Marco 
 * Coms: This controller manages the score achived by a player of the html game.
 *       To store data it uses Redis as a high-scalable NoSQL key-value database.
 *
 *       You must run `docker-compose -f redis.yml up` in order to have redis seted up
 *       before you access this endpoint. 
*/

package es.unizar.webeng.hello.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.data.redis.core.StringRedisTemplate
import javax.servlet.http.HttpServletRequest

@Controller
class GameController(
    private val sharedData: StringRedisTemplate
) {
    
    /**
     *  This endpoint retrieves the maximum score made by a player behind an @IP.
     *  If its the player first time, it will returns a message instead.
     */
    @GetMapping("/game")
    fun getMaxScore(model: MutableMap<String,Any>, request : HttpServletRequest):String {
        val ip :String = request.remoteAddr
        //Retrieves the max score of user with @IP = ip, from Redis.
        val maxScore : String? = sharedData.opsForValue().get(ip)

        if (maxScore == null) {
            //Case its the player first time
            model["maxScore"] = "You haven't played yet"
        } else {
            model["maxScore"] = maxScore
        }

        return "game"
    }

    /**
     *  This endpoint recieves the last score made by a player behind an @IP and
     *  updates it only if its a new record.
     */
    @PostMapping("/game")
    fun postMaxScore(model: MutableMap<String, Any>, request:
    HttpServletRequest, @RequestParam score:String): String {
        val ip :String = request.remoteAddr
        val maxScore : String? = sharedData.opsForValue().get(ip)

        if(maxScore == null || score.toInt() > maxScore.toInt()) {
            //First time scoring or new record
            sharedData.opsForValue().set("127.0.0.1", score)
            model["maxScore"] = score
        } else {
            //The new score wasn't enough to beat maxScore
            model["maxScore"] = maxScore
        }

        return "game"
    }
}
