/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Api Documentation
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package ru.hsHelper.androidApp.rest.codegen.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property column
 * @property workId
 */
@JsonClass(generateAdapter = true)
data class MarksColumnData(
    @Json(name = "column") @field:Json(name = "column") var column: String? = null,
    @Json(name = "workId") @field:Json(name = "workId") var workId: Long? = null
)
