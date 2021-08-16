package cz.grunwjir.csasdemo.branch.api

import com.ninjasquad.springmockk.MockkBean
import cz.grunwjir.csasdemo.CsasDemoApplication
import cz.grunwjir.csasdemo.Page
import cz.grunwjir.csasdemo.branch.model.Branch
import cz.grunwjir.csasdemo.branch.service.BranchService
import cz.grunwjir.csasdemo.branch.service.BranchServiceImpl
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

/**
 * Tests {@link BranchControllerImpl}
 *
 * @author Jiri Grunwald
 */
@ContextConfiguration(classes = [CsasDemoApplication::class])
@WebMvcTest(controllers = [BranchControllerImpl::class])
@WithMockUser
class BranchControllerImplTest(@Autowired private val mvc: MockMvc) {

    @MockkBean
    lateinit var branchServiceMock: BranchService

    @Test
    fun `Search 'letohrad' should return one record`() {
        // given
        every { branchServiceMock.searchBranches("letohrad", null, null) } returns
                Page(
                    pageNumber = 0,
                    pageCount = 1,
                    pageSize = 5,
                    totalItemCount = 1,
                    items = listOf(
                        Branch(
                            id = 1,
                            manager = "Novak",
                            name = "Letohradská",
                            phones = emptyList(),
                            email = "",
                            equipments = emptyList(),
                            address = Branch.Address(street = "", city = "", postalCode = ""),
                            googleMapsRating = 4.5f
                        )
                    )
                )

        // when
        val results = mvc.perform(get("/branches?query=letohrad"))

        // then
        results.andExpect(status().isOk)
        results.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        results.andExpect(jsonPath("$.pageNumber").value(0))
        results.andExpect(jsonPath("$.pageCount").value(1))
        results.andExpect(jsonPath("$.pageSize").value(5))
        results.andExpect(jsonPath("$.totalItemCount").value(1))
        results.andExpect(jsonPath("$.items[0].name").value("Letohradská"))
        results.andExpect(jsonPath("$.items[0].googleMapsRating").value(4.5f))
    }

    @Test
    fun `Query params should be passed`() {
        // given
        every { branchServiceMock.searchBranches("jablonne", 40, 8) } returns
                Page(
                    pageNumber = 0,
                    pageCount = 0,
                    pageSize = 40,
                    totalItemCount = 0,
                    items = emptyList()
                )


        // when
        val results = mvc.perform (get("/branches?query=jablonne&size=40&page=8"))

        // then
        results.andExpect(status().isOk)
        results.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        results.andExpect(jsonPath("$.pageNumber").value(0))
        results.andExpect(jsonPath("$.pageCount").value(0))
        results.andExpect(jsonPath("$.pageSize").value(40))
        results.andExpect(jsonPath("$.totalItemCount").value(0))
        results.andExpect(jsonPath("$.items.length()").value(0))
    }

}