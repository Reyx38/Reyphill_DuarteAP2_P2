package edu.com.reyphill_duarteap2_p2.Data.Remote.Dto

import com.squareup.moshi.Json

data class RepositoryDto(
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String?,
    @Json(name = "html_url") val htmlUrl: String
)