/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Api Documentation
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package ru.hsHelper.androidApp.rest.codegen.apis

import retrofit2.http.*
import ru.hsHelper.androidApp.rest.codegen.models.ModelAndView

@JvmSuppressWildcards
interface BasicErrorControllerApi {
    /**
     * errorHtml
     * The endpoint is owned by integration service owner
     */
    @Headers(
        "X-Operation-ID: errorHtmlUsingDELETE"
    )
    @DELETE("error")
    suspend fun errorHtmlUsingDELETE(): ModelAndView
    /**
     * errorHtml
     * The endpoint is owned by integration service owner
     */
    @Headers(
        "X-Operation-ID: errorHtmlUsingGET"
    )
    @GET("error")
    suspend fun errorHtmlUsingGET(): ModelAndView
    /**
     * errorHtml
     * The endpoint is owned by integration service owner
     */
    @Headers(
        "X-Operation-ID: errorHtmlUsingHEAD",
      "Content-Type: application/json"
    )
    @HEAD("error")
    suspend fun errorHtmlUsingHEAD(): ModelAndView
    /**
     * errorHtml
     * The endpoint is owned by integration service owner
     */
    @Headers(
        "X-Operation-ID: errorHtmlUsingOPTIONS",
      "Content-Type: application/json"
    )
    @OPTIONS("error")
    suspend fun errorHtmlUsingOPTIONS(): ModelAndView
    /**
     * errorHtml
     * The endpoint is owned by integration service owner
     */
    @Headers(
        "X-Operation-ID: errorHtmlUsingPATCH",
      "Content-Type: application/json"
    )
    @PATCH("error")
    suspend fun errorHtmlUsingPATCH(): ModelAndView
    /**
     * errorHtml
     * The endpoint is owned by integration service owner
     */
    @Headers(
        "X-Operation-ID: errorHtmlUsingPOST",
      "Content-Type: application/json"
    )
    @POST("error")
    suspend fun errorHtmlUsingPOST(): ModelAndView
    /**
     * errorHtml
     * The endpoint is owned by integration service owner
     */
    @Headers(
        "X-Operation-ID: errorHtmlUsingPUT",
      "Content-Type: application/json"
    )
    @PUT("error")
    suspend fun errorHtmlUsingPUT(): ModelAndView
}
