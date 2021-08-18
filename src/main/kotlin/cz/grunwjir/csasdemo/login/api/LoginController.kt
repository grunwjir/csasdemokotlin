package cz.grunwjir.csasdemo.login.api

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

/**
 * The dummy REST API controller for basic authentication.
 *
 * @author Jiri Grunwald
 */
@RequestMapping(value = ["/login"])
interface LoginController {
    @GetMapping
    @Operation(summary = "Returns \"Logged!\" on successful login")
    fun login(): String
}