package cz.grunwjir.csasdemo.branch.service

import cz.grunwjir.csasdemo.csas.client.BranchData
import cz.grunwjir.csasdemo.csas.client.CsasClient
import cz.grunwjir.csasdemo.csas.client.CsasResponse
import cz.grunwjir.csasdemo.gmaps.service.GMapsService
import spock.lang.Specification

/**
 * Tests {@link BranchServiceImpl}.
 *
 * @author Jiri Grunwald
 */
class BranchServiceImplSpec extends Specification {

    def gMapsServiceMock = Mock(GMapsService)
    def csasClient = Mock(CsasClient)

    def branchService = new BranchServiceImpl(gMapsServiceMock, csasClient)

    def "One branch should be found and object mapping should be correct"() {
        given:
        def query = "letohrad"
        def size = 5
        def page = 0

        when:
        def result = branchService.searchBranches(query, size, page)

        then:
        1 * gMapsServiceMock.getPlaceRating("Letohradská", "Václavské náměstí 45", "Letohrad") >> 4.5f
        1 * csasClient.searchBranches("letohrad", 5, 0) >>
                new CsasResponse<BranchData>(pageNumber: 0, pageCount: 1, pageSize: 5, totalItemCount: 1,
                        items: [getLetohradBranch()])
        result != null
        result.items != null
        result.items.size() == 1
        verifyAll(result.items.get(0)) {
            it.getId() == 2
            it.getName() == "Letohradská"
            it.getAddress() != null
            it.getAddress().getCity() == "Letohrad"
            it.getAddress().getPostalCode() == "56151"
            it.getAddress().getStreet() == "Václavské náměstí 45"
            it.getEquipments() == ["Bankomat"]
            it.getGoogleMapsRating() == 4.5f
        }
        verifyAll(result) {
            it.getPageCount() == 1
            it.getPageNumber() == 0
            it.getPageSize() == 5
            it.getTotalItemCount() == 1
        }
    }

    def "No branch should be found for the given input"() {
        given:
        def query = "bla"
        def size = 5
        def page = 0

        when:
        def result = branchService.searchBranches(query, size, page)

        then:
        1 * csasClient.searchBranches("bla", 5, 0) >>
                new CsasResponse<BranchData>(pageNumber: 0, pageCount: 0, pageSize: 5, totalItemCount: 0, items: [])

        and: "Google Maps API shouldn't be called"
        0 * gMapsServiceMock.getPlaceRating(_, _, _)

        result != null
        result.items != null
        result.items.size() == 0
        verifyAll(result) {
            it.getPageCount() == 0
            it.getPageNumber() == 0
            it.getPageSize() == 5
            it.getTotalItemCount() == 0
        }
    }

    def "Two branches should be found"() {
        given:
        def query = "letohrad"
        def size = 5
        def page = 0

        when:
        def result = branchService.searchBranches(query, size, page)

        then:
        1 * csasClient.searchBranches("letohrad", 5, 0) >>
                new CsasResponse<BranchData>(pageNumber: 0, pageCount: 1, pageSize: 5, totalItemCount: 2,
                        items: [getLetohradBranch(), getLetohradBranch()])
        and: "Google Maps API should be called twice"
        2 * gMapsServiceMock.getPlaceRating("Letohradská", "Václavské náměstí 45", "Letohrad") >> 4.5f

        result != null
        result.items != null
        result.items.size() == 2
        verifyAll(result) {
            it.getPageCount() == 1
            it.getPageNumber() == 0
            it.getPageSize() == 5
            it.getTotalItemCount() == 2
        }
    }

    static BranchData getLetohradBranch() {
        return new BranchData(id: 2, name: "Letohradská", address: "Václavské náměstí 45", city: "Letohrad",
                postCode: "56151", managerName: "Jana Nováková", phones: ["+420 789 564 879"], email: "letohrad@csas.cz",
                equipments: [new BranchData.Equipment(name: "Bankomat")])
    }
}