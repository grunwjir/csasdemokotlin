package cz.grunwjir.csasdemo.branch.api

import cz.grunwjir.csasdemo.CsasDemoApplication
import cz.grunwjir.csasdemo.Page
import cz.grunwjir.csasdemo.branch.model.Branch
import cz.grunwjir.csasdemo.branch.service.BranchServiceImpl
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

/**
 * Tests {@link BranchControllerImpl}
 *
 * @author Jiri Grunwald
 */
@ContextConfiguration(classes = CsasDemoApplication)
@WebMvcTest(controllers = [BranchControllerImpl])
class BranchControllerImplSpec extends Specification {

    @Autowired
    MockMvc mvc

    @SpringBean
    BranchServiceImpl branchServiceStub = Stub()

    def "Search 'letohrad' should return one record"() {
        given:
        branchServiceStub.searchBranches("letohrad", null, null) >>
                Page.builder()
                        .pageNumber(0)
                        .pageCount(1)
                        .pageSize(5)
                        .totalItemCount(1)
                        .items([new Branch(name: "Letohradská", googleMapsRating: 4.5f)])
                        .build()

        when:
        def results = mvc.perform(get("/branches?query=letohrad"))

        then:
        results.andExpect(status().isOk())
        results.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        results.andExpect(jsonPath('$.pageNumber').value(0))
        results.andExpect(jsonPath('$.pageCount').value(1))
        results.andExpect(jsonPath('$.pageSize').value(5))
        results.andExpect(jsonPath('$.totalItemCount').value(1))
        results.andExpect(jsonPath('$.items[0].name').value("Letohradská"))
        results.andExpect(jsonPath('$.items[0].googleMapsRating').value(4.5f))
    }

    def "Query params should be passed"() {
        given:
        branchServiceStub.searchBranches("jablonne", 40, 8) >>
                Page.builder()
                        .pageNumber(0)
                        .pageCount(0)
                        .pageSize(40)
                        .totalItemCount(0)
                        .items([])
                        .build()

        when:
        def results = mvc.perform(get("/branches?query=jablonne&size=40&page=8"))

        then:
        results.andExpect(status().isOk())
        results.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        results.andExpect(jsonPath('$.pageNumber').value(0))
        results.andExpect(jsonPath('$.pageCount').value(0))
        results.andExpect(jsonPath('$.pageSize').value(40))
        results.andExpect(jsonPath('$.totalItemCount').value(0))
        results.andExpect(jsonPath('$.items.length()').value(0))
    }

}