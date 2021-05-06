/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Api Documentation
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package ru.hsHelper.androidApp.rest.codegen.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property empty
 * @property model
 * @property modelMap
 * @property reference
 * @property status
 * @property view
 * @property viewName
 */
@JsonClass(generateAdapter = true)
data class ModelAndView(
    @Json(name = "empty") @field:Json(name = "empty") var empty: Boolean? = null,
    @Json(name = "model") @field:Json(name = "model") var model: Map<String, Any?>? = null,
    @Json(name = "modelMap") @field:Json(name = "modelMap") var modelMap: Map<String, Map<String, Any?>>? = null,
    @Json(name = "reference") @field:Json(name = "reference") var reference: Boolean? = null,
    @Json(name = "status") @field:Json(name = "status") var status: ModelAndView.StatusEnum? = null,
    @Json(name = "view") @field:Json(name = "view") var view: View? = null,
    @Json(name = "viewName") @field:Json(name = "viewName") var viewName: String? = null
) {
    /**
     * Values: ACCEPTED, ALREADY_REPORTED, BAD_GATEWAY, BAD_REQUEST, BANDWIDTH_LIMIT_EXCEEDED, CHECKPOINT, CONFLICT, CONTINUE, CREATED, DESTINATION_LOCKED, EXPECTATION_FAILED, FAILED_DEPENDENCY, FORBIDDEN, FOUND, GATEWAY_TIMEOUT, GONE, HTTP_VERSION_NOT_SUPPORTED, IM_USED, INSUFFICIENT_SPACE_ON_RESOURCE, INSUFFICIENT_STORAGE, INTERNAL_SERVER_ERROR, I_AM_A_TEAPOT, LENGTH_REQUIRED, LOCKED, LOOP_DETECTED, METHOD_FAILURE, METHOD_NOT_ALLOWED, MOVED_PERMANENTLY, MOVED_TEMPORARILY, MULTIPLE_CHOICES, MULTI_STATUS, NETWORK_AUTHENTICATION_REQUIRED, NON_AUTHORITATIVE_INFORMATION, NOT_ACCEPTABLE, NOT_EXTENDED, NOT_FOUND, NOT_IMPLEMENTED, NOT_MODIFIED, NO_CONTENT, OK, PARTIAL_CONTENT, PAYLOAD_TOO_LARGE, PAYMENT_REQUIRED, PERMANENT_REDIRECT, PRECONDITION_FAILED, PRECONDITION_REQUIRED, PROCESSING, PROXY_AUTHENTICATION_REQUIRED, REQUESTED_RANGE_NOT_SATISFIABLE, REQUEST_ENTITY_TOO_LARGE, REQUEST_HEADER_FIELDS_TOO_LARGE, REQUEST_TIMEOUT, REQUEST_URI_TOO_LONG, RESET_CONTENT, SEE_OTHER, SERVICE_UNAVAILABLE, SWITCHING_PROTOCOLS, TEMPORARY_REDIRECT, TOO_EARLY, TOO_MANY_REQUESTS, UNAUTHORIZED, UNAVAILABLE_FOR_LEGAL_REASONS, UNPROCESSABLE_ENTITY, UNSUPPORTED_MEDIA_TYPE, UPGRADE_REQUIRED, URI_TOO_LONG, USE_PROXY, VARIANT_ALSO_NEGOTIATES
     */
    @JsonClass(generateAdapter = false)
    enum class StatusEnum(val value: String) {
        @Json(name = "ACCEPTED") ACCEPTED("ACCEPTED"),
        @Json(name = "ALREADY_REPORTED") ALREADY_REPORTED("ALREADY_REPORTED"),
        @Json(name = "BAD_GATEWAY") BAD_GATEWAY("BAD_GATEWAY"),
        @Json(name = "BAD_REQUEST") BAD_REQUEST("BAD_REQUEST"),
        @Json(name = "BANDWIDTH_LIMIT_EXCEEDED") BANDWIDTH_LIMIT_EXCEEDED("BANDWIDTH_LIMIT_EXCEEDED"),
        @Json(name = "CHECKPOINT") CHECKPOINT("CHECKPOINT"),
        @Json(name = "CONFLICT") CONFLICT("CONFLICT"),
        @Json(name = "CONTINUE") CONTINUE("CONTINUE"),
        @Json(name = "CREATED") CREATED("CREATED"),
        @Json(name = "DESTINATION_LOCKED") DESTINATION_LOCKED("DESTINATION_LOCKED"),
        @Json(name = "EXPECTATION_FAILED") EXPECTATION_FAILED("EXPECTATION_FAILED"),
        @Json(name = "FAILED_DEPENDENCY") FAILED_DEPENDENCY("FAILED_DEPENDENCY"),
        @Json(name = "FORBIDDEN") FORBIDDEN("FORBIDDEN"),
        @Json(name = "FOUND") FOUND("FOUND"),
        @Json(name = "GATEWAY_TIMEOUT") GATEWAY_TIMEOUT("GATEWAY_TIMEOUT"),
        @Json(name = "GONE") GONE("GONE"),
        @Json(name = "HTTP_VERSION_NOT_SUPPORTED") HTTP_VERSION_NOT_SUPPORTED("HTTP_VERSION_NOT_SUPPORTED"),
        @Json(name = "IM_USED") IM_USED("IM_USED"),
        @Json(name = "INSUFFICIENT_SPACE_ON_RESOURCE") INSUFFICIENT_SPACE_ON_RESOURCE("INSUFFICIENT_SPACE_ON_RESOURCE"),
        @Json(name = "INSUFFICIENT_STORAGE") INSUFFICIENT_STORAGE("INSUFFICIENT_STORAGE"),
        @Json(name = "INTERNAL_SERVER_ERROR") INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
        @Json(name = "I_AM_A_TEAPOT") I_AM_A_TEAPOT("I_AM_A_TEAPOT"),
        @Json(name = "LENGTH_REQUIRED") LENGTH_REQUIRED("LENGTH_REQUIRED"),
        @Json(name = "LOCKED") LOCKED("LOCKED"),
        @Json(name = "LOOP_DETECTED") LOOP_DETECTED("LOOP_DETECTED"),
        @Json(name = "METHOD_FAILURE") METHOD_FAILURE("METHOD_FAILURE"),
        @Json(name = "METHOD_NOT_ALLOWED") METHOD_NOT_ALLOWED("METHOD_NOT_ALLOWED"),
        @Json(name = "MOVED_PERMANENTLY") MOVED_PERMANENTLY("MOVED_PERMANENTLY"),
        @Json(name = "MOVED_TEMPORARILY") MOVED_TEMPORARILY("MOVED_TEMPORARILY"),
        @Json(name = "MULTIPLE_CHOICES") MULTIPLE_CHOICES("MULTIPLE_CHOICES"),
        @Json(name = "MULTI_STATUS") MULTI_STATUS("MULTI_STATUS"),
        @Json(name = "NETWORK_AUTHENTICATION_REQUIRED") NETWORK_AUTHENTICATION_REQUIRED("NETWORK_AUTHENTICATION_REQUIRED"),
        @Json(name = "NON_AUTHORITATIVE_INFORMATION") NON_AUTHORITATIVE_INFORMATION("NON_AUTHORITATIVE_INFORMATION"),
        @Json(name = "NOT_ACCEPTABLE") NOT_ACCEPTABLE("NOT_ACCEPTABLE"),
        @Json(name = "NOT_EXTENDED") NOT_EXTENDED("NOT_EXTENDED"),
        @Json(name = "NOT_FOUND") NOT_FOUND("NOT_FOUND"),
        @Json(name = "NOT_IMPLEMENTED") NOT_IMPLEMENTED("NOT_IMPLEMENTED"),
        @Json(name = "NOT_MODIFIED") NOT_MODIFIED("NOT_MODIFIED"),
        @Json(name = "NO_CONTENT") NO_CONTENT("NO_CONTENT"),
        @Json(name = "OK") OK("OK"),
        @Json(name = "PARTIAL_CONTENT") PARTIAL_CONTENT("PARTIAL_CONTENT"),
        @Json(name = "PAYLOAD_TOO_LARGE") PAYLOAD_TOO_LARGE("PAYLOAD_TOO_LARGE"),
        @Json(name = "PAYMENT_REQUIRED") PAYMENT_REQUIRED("PAYMENT_REQUIRED"),
        @Json(name = "PERMANENT_REDIRECT") PERMANENT_REDIRECT("PERMANENT_REDIRECT"),
        @Json(name = "PRECONDITION_FAILED") PRECONDITION_FAILED("PRECONDITION_FAILED"),
        @Json(name = "PRECONDITION_REQUIRED") PRECONDITION_REQUIRED("PRECONDITION_REQUIRED"),
        @Json(name = "PROCESSING") PROCESSING("PROCESSING"),
        @Json(name = "PROXY_AUTHENTICATION_REQUIRED") PROXY_AUTHENTICATION_REQUIRED("PROXY_AUTHENTICATION_REQUIRED"),
        @Json(name = "REQUESTED_RANGE_NOT_SATISFIABLE") REQUESTED_RANGE_NOT_SATISFIABLE("REQUESTED_RANGE_NOT_SATISFIABLE"),
        @Json(name = "REQUEST_ENTITY_TOO_LARGE") REQUEST_ENTITY_TOO_LARGE("REQUEST_ENTITY_TOO_LARGE"),
        @Json(name = "REQUEST_HEADER_FIELDS_TOO_LARGE") REQUEST_HEADER_FIELDS_TOO_LARGE("REQUEST_HEADER_FIELDS_TOO_LARGE"),
        @Json(name = "REQUEST_TIMEOUT") REQUEST_TIMEOUT("REQUEST_TIMEOUT"),
        @Json(name = "REQUEST_URI_TOO_LONG") REQUEST_URI_TOO_LONG("REQUEST_URI_TOO_LONG"),
        @Json(name = "RESET_CONTENT") RESET_CONTENT("RESET_CONTENT"),
        @Json(name = "SEE_OTHER") SEE_OTHER("SEE_OTHER"),
        @Json(name = "SERVICE_UNAVAILABLE") SERVICE_UNAVAILABLE("SERVICE_UNAVAILABLE"),
        @Json(name = "SWITCHING_PROTOCOLS") SWITCHING_PROTOCOLS("SWITCHING_PROTOCOLS"),
        @Json(name = "TEMPORARY_REDIRECT") TEMPORARY_REDIRECT("TEMPORARY_REDIRECT"),
        @Json(name = "TOO_EARLY") TOO_EARLY("TOO_EARLY"),
        @Json(name = "TOO_MANY_REQUESTS") TOO_MANY_REQUESTS("TOO_MANY_REQUESTS"),
        @Json(name = "UNAUTHORIZED") UNAUTHORIZED("UNAUTHORIZED"),
        @Json(name = "UNAVAILABLE_FOR_LEGAL_REASONS") UNAVAILABLE_FOR_LEGAL_REASONS("UNAVAILABLE_FOR_LEGAL_REASONS"),
        @Json(name = "UNPROCESSABLE_ENTITY") UNPROCESSABLE_ENTITY("UNPROCESSABLE_ENTITY"),
        @Json(name = "UNSUPPORTED_MEDIA_TYPE") UNSUPPORTED_MEDIA_TYPE("UNSUPPORTED_MEDIA_TYPE"),
        @Json(name = "UPGRADE_REQUIRED") UPGRADE_REQUIRED("UPGRADE_REQUIRED"),
        @Json(name = "URI_TOO_LONG") URI_TOO_LONG("URI_TOO_LONG"),
        @Json(name = "USE_PROXY") USE_PROXY("USE_PROXY"),
        @Json(name = "VARIANT_ALSO_NEGOTIATES") VARIANT_ALSO_NEGOTIATES("VARIANT_ALSO_NEGOTIATES")
    }
}
