package cz.grunwjir.csasdemo.gmaps.client;

import lombok.Data;

/**
 * Data object that represents Google Maps place.
 *
 * @author Jiri Grunwald
 */
@Data
public class PlaceData {

    private String id;
    private String name;
    private float rating;

}
