/*
 * File: GameController
 * Author: Diego Marco 
 * Coms: This controller manages the score achived by a player of an html game.
 *       To store data it uses Redis as a high-scalable NoSQL key-value database.
 *       
 *       key - value format: <ip:String> <score:String>
 * 
*/

package es.unizar.webeng.hello.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate

@Controller
class GameController(
    private val sharedData: StringRedisTemplate
) {
    
    /**
     *  This endpoint retrieves the maximum score made by a player behind an @IP.
     *  If its the first time playing it returns a message instead.
     */
    @GetMapping("/game")
    fun getMaxScore(model: MutableMap<String,Any>): String {
        var score : String? = sharedData.opsForValue().get("127.0.0.1")
        if (score == null) {
            model["maxScore"] = "You haven't played yet"
        } else {
            model["maxScore"] = score
        }

        return "game"
    }

    /**
     *  This endpoint recieves the last score made by a player behind an @IP and
     *  updates it only if its a new record.
     */
    @PostMapping("/game")
    fun postMaxScore(model: MutableMap<String, Any>, @RequestParam score:String): String {
        var maxScore : String? = sharedData.opsForValue().get("127.0.0.1")
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
