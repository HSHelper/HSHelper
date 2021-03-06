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
 * @property permissionType
 */
@JsonClass(generateAdapter = true)
data class Permissions(
    @Json(name = "id") @field:Json(name = "id") var id: Long,
    @Json(name = "permissionType") @field:Json(name = "permissionType") var permissionType: Permissions.PermissionTypeEnum
) {
    /**
     * Values: COMMENT, CREATE, UPDATE, VIEW
     */
    @JsonClass(generateAdapter = false)
    enum class PermissionTypeEnum(val value: String) {
        @Json(name = "COMMENT") COMMENT("COMMENT"),
        @Json(name = "CREATE") CREATE("CREATE"),
        @Json(name = "UPDATE") UPDATE("UPDATE"),
        @Json(name = "VIEW") VIEW("VIEW")
    }
}
