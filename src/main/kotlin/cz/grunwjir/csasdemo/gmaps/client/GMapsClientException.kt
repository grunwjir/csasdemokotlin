package cz.grunwjir.csasdemo.gmaps.client

/**
 * Exception that Google Maps API call failed.
 *
 * @author Jiri Grunwald
 */
class GMapsClientException(message: String, cause: Throwable?) : Exception(message, cause)