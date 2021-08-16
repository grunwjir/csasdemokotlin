package cz.grunwjir.csasdemo

import com.fasterxml.jackson.annotation.JsonProperty
import cz.grunwjir.csasdemo.csas.client.BranchData.Equipment
import cz.grunwjir.csasdemo.csas.client.CsasResponse
import cz.grunwjir.csasdemo.csas.client.CsasClientProperties
import cz.grunwjir.csasdemo.csas.client.CsasClient
import org.springframework.web.client.RestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder
import cz.grunwjir.csasdemo.csas.client.CsasClientImpl
import org.springframework.http.HttpEntity
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import cz.grunwjir.csasdemo.gmaps.client.PlaceData
import cz.grunwjir.csasdemo.gmaps.client.GMapsClient
import cz.grunwjir.csasdemo.gmaps.client.GMapsClientProperties
import com.google.maps.GeoApiContext
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import com.google.maps.model.FindPlaceFromText
import com.google.maps.PlacesApi
import com.google.maps.FindPlaceFromTextRequest
import com.google.maps.errors.ApiException
import cz.grunwjir.csasdemo.gmaps.client.GMapsClientException
import java.lang.InterruptedException
import java.io.IOException
import com.google.maps.model.PlacesSearchResult
import cz.grunwjir.csasdemo.gmaps.client.GMapsClientImpl
import java.lang.RuntimeException
import cz.grunwjir.csasdemo.gmaps.service.GMapsService
import cz.grunwjir.csasdemo.CsasDemoApplication
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.CrossOrigin
import cz.grunwjir.csasdemo.branch.api.BranchController
import cz.grunwjir.csasdemo.branch.service.BranchService
import java.util.stream.Collectors
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import kotlin.Throws
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.cache.CacheManager
import org.springframework.scheduling.annotation.Scheduled
import kotlin.jvm.JvmStatic
import org.springframework.boot.SpringApplication
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import java.lang.Exception

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
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
    }
}