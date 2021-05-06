/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Api Documentation
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package ru.hsHelper.androidApp.rest.codegen.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property block
 * @property course
 * @property gsheetLink
 * @property id
 * @property name
 * @property partition
 * @property weight
 */
@JsonClass(generateAdapter = true)
data class CoursePart(
    @Json(name = "block") @field:Json(name = "block") var block: Double? = null,
    @Json(name = "course") @field:Json(name = "course") var course: Course? = null,
    @Json(name = "gsheetLink") @field:Json(name = "gsheetLink") var gsheetLink: String? = null,
    @Json(name = "id") @field:Json(name = "id") var id: Long? = null,
    @Json(name = "name") @field:Json(name = "name") var name: String? = null,
    @Json(name = "partition") @field:Json(name = "partition") var partition: Partition? = null,
    @Json(name = "weight") @field:Json(name = "weight") var weight: Double? = null
)
