package cz.grunwjir.csasdemo.branch.api

import cz.grunwjir.csasdemo.Page
import cz.grunwjir.csasdemo.branch.model.Branch
import cz.grunwjir.csasdemo.branch.service.BranchService
import org.springframework.web.bind.annotation.RestController

/**
 * Implementation of [BranchController].
 *
 * @author Jiri Grunwald
 */
@RestController
class BranchControllerImpl(private val branchService: BranchService) : BranchController {
    override fun searchBranches(query: String, size: Int?, page: Int?): Page<Branch> {
        return branchService.searchBranches(query, size, page)
    }
}