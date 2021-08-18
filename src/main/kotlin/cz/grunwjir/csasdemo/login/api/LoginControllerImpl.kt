package cz.grunwjir.csasdemo.login.api

import org.springframework.web.bind.annotation.RestController

/**
 * Implementation of [LoginController].
 *
 * @author Jiri Grunwald
 */
@RestController
class LoginControllerImpl : LoginController {
    override fun login(): String {
        return """{"response": "Logged!"}"""
    }
}