package cz.grunwjir.csasdemo.csas.client

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Data object that represents Csas branch.
 *
 * @author Jiri Grunwald
 */
data class BranchData(val id: Int,
                      val name: String,
                      val address: String,
                      val city: String,
                      val postCode: String,
                      val managerName: String?,
                      val phones: List<String>?,
                      val email: String?,
                      @JsonProperty("equipment") val equipments: List<Equipment>?) {

    data class Equipment(val id: Int,
                         val name: String,
                         val count: Int)
}