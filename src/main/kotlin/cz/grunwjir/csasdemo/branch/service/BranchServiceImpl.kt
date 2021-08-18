package cz.grunwjir.csasdemo.branch.service

import cz.grunwjir.csasdemo.Page
import cz.grunwjir.csasdemo.branch.model.Branch
import cz.grunwjir.csasdemo.csas.client.BranchData
import cz.grunwjir.csasdemo.csas.client.CsasClient
import cz.grunwjir.csasdemo.csas.client.CsasResponse
import cz.grunwjir.csasdemo.gmaps.service.GMapsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * Implementation of [BranchService].
 *
 * @author Jiri Grunwald
 */
@Service
class BranchServiceImpl(private val gMapsService: GMapsService, private val csasClient: CsasClient) : BranchService {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(BranchServiceImpl::class.java)
    }

    override fun searchBranches(query: String, size: Int?, page: Int?): Page<Branch> {
        log.info("Searching branch with review: {} (size: {}, page: {})", query, size, page)
        val csasResponse: CsasResponse<BranchData> = csasClient.searchBranches(query, size, page)

        return csasResponse.items
            .map { mapBranch(it, gMapsService.getPlaceRating(it.name, it.address, it.city)) }
            .let {
                Page(
                    items = it,
                    pageNumber = csasResponse.pageNumber,
                    pageCount = csasResponse.pageCount,
                    pageSize = csasResponse.pageSize,
                    totalItemCount = csasResponse.totalItemCount
                )
            }
            .also { log.info("Total: {} branches were found with query text: {}", csasResponse.totalItemCount, query) }
    }

    private fun mapBranch(data: BranchData, googleMapsRating: Float?): Branch {
        return Branch(
            id = data.id,
            name = data.name,
            manager = data.managerName ?: "",
            phones = data.phones ?: emptyList(),
            email = data.email ?: "",
            equipments = data.equipments?.map { it.name } ?: emptyList(),
            address = Branch.Address(
                street = data.address,
                city = data.city,
                postalCode = data.postCode
            ),
            googleMapsRating = googleMapsRating
        )
    }
}