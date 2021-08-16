package cz.grunwjir.csasdemo.csas.client

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 * Configuration properties class for Csas API client.
 *
 * @author Jiri Grunwald
 */
@ConfigurationProperties(prefix = "csas")
@ConstructorBinding
data class CsasClientProperties(val host: String, val apiKey: String)