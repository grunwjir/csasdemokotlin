package cz.grunwjir.csasdemo.csas.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Implementation of {@link CsasClient}.
 *
 * @author Jiri Grunwald
 */
@Slf4j
@RequiredArgsConstructor
public class CsasClientImpl implements CsasClient {

    private static final String SEARCH_BRANCHES_URL = "/webapi/api/v3/places/branches";
    private static final Integer DEFAULT_SIZE = 5;
    private static final Integer DEFAULT_PAGE = 0;

    private final RestTemplate restTemplate;
    private final ParameterizedTypeReference<CsasResponse<BranchData>> responseClazz;

    public CsasClientImpl(CsasClientProperties clientProperties) {
        restTemplate = new RestTemplateBuilder()
                .rootUri(clientProperties.getHost())
                .defaultHeader("WEB-API-key", clientProperties.getApiKey())
                .build();
        responseClazz = new ParameterizedTypeReference<>() {
        };
    }

    @Override
    public CsasResponse<BranchData> searchBranches(String searchText, Integer size, Integer page) {
        UriComponents builder = UriComponentsBuilder.fromPath(SEARCH_BRANCHES_URL)
                .queryParam("q", searchText)
                .queryParam("size", (size == null || size <= 0) ? DEFAULT_SIZE : size)
                .queryParam("page", (page == null || page < 0) ? DEFAULT_PAGE : page)
                .build();

        log.debug("Calling Csas API to search branches: {} (page size: {}, page no: {})", searchText, size, page);
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, HttpEntity.EMPTY, responseClazz).getBody();
    }
}
