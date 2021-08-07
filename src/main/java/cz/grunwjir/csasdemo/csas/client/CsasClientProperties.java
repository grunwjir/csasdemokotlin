package cz.grunwjir.csasdemo.csas.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties class for Csas API client.
 *
 * @author Jiri Grunwald
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "csas")
public class CsasClientProperties {

    private String host;
    private String apiKey;
}