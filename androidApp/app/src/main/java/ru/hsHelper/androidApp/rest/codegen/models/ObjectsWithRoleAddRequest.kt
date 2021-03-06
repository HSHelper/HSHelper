/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Api Documentation
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package ru.hsHelper.androidApp.rest.codegen.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property objectIds
 * @property roleIds
 */
@JsonClass(generateAdapter = true)
data class ObjectsWithRoleAddRequest(
    @Json(name = "objectIds") @field:Json(name = "objectIds") var objectIds: List<Long>,
    @Json(name = "roleIds") @field:Json(name = "roleIds") var roleIds: Map<String, List<Long>>
)
