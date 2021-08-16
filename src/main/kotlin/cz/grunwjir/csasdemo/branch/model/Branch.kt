package cz.grunwjir.csasdemo.branch.model

/**
 * Represents physical Csas branch.
 * The object contains details about the branch and its Google Maps rating.
 *
 * @author Jiri Grunwald
 */
data class Branch(val id: Int,
                  val name: String,
                  val address: Address,
                  val manager: String,
                  val phones: List<String>,
                  val email: String,
                  val equipments: List<String>,
                  val googleMapsRating: Float? = null) {
    data class Address(val street: String,
                       val city: String,
                       val postalCode: String)
}

