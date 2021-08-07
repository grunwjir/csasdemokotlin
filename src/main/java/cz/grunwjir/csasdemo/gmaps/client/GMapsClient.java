package cz.grunwjir.csasdemo.gmaps.client;

/**
 * The client for Google Maps API.
 * Now supports only one endpoint to search places.
 *
 * @author Jiri Grunwald
 */
public interface GMapsClient {

    /**
     * Search place by the given text.
     *
     * @param placeName place name, can also contain address (if place name is ambiguous)
     * @return found place that most meets the criteria, otherwise null
     */
    PlaceData findPlaceFromText(String placeName);
}