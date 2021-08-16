package cz.grunwjir.csasdemo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

/**
 * Basic application class.
 *
 * @author Jiri Grunwald
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
class CsasDemoApplication(val cacheManager: CacheManager) {

    companion object {
        const val PLACE_RATINGS_CACHE_NAME = "placeRatings"
    }

    /**
     * The places rating values are cleaned from cache each day at midnight.
     */
    @Scheduled(cron = "@midnight")
    protected fun clearGMapsPlaceRatingsCache() {
        cacheManager.getCache(PLACE_RATINGS_CACHE_NAME)?.clear()
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(CsasDemoApplication::class.java, *args)
}

