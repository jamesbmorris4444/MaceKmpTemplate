package com.jetbrains.handson.kmm.shared.entity

import DateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RocketLaunch(
    @SerialName("flight_number")
    val flightNumber: Int,
    @SerialName("name")
    val missionName: String,
    @SerialName("date_utc")
    val launchDateUTC: String,
    @SerialName("details")
    val details: String?,
    @SerialName("success")
    val launchSuccess: Boolean?,
    @SerialName("links")
    val links: Links
) {
    @Contextual
    var launchDate = DateTime().getFormattedDate(launchDateUTC, "dd.MM.yyyy")
}

@Serializable
data class Links(
    @SerialName("patch")
    val patch: Patch?,
    @SerialName("article")
    val article: String?
)

@Serializable
data class Patch(
    @SerialName("small")
    val small: String?,
    @SerialName("large")
    val large: String?
)

@Serializable
data class Donor(
    @SerialName("vote_count") var voteCount: Int = 0,
    @SerialName("video") var video: Boolean = false,
    @SerialName("vote_average") var voteAverage: Float = 0f,
    @SerialName("title") var lastName: String = "",
    @SerialName("popularity") var popularity: Float = 0f,
    @SerialName("poster_path") var firstName: String = "",
    @SerialName("original_language") var middleName: String = "",
    @SerialName("original_title") var branch: String = "",
    @SerialName("backdrop_path") var aboRh: String = "",
    @SerialName("adult") var gender: Boolean = false,
    @SerialName("overview") var overview: String = "",
    @SerialName("release_date") var dob: String = "",
    var inReassociate: Boolean = false
)

@Serializable
data class Product(
    var donorId: Long = 0,
    @SerialName("din") var din: String = "",
    @SerialName("abo_rh") var aboRh: String = "",
    @SerialName("product_code") var productCode: String = "",
    @SerialName("expiration_date") var expirationDate: String = "",
    var removedForReassociation: Boolean = false,
    var inReassociate: Boolean = false

)

data class DonorWithProducts(
    val donor: Donor,
    val products: List<Product> = listOf()
)

