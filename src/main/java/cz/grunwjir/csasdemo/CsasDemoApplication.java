package cz.grunwjir.csasdemo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Basic application class.
 *
 * @author Jiri Grunwald
 */
@SpringBootApplication
@RequiredArgsConstructor
@EnableCaching
@EnableScheduling
@Slf4j
public class CsasDemoApplication {

    public static final String PLACE_RATINGS_CACHE_NAME = "placeRatings";

    private final CacheManager cacheManager;

    public static void main(String[] args) {
        SpringApplication.run(CsasDemoApplication.class, args);
    }

    /**
     * The places rating values are cleaned from cache each day at midnight.
     */
    @Scheduled(cron = "@midnight")
    protected void clearGMapsPlaceRatingsCache() {
        Cache placeRatings = cacheManager.getCache(PLACE_RATINGS_CACHE_NAME);

        if (placeRatings != null) {
            placeRatings.clear();
        }
    }

}