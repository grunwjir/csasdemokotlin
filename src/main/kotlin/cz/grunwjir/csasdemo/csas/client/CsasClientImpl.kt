package cz.grunwjir.csasdemo.csas.client

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder

/**
 * Implementation of [CsasClient].
 *
 * @author Jiri Grunwald
 */
class CsasClientImpl(clientProperties: CsasClientProperties) : CsasClient {
    private val restTemplate: RestTemplate = RestTemplateBuilder()
        .rootUri(clientProperties.host)
        .defaultHeader("WEB-API-key", clientProperties.apiKey)
        .build()
    private val responseClazz: ParameterizedTypeReference<CsasResponse<BranchData>> =
        object : ParameterizedTypeReference<CsasResponse<BranchData>>() {}

    companion object {
        private val log: Logger = LoggerFactory.getLogger(CsasClientImpl::class.java)
        private const val SEARCH_BRANCHES_URL = "/webapi/api/v3/places/branches"
        private const val DEFAULT_SIZE = 5
        private const val DEFAULT_PAGE = 0
    }

    override fun searchBranches(searchText: String, size: Int?, page: Int?): CsasResponse<BranchData> {
        val builder: UriComponents = UriComponentsBuilder.fromPath(SEARCH_BRANCHES_URL)
            .queryParam("q", searchText)
            .queryParam("size", if (size == null || size <= 0) DEFAULT_SIZE else size)
            .queryParam("page", if (page == null || page < 0) DEFAULT_PAGE else page)
            .build()
        log.debug("Calling Csas API to search branches: {} (page size: {}, page no: {})", searchText, size, page)

        return restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.GET,
            HttpEntity.EMPTY,
            responseClazz
        ).body ?: CsasResponse(0, 0, 0, 0, emptyList())
    }
}