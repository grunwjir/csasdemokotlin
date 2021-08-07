package cz.grunwjir.csasdemo.gmaps.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties class for Google Maps API client.
 *
 * @author Jiri Grunwald
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "gmaps")
public class GMapsClientProperties {

    private String apiKey;
}