/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Api Documentation
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package ru.hsHelper.androidApp.rest.codegen.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property name
 * @property partitionId
 */
@JsonClass(generateAdapter = true)
data class CourseUpdateRequest(
    @Json(name = "name") @field:Json(name = "name") var name: String,
    @Json(name = "partitionId") @field:Json(name = "partitionId") var partitionId: Long
)
