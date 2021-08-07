package cz.grunwjir.csasdemo.gmaps.service;

import cz.grunwjir.csasdemo.CsasDemoApplication;
import cz.grunwjir.csasdemo.gmaps.client.GMapsClient;
import cz.grunwjir.csasdemo.gmaps.client.PlaceData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link GMapsService}.
 *
 * @author Jiri Grunwald
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class GMapsServiceImpl implements GMapsService {

    private final GMapsClient gMapsClient;

    @Cacheable(CsasDemoApplication.PLACE_RATINGS_CACHE_NAME)
    public Float getPlaceRating(String name, String street, String city) {
        PlaceData place = gMapsClient.findPlaceFromText(buildPlaceName(name, street, city));

        return (place != null) ? place.getRating() : null;
    }

    private String buildPlaceName(String name, String street, String city) {
        return String.format("česká spořitelna pobočka %s %s %s", name, street, city);
    }
}