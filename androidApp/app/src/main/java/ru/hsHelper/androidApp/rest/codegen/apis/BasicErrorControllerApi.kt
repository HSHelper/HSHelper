/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Api Documentation
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package ru.hsHelper.androidApp.rest.codegen.apis

import retrofit2.http.*

@JvmSuppressWildcards
interface BasicErrorControllerApi {
    /**
     * error
     * The endpoint is owned by server REST api service owner
     */
    @Headers(
        "X-Operation-ID: errorUsingDELETE"
    )
    @DELETE("error")
    suspend fun errorUsingDELETE(): Map<String, Map<String, Any?>>
    /**
     * error
     * The endpoint is owned by server REST api service owner
     */
    @Headers(
        "X-Operation-ID: errorUsingGET"
    )
    @GET("error")
    suspend fun errorUsingGET(): Map<String, Map<String, Any?>>
    /**
     * error
     * The endpoint is owned by server REST api service owner
     */
    @Headers(
        "X-Operation-ID: errorUsingHEAD",
      "Content-Type: application/json"
    )
    @HEAD("error")
    suspend fun errorUsingHEAD(): Map<String, Map<String, Any?>>
    /**
     * error
     * The endpoint is owned by server REST api service owner
     */
    @Headers(
        "X-Operation-ID: errorUsingOPTIONS",
      "Content-Type: application/json"
    )
    @OPTIONS("error")
    suspend fun errorUsingOPTIONS(): Map<String, Map<String, Any?>>
    /**
     * error
     * The endpoint is owned by server REST api service owner
     */
    @Headers(
        "X-Operation-ID: errorUsingPATCH",
      "Content-Type: application/json"
    )
    @PATCH("error")
    suspend fun errorUsingPATCH(): Map<String, Map<String, Any?>>
    /**
     * error
     * The endpoint is owned by server REST api service owner
     */
    @Headers(
        "X-Operation-ID: errorUsingPOST",
      "Content-Type: application/json"
    )
    @POST("error")
    suspend fun errorUsingPOST(): Map<String, Map<String, Any?>>
    /**
     * error
     * The endpoint is owned by server REST api service owner
     */
    @Headers(
        "X-Operation-ID: errorUsingPUT",
      "Content-Type: application/json"
    )
    @PUT("error")
    suspend fun errorUsingPUT(): Map<String, Map<String, Any?>>
}
