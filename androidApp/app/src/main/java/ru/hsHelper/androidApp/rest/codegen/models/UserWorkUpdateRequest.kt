/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Api Documentation
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package ru.hsHelper.androidApp.rest.codegen.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property mark
 * @property solution
 */
@JsonClass(generateAdapter = true)
data class UserWorkUpdateRequest(
    @Json(name = "solution") @field:Json(name = "solution") var solution: String,
    @Json(name = "mark") @field:Json(name = "mark") var mark: Double? = null
)
