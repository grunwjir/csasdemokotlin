package cz.grunwjir.csasdemo.branch.service;

import cz.grunwjir.csasdemo.Page;
import cz.grunwjir.csasdemo.branch.model.Branch;

/**
 * Service class for Csas branches.
 *
 * @author Jiri Grunwald
 */
public interface BranchService {

    /**
     * Search Csas branches by the given text. Supports paging.
     *
     * @param query searching text (can be branch name, address, city)
     * @param size  page size
     * @param page  selected page, first page is 0
     * @return list of found branches and paging information
     */
    Page<Branch> searchBranches(String query, Integer size, Integer page);
}