package cz.grunwjir.csasdemo.branch.service

import cz.grunwjir.csasdemo.csas.client.BranchData
import cz.grunwjir.csasdemo.csas.client.CsasClient
import cz.grunwjir.csasdemo.csas.client.CsasResponse
import cz.grunwjir.csasdemo.gmaps.service.GMapsService
import io.kotest.assertions.asClue
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test


/**
 * Tests {@link BranchServiceImpl}.
 *
 * @author Jiri Grunwald
 */
class BranchServiceImplTest {

    private val gMapsServiceMock = mockk<GMapsService>()
    private val csasClient = mockk<CsasClient>()

    private val branchService = BranchServiceImpl(gMapsServiceMock, csasClient)

    @Test
    fun `One branch should be found and object mapping should be correct`() {
        // given
        val query = "letohrad"
        val size = 5
        val page = 0
        every { gMapsServiceMock.getPlaceRating("Letohradská", "Václavské náměstí 45", "Letohrad") } returns 4.5f
        every {
            csasClient.searchBranches(
                "letohrad",
                5,
                0
            )
        } returns CsasResponse(
            pageNumber = 0, pageCount = 1, pageSize = 5, totalItemCount = 1,
            items = listOf(getLetohradBranch())
        )

        // when
        val result = branchService.searchBranches(query, size, page)

        // then
        verify(exactly = 1) {
            gMapsServiceMock.getPlaceRating("Letohradská", "Václavské náměstí 45", "Letohrad")
            csasClient.searchBranches("letohrad", 5, 0)
        }

        result.items.size shouldBe 1
        result.items[0].asClue {
            it.id shouldBe 2
            it.name shouldBe "Letohradská"
            it.address.city shouldBe "Letohrad"
            it.address.postalCode shouldBe "56151"
            it.address.street shouldBe "Václavské náměstí 45"
            it.equipments shouldBe listOf("Bankomat")
            it.googleMapsRating shouldBe 4.5f
        }

        result.asClue {
            it.pageCount shouldBe 1
            it.pageNumber shouldBe 0
            it.pageSize shouldBe 5
            it.totalItemCount shouldBe 1
        }
    }

    @Test
    fun `No branch should be found for the given input`() {
        // given
        val query = "bla"
        val size = 5
        val page = 0
        every { csasClient.searchBranches("bla", 5, 0) } returns CsasResponse(
            pageNumber = 0,
            pageCount = 0,
            pageSize = 5,
            totalItemCount = 0,
            items = listOf()
        )

        // when
        val result = branchService.searchBranches(query, size, page)

        // then
        verify(exactly = 0) { gMapsServiceMock.getPlaceRating(any(), any(), any()) }
        result.items.size shouldBe 0
        result.asClue {
            it.pageCount shouldBe 0
            it.pageNumber shouldBe 0
            it.pageSize shouldBe 5
            it.totalItemCount shouldBe 0
        }
    }

    @Test
    fun `Two branches should be found`() {
        // given
        val query = "letohrad"
        val size = 5
        val page = 0
        every { csasClient.searchBranches(any(), any(), any()) } returns CsasResponse(
            pageNumber = 0, pageCount = 1, pageSize = 5, totalItemCount = 2,
            items = listOf(getLetohradBranch(), getLetohradBranch())
        )
        every { gMapsServiceMock.getPlaceRating(any(), any(), any()) } returns 4.5f

        // when
        val result = branchService.searchBranches(query, size, page)

        // then
        verify(exactly = 1) { csasClient.searchBranches("letohrad", 5, 0) }
        verify(exactly = 2) { gMapsServiceMock.getPlaceRating("Letohradská", "Václavské náměstí 45", "Letohrad") }
        result.items.size shouldBe 2
        result.asClue {
            it.pageCount shouldBe 1
            it.pageNumber shouldBe 0
            it.pageSize shouldBe 5
            it.totalItemCount shouldBe 2
        }
    }

    private fun getLetohradBranch(): BranchData {
        return BranchData(
            id = 2,
            name = "Letohradská",
            address = "Václavské náměstí 45",
            city = "Letohrad",
            postCode = "56151",
            managerName = "Jana Nováková",
            phones = listOf("+420 789 564 879"),
            email = "letohrad@csas.cz",
            equipments = listOf(BranchData.Equipment(id = 1, name = "Bankomat", count = 1))
        )
    }
}
