/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Api Documentation
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package ru.hsHelper.androidApp.rest.codegen.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property courseId
 * @property userId
 */
@JsonClass(generateAdapter = true)
data class UserCourseRoleKey(
    @Json(name = "courseId") @field:Json(name = "courseId") var courseId: Long? = null,
    @Json(name = "userId") @field:Json(name = "userId") var userId: Long? = null
)