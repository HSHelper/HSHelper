/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: Api Documentation
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package ru.hsHelper.androidApp.rest.codegen.apis

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import ru.hsHelper.androidApp.rest.codegen.models.ObjectsWithSolutionsAddRequest
import ru.hsHelper.androidApp.rest.codegen.models.Work
import ru.hsHelper.androidApp.rest.codegen.models.WorkCreateRequest
import ru.hsHelper.androidApp.rest.codegen.models.WorkUpdateRequest

@JvmSuppressWildcards
interface WorkControllerApi {
    /**
     * addUsers
     * The endpoint is owned by integration service owner
     * @param id id (required)
     * @param objectsWithSolutionsAddRequest objectsWithSolutionsAddRequest (required)
     */
    @Headers(
        "X-Operation-ID: addUsersUsingPUT_4",
      "Content-Type: application/json"
    )
    @PUT("works/{id}/users")
    suspend fun addUsersUsingPUT4(
        @retrofit2.http.Path("id") id: Long,
        @retrofit2.http.Body objectsWithSolutionsAddRequest: ObjectsWithSolutionsAddRequest
    ): Work
    /**
     * createWork
     * The endpoint is owned by integration service owner
     * @param workCreateRequest workCreateRequest (required)
     */
    @Headers(
        "X-Operation-ID: createWorkUsingPOST",
      "Content-Type: application/json"
    )
    @POST("works/")
    suspend fun createWorkUsingPOST(
        @retrofit2.http.Body workCreateRequest: WorkCreateRequest
    ): Work
    /**
     * deleteUsers
     * The endpoint is owned by integration service owner
     * @param id id (required)
     * @param userIds userIds (required)
     */
    @Headers(
        "X-Operation-ID: deleteUsersUsingDELETE_4"
    )
    @DELETE("works/{id}/users")
    suspend fun deleteUsersUsingDELETE4(
        @retrofit2.http.Path("id") id: Long,
        @retrofit2.http.Body userIds: List<Long>
    ): Work
    /**
     * deleteWork
     * The endpoint is owned by integration service owner
     * @param id id (required)
     */
    @Headers(
        "X-Operation-ID: deleteWorkUsingDELETE"
    )
    @DELETE("works/{id}")
    suspend fun deleteWorkUsingDELETE(
        @retrofit2.http.Path("id") id: Long
    ): Unit
    /**
     * getAll
     * The endpoint is owned by integration service owner
     */
    @Headers(
        "X-Operation-ID: getAllUsingGET_5"
    )
    @GET("works/")
    suspend fun getAllUsingGET5(): List<Work>
    /**
     * getWork
     * The endpoint is owned by integration service owner
     * @param id id (required)
     */
    @Headers(
        "X-Operation-ID: getWorkUsingGET_1"
    )
    @GET("works/{id}")
    suspend fun getWorkUsingGET1(
        @retrofit2.http.Path("id") id: Long
    ): Work
    /**
     * updateWork
     * The endpoint is owned by integration service owner
     * @param id id (required)
     * @param workUpdateRequest workUpdateRequest (required)
     */
    @Headers(
        "X-Operation-ID: updateWorkUsingPUT",
      "Content-Type: application/json"
    )
    @PUT("works/{id}")
    suspend fun updateWorkUsingPUT(
        @retrofit2.http.Path("id") id: Long,
        @retrofit2.http.Body workUpdateRequest: WorkUpdateRequest
    ): Work
}