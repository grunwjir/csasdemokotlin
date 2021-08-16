package cz.grunwjir.csasdemo.csas.client

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration class for Csas API client.
 *
 * @author Jiri Grunwald
 */
@Configuration
@EnableConfigurationProperties(CsasClientProperties::class)
class CsasClientConfig {

    @Bean
    fun csasClient(clientProperties: CsasClientProperties): CsasClient = CsasClientImpl(clientProperties)
}