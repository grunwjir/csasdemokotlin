package cz.grunwjir.csasdemo.gmaps.client;

/**
 * Runtime exception that Google Maps API call failed.
 *
 * @author Jiri Grunwald
 */
public class GMapsClientException extends RuntimeException {

    public GMapsClientException(String message, Throwable cause) {
        super(message, cause);
    }
}