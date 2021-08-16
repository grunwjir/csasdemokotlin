package cz.grunwjir.csasdemo.csas.client

/**
 * The client for Csas public API.
 * Now supports only one endpoint to search branches.
 *
 * @author Jiri Grunwald
 */
interface CsasClient {
    /**
     * Search Csas branches by the given text.
     *
     * @param searchText searched branch by its name or address
     * @param size       the number of items per page (paging purpose)
     * @param page       page number (paging purpose)
     * @return list of found branches including information about paging
     */
    fun searchBranches(searchText: String, size: Int?, page: Int?): CsasResponse<BranchData>
}