package cz.grunwjir.csasdemo.gmaps.service

/**
 * Service class providing place rating for the given place name and address.
 *
 * @author Jiri Grunwald
 */
interface GMapsService {
    /**
     * Return place rating by the given place name and address.
     * The result value can be cached.
     *
     * @param name   place name
     * @param street place street
     * @param city   place city
     * @return rating value, value should be less than or equal to 5
     */
    fun getPlaceRating(name: String, street: String, city: String): Float?
}