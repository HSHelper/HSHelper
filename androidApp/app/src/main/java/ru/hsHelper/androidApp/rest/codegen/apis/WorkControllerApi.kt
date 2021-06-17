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
import ru.hsHelper.androidApp.rest.codegen.models.UserWork
import ru.hsHelper.androidApp.rest.codegen.models.UserWorkUpdateRequest
import ru.hsHelper.androidApp.rest.codegen.models.Work
import ru.hsHelper.androidApp.rest.codegen.models.WorkCreateRequest
import ru.hsHelper.androidApp.rest.codegen.models.WorkUpdateRequest

@JvmSuppressWildcards
interface WorkControllerApi {
    /**
     * addUsers
     * The endpoint is owned by server REST api service owner
     * @param objectsWithSolutionsAddRequest objectsWithSolutionsAddRequest (required)
     * @param workId workId (required)
     */
    @Headers(
        "X-Operation-ID: addUsersUsingPUT_4",
      "Content-Type: application/json"
    )
    @PUT("works/{workId}/users")
    suspend fun addUsersUsingPUT4(
        @retrofit2.http.Body objectsWithSolutionsAddRequest: ObjectsWithSolutionsAddRequest,
        @retrofit2.http.Path("workId") workId: Long
    ): Work
    /**
     * createWork
     * The endpoint is owned by server REST api service owner
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
     * The endpoint is owned by server REST api service owner
     * @param userIds userIds (required)
     * @param workId workId (required)
     */
    @Headers(
        "X-Operation-ID: deleteUsersUsingPOST_4",
      "Content-Type: application/json"
    )
    @POST("works/{workId}/users")
    suspend fun deleteUsersUsingPOST4(
        @retrofit2.http.Body userIds: List<Long>,
        @retrofit2.http.Path("workId") workId: Long
    ): Work
    /**
     * deleteWork
     * The endpoint is owned by server REST api service owner
     * @param workId workId (required)
     */
    @Headers(
        "X-Operation-ID: deleteWorkUsingDELETE"
    )
    @DELETE("works/{workId}")
    suspend fun deleteWorkUsingDELETE(
        @retrofit2.http.Path("workId") workId: Long
    ): Unit
    /**
     * getAllUserWorks
     * The endpoint is owned by server REST api service owner
     * @param workId workId (required)
     */
    @Headers(
        "X-Operation-ID: getAllUserWorksUsingGET_1"
    )
    @GET("works/{workId}/users")
    suspend fun getAllUserWorksUsingGET1(
        @retrofit2.http.Path("workId") workId: Long
    ): List<UserWork>
    /**
     * getAllWorks
     * The endpoint is owned by server REST api service owner
     */
    @Headers(
        "X-Operation-ID: getAllWorksUsingGET_1"
    )
    @GET("works/")
    suspend fun getAllWorksUsingGET1(): List<Work>
    /**
     * getUserWork
     * The endpoint is owned by server REST api service owner
     * @param userId userId (required)
     * @param workId workId (required)
     */
    @Headers(
        "X-Operation-ID: getUserWorkUsingGET_1"
    )
    @GET("works/{workId}/users/{userId}")
    suspend fun getUserWorkUsingGET1(
        @retrofit2.http.Path("userId") userId: Long,
        @retrofit2.http.Path("workId") workId: Long
    ): UserWork
    /**
     * getWork
     * The endpoint is owned by server REST api service owner
     * @param workId workId (required)
     */
    @Headers(
        "X-Operation-ID: getWorkUsingGET"
    )
    @GET("works/{workId}")
    suspend fun getWorkUsingGET(
        @retrofit2.http.Path("workId") workId: Long
    ): Work
    /**
     * updateUserWork
     * The endpoint is owned by server REST api service owner
     * @param userId userId (required)
     * @param userWorkUpdateRequest userWorkUpdateRequest (required)
     * @param workId workId (required)
     */
    @Headers(
        "X-Operation-ID: updateUserWorkUsingPUT_1",
      "Content-Type: application/json"
    )
    @PUT("works/{workId}/users/{userId}")
    suspend fun updateUserWorkUsingPUT1(
        @retrofit2.http.Path("userId") userId: Long,
        @retrofit2.http.Body userWorkUpdateRequest: UserWorkUpdateRequest,
        @retrofit2.http.Path("workId") workId: Long
    ): UserWork
    /**
     * updateWork
     * The endpoint is owned by server REST api service owner
     * @param workId workId (required)
     * @param workUpdateRequest workUpdateRequest (required)
     */
    @Headers(
        "X-Operation-ID: updateWorkUsingPUT",
      "Content-Type: application/json"
    )
    @PUT("works/{workId}")
    suspend fun updateWorkUsingPUT(
        @retrofit2.http.Path("workId") workId: Long,
        @retrofit2.http.Body workUpdateRequest: WorkUpdateRequest
    ): Work
}
