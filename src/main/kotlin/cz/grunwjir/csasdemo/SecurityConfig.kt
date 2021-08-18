package cz.grunwjir.csasdemo

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

/**
 * Security configuration for REST API.
 *
 * @author Jiri Grunwald
 */
@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .cors().and()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**", "/").permitAll()
            .antMatchers("/resources/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic()
    }
}