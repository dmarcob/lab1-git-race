package es.unizar.webeng.hello.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

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
     * element. In this case, it sets the value of the String helloMessage to `"Hola estudiante"` and the String welcomeMessage to "Bienvenido a la universidad".
     */
    @Value("\${app.message}")
    private var message: String = "Hello World"

    @Value("\${app.hello}")
    private var helloMessage: String = "Welcome to the University"

    /**
     * This function acts as a handler of the HelloController.
     *
     * **Note** 
     * 
     * The view of this handler uses Thymeleaf as language template.
     * The view is `resources/templates/welcome.html`.
     * Thymeleaf templates has the extension `html` by default.
     * Thymeleaf templates requires to add the dependency `org.springframework.boot:spring-boot-starter-thymeleaf`.
     *
     * The annotation `@GetMapping` acts as a shortcut for `@RequestMapping(method =
     * RequestMethod.GET)`. This allows us to handle all the GET petitions to the path `/` using
     * this controller.
     *
     * @param model collection with the data used to update the view (thymeleaf template)
     * @return the template with the updated information
     */
    @GetMapping("/")
    fun welcome(model: MutableMap<String, Any>): String {
        // This is used to associate the variable "message" of the template welcome with a value.
        model["message"] = message
        return "welcome"
    }

   /**
    * This function acts as the handler of the HelloController.
    * shows a template saying hello to the parameter pased by url
    * 
    * **Note**
    * @param name parameter passed by url
    * @param model collection with the data used to update the view (template)
    * @return the template with the updated information
    */
    @GetMapping("/name/{name}")
    fun new(@PathVariable name: String, model: MutableMap<String, Any>): String {
        model["message"] = "Hello " + name 
        return "new"
    }

    /**
     * This function acts as a handler of the HelloController.
     *
     * **Note** 
     * 
     * The view of this handler uses Mustache as language template. 
     * The view is `resources/templates/hello.mustache`.
     * Mustache templates has the extension `mustache` by default.
     * Mustache templates requires to add the dependency `org.springframework.boot:spring-boot-starter-mustache`.
     * 
     */
    @GetMapping("/hello")
    fun hi(model: MutableMap<String, Any>): String {
        // This is used to associate the variable "message" of the template hello with a value.
        model["message"] = helloMessage
        return "hello"
    }

}
