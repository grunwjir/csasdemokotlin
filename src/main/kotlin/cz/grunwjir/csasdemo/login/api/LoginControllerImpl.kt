package cz.grunwjir.csasdemo.login.api

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController

/**
 * Implementation of [LoginController].
 *
 * @author Jiri Grunwald
 */
@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
class LoginControllerImpl : LoginController {
    override fun login(): String {
        return """{"response": "Logged!"}"""
    }
}