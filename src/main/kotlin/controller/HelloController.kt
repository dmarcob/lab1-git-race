package es.unizar.webeng.hello.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * **Note**
 *
 * The annotation `@Controller` serves as a specialization of `@Component` and it allows us to
 * implement a web controller that handles templates.
 */
@Controller
class HelloController {

    /**
     * **Note** 
     * 
     * The annotation `@vValue` indicates a default value expression for the annotated
     * element. In this case, it sets the value of the String message to `"Hola estudiante"`.
     */
    @Value("\${app.message}") private var message: String = "Hello World"

    /**
     * This function acts as the handler of the HelloController.
     *
     * **Note** 
     * 
     * The annotation `@GetMapping` acts as a shortcut for `@RequestMapping(method =
     * RequestMethod.GET)`. This allows us to handle all the GET petitions to the path `/` using
     * this controller.
     *
     * @param model collection with the data used to update the view (template)
     * @return the template with the updated information
     */
    @GetMapping("/")
    fun welcome(model: MutableMap<String, Any>): String {
        // This is used to associate the variable "message" of the template welcome with a value.
        model["message"] = message
        return "welcome"
    }
}
