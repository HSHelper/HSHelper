/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Api Documentation
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package ru.hsHelper.androidApp.rest.codegen.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property userId
 * @property workId
 */
@JsonClass(generateAdapter = true)
data class UserWorkKey(
    @Json(name = "userId") @field:Json(name = "userId") var userId: Long? = null,
    @Json(name = "workId") @field:Json(name = "workId") var workId: Long? = null
)