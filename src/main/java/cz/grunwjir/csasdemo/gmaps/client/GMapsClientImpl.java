package cz.grunwjir.csasdemo.gmaps.client;

import com.google.maps.FindPlaceFromTextRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.FindPlaceFromText;
import com.google.maps.model.PlacesSearchResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

/**
 * Implementation of {@link GMapsClient}.
 *
 * @author Jiri Grunwald
 */
@RequiredArgsConstructor
@Slf4j
public class GMapsClientImpl implements GMapsClient {

    private final GMapsClientProperties clientProperties;
    private GeoApiContext context;

    @PostConstruct
    private void initialize() {
        context = new GeoApiContext.Builder()
                .apiKey(clientProperties.getApiKey())
                .build();
    }

    @PreDestroy
    private void cleanUp() {
        context.shutdown();
    }

    @Override
    public PlaceData findPlaceFromText(String placeName) {
        try {
            log.debug("Calling Google Maps API to search location: {}", placeName);
            FindPlaceFromText result = PlacesApi.findPlaceFromText(context, placeName,
                    FindPlaceFromTextRequest.InputType.TEXT_QUERY)
                    .language("cs")
                    .fields(FindPlaceFromTextRequest.FieldMask.PLACE_ID, FindPlaceFromTextRequest.FieldMask.NAME,
                            FindPlaceFromTextRequest.FieldMask.RATING)
                    .await();

            if (result.candidates.length > 0) {
                return mapPlace(result.candidates[0]);
            } else {
                log.warn("No place for: {} was found in Google Maps", placeName);
                return null;
            }
        } catch (ApiException | InterruptedException | IOException e) {
            throw new GMapsClientException("Error during calling Google Maps API", e);
        }
    }

    private PlaceData mapPlace(PlacesSearchResult result) {
        PlaceData place = new PlaceData();
        place.setId(result.placeId);
        place.setName(result.name);
        place.setRating(result.rating);

        return place;
    }
}