package cz.grunwjir.csasdemo.branch.service;

import cz.grunwjir.csasdemo.Page;
import cz.grunwjir.csasdemo.branch.model.Branch;
import cz.grunwjir.csasdemo.csas.client.BranchData;
import cz.grunwjir.csasdemo.csas.client.CsasClient;
import cz.grunwjir.csasdemo.csas.client.CsasResponse;
import cz.grunwjir.csasdemo.gmaps.service.GMapsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Implementation of {@link BranchService}.
 *
 * @author Jiri Grunwald
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BranchServiceImpl implements BranchService {

    private final GMapsService gMapsService;
    private final CsasClient csasClient;

    public Page<Branch> searchBranches(String query, Integer size, Integer page) {
        log.info("Searching branch with review: {} (size: {}, page: {})", query, size, page);
        CsasResponse<BranchData> response = csasClient.searchBranches(query, size, page);

        List<Branch> branches = response.getItems().stream()
                .map(data -> {
                    Branch branch = mapBranch(data);

                    // add branch rating
                    branch.setGoogleMapsRating(gMapsService.getPlaceRating(branch.getName(),
                            branch.getAddress().getStreet(), branch.getAddress().getCity()));

                    return branch;
                })
                .collect(Collectors.toList());

        log.info("Total: {} branches were found with query text: {}", response.getTotalItemCount(), query);

        return Page.<Branch>builder()
                .items(branches)
                .pageNumber(response.getPageNumber())
                .pageCount(response.getPageCount())
                .pageSize(response.getPageSize())
                .totalItemCount(response.getTotalItemCount())
                .build();
    }

    private Branch mapBranch(BranchData data) {
        Branch branch = new Branch();
        branch.setId(data.getId());
        branch.setName(data.getName());
        branch.setManager(data.getManagerName());
        branch.setPhones(data.getPhones());
        branch.setEquipments((data.getEquipments() != null) ? data.getEquipments().stream()
                .map(BranchData.Equipment::getName)
                .collect(Collectors.toList()) : List.of());
        branch.setEmail(data.getEmail());
        branch.setAddress(new Branch.Address(data.getAddress(), data.getCity(), data.getPostCode()));

        return branch;
    }
}