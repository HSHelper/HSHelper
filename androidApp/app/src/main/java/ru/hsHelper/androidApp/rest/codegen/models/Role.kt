/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Api Documentation
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package ru.hsHelper.androidApp.rest.codegen.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property id
 * @property permissions
 * @property roleType
 */
@JsonClass(generateAdapter = true)
data class Role(
    @Json(name = "id") @field:Json(name = "id") var id: Long,
    @Json(name = "permissions") @field:Json(name = "permissions") var permissions: List<Permissions>,
    @Json(name = "roleType") @field:Json(name = "roleType") var roleType: Role.RoleTypeEnum
) {
    /**
     * Values: ADMIN, OBSERVER, STUDENT, TEACHER
     */
    @JsonClass(generateAdapter = false)
    enum class RoleTypeEnum(val value: String) {
        @Json(name = "ADMIN") ADMIN("ADMIN"),
        @Json(name = "OBSERVER") OBSERVER("OBSERVER"),
        @Json(name = "STUDENT") STUDENT("STUDENT"),
        @Json(name = "TEACHER") TEACHER("TEACHER")
    }
}
