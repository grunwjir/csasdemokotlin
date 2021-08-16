package cz.grunwjir.csasdemo.gmaps.client

import com.google.maps.FindPlaceFromTextRequest
import com.google.maps.GeoApiContext
import com.google.maps.PlacesApi
import com.google.maps.errors.ApiException
import com.google.maps.model.FindPlaceFromText
import com.google.maps.model.PlacesSearchResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.IOException
import javax.annotation.PreDestroy

/**
 * Implementation of [GMapsClient].
 *
 * @author Jiri Grunwald
 */
class GMapsClientImpl(clientProperties: GMapsClientProperties) : GMapsClient {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(GMapsClientImpl::class.java)
    }

    private val context: GeoApiContext = GeoApiContext.Builder()
        .apiKey(clientProperties.apiKey)
        .build()

    override fun findPlaceFromText(placeName: String): PlaceData? {
        try {
            log.debug("Calling Google Maps API to search location: {}", placeName)
            val result: FindPlaceFromText = PlacesApi.findPlaceFromText(
                context, placeName, FindPlaceFromTextRequest.InputType.TEXT_QUERY
            )
                .language("cs")
                .fields(
                    FindPlaceFromTextRequest.FieldMask.PLACE_ID,
                    FindPlaceFromTextRequest.FieldMask.NAME,
                    FindPlaceFromTextRequest.FieldMask.RATING
                )
                .await()

            return if (result.candidates.isNotEmpty()) {
                mapPlace(result.candidates[0])
            } else {
                log.warn("No place for: {} was found in Google Maps", placeName)
                null
            }
        } catch (e: ApiException) {
            throw GMapsClientException("Error during calling Google Maps API", e)
        } catch (e: InterruptedException) {
            throw GMapsClientException("Error during calling Google Maps API", e)
        } catch (e: IOException) {
            throw GMapsClientException("Error during calling Google Maps API", e)
        }
    }

    @PreDestroy
    private fun cleanUp() = context.shutdown()

    private fun mapPlace(result: PlacesSearchResult): PlaceData = PlaceData(
        id = result.placeId,
        name = result.name,
        rating = result.rating
    )
}