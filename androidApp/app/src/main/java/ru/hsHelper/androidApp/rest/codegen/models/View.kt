/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Api Documentation
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package ru.hsHelper.androidApp.rest.codegen.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property contentType
 */
@JsonClass(generateAdapter = true)
data class View(
    @Json(name = "contentType") @field:Json(name = "contentType") var contentType: String? = null
)
