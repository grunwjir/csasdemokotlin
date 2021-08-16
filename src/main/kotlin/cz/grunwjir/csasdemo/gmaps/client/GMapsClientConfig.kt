package cz.grunwjir.csasdemo.gmaps.client

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration class for Google Maps API client.
 *
 * @author Jiri Grunwald
 */
@Configuration
@EnableConfigurationProperties(GMapsClientProperties::class)
class GMapsClientConfig {

    @Bean
    fun gMapsClient(clientProperties: GMapsClientProperties): GMapsClient = GMapsClientImpl(clientProperties)
}