/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Api Documentation
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package ru.hsHelper.androidApp.rest.codegen.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.ZonedDateTime

/**
 * @property block
 * @property coursePart
 * @property deadline
 * @property description
 * @property id
 * @property name
 * @property weight
 * @property workType
 */
@JsonClass(generateAdapter = true)
data class Work(
    @Json(name = "block") @field:Json(name = "block") var block: Double? = null,
    @Json(name = "coursePart") @field:Json(name = "coursePart") var coursePart: CoursePart? = null,
    @Json(name = "deadline") @field:Json(name = "deadline") var deadline: ZonedDateTime? = null,
    @Json(name = "description") @field:Json(name = "description") var description: String? = null,
    @Json(name = "id") @field:Json(name = "id") var id: Long? = null,
    @Json(name = "name") @field:Json(name = "name") var name: String? = null,
    @Json(name = "weight") @field:Json(name = "weight") var weight: Double? = null,
    @Json(name = "workType") @field:Json(name = "workType") var workType: Work.WorkTypeEnum? = null
) {
    /**
     * Values: CONTROLWORK, HOMEWORK
     */
    @JsonClass(generateAdapter = false)
    enum class WorkTypeEnum(val value: String) {
        @Json(name = "CONTROLWORK") CONTROLWORK("CONTROLWORK"),
        @Json(name = "HOMEWORK") HOMEWORK("HOMEWORK")
    }
}
