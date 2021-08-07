package cz.grunwjir.csasdemo.branch.api;

import cz.grunwjir.csasdemo.Page;
import cz.grunwjir.csasdemo.branch.model.Branch;
import cz.grunwjir.csasdemo.branch.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of {@link BranchController}.
 *
 * @author Jiri Grunwald
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class BranchControllerImpl implements BranchController {

    private final BranchService branchService;

    @Override
    public Page<Branch> searchBranches(String query, Integer size, Integer page) {
        return branchService.searchBranches(query, size, page);
    }
}