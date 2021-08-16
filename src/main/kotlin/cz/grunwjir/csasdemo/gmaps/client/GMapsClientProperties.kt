package cz.grunwjir.csasdemo.gmaps.client

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 * Configuration properties class for Google Maps API client.
 *
 * @author Jiri Grunwald
 */

@ConstructorBinding
@ConfigurationProperties(prefix = "gmaps")
data class GMapsClientProperties(val apiKey: String)