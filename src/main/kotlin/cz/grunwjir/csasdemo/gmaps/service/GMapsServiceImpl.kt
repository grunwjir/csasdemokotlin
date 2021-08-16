package cz.grunwjir.csasdemo.gmaps.service

import cz.grunwjir.csasdemo.CsasDemoApplication
import cz.grunwjir.csasdemo.gmaps.client.GMapsClient
import cz.grunwjir.csasdemo.gmaps.client.PlaceData
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

/**
 * Implementation of [GMapsService].
 *
 * @author Jiri Grunwald
 */
@Service
class GMapsServiceImpl(private val gMapsClient: GMapsClient) : GMapsService {
    @Cacheable(CsasDemoApplication.PLACE_RATINGS_CACHE_NAME)
    override fun getPlaceRating(name: String, street: String, city: String): Float? {
        val place: PlaceData? = gMapsClient.findPlaceFromText(buildPlaceName(name, street, city))
        return place?.rating
    }

    private fun buildPlaceName(name: String, street: String, city: String): String {
        return String.format("česká spořitelna pobočka %s %s %s", name, street, city)
    }
}