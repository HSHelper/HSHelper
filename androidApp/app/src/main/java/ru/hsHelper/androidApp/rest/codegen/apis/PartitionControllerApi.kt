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
import ru.hsHelper.androidApp.rest.codegen.models.Course
import ru.hsHelper.androidApp.rest.codegen.models.CoursePart
import ru.hsHelper.androidApp.rest.codegen.models.Partition
import ru.hsHelper.androidApp.rest.codegen.models.PartitionAddRequest
import ru.hsHelper.androidApp.rest.codegen.models.PartitionCreateRequest
import ru.hsHelper.androidApp.rest.codegen.models.PartitionUpdateRequest
import ru.hsHelper.androidApp.rest.codegen.models.UserToPartition

@JvmSuppressWildcards
interface PartitionControllerApi {
    /**
     * addUsers
     * The endpoint is owned by server REST api service owner
     * @param id id (required)
     * @param partitionAddRequest partitionAddRequest (required)
     */
    @Headers(
        "X-Operation-ID: addUsersUsingPUT_3",
      "Content-Type: application/json"
    )
    @PUT("partitions/{id}/users")
    suspend fun addUsersUsingPUT3(
        @retrofit2.http.Path("id") id: Long,
        @retrofit2.http.Body partitionAddRequest: PartitionAddRequest
    ): Partition
    /**
     * createPartition
     * The endpoint is owned by server REST api service owner
     * @param partitionCreateRequest partitionCreateRequest (required)
     */
    @Headers(
        "X-Operation-ID: createPartitionUsingPOST",
      "Content-Type: application/json"
    )
    @POST("partitions/")
    suspend fun createPartitionUsingPOST(
        @retrofit2.http.Body partitionCreateRequest: PartitionCreateRequest
    ): Partition
    /**
     * deletePartition
     * The endpoint is owned by server REST api service owner
     * @param id id (required)
     */
    @Headers(
        "X-Operation-ID: deletePartitionUsingDELETE"
    )
    @DELETE("partitions/{id}")
    suspend fun deletePartitionUsingDELETE(
        @retrofit2.http.Path("id") id: Long
    ): Unit
    /**
     * deleteUsers
     * The endpoint is owned by server REST api service owner
     * @param id id (required)
     * @param userIds userIds (required)
     */
    @Headers(
        "X-Operation-ID: deleteUsersUsingDELETE_3"
    )
    @DELETE("partitions/{id}/users")
    suspend fun deleteUsersUsingDELETE3(
        @retrofit2.http.Path("id") id: Long,
        @retrofit2.http.Body userIds: List<Long>
    ): Partition
    /**
     * getAllCourseParts
     * The endpoint is owned by server REST api service owner
     * @param partitionId partitionId (required)
     */
    @Headers(
        "X-Operation-ID: getAllCoursePartsUsingGET_1"
    )
    @GET("partitions/{partitionId}/course-parts")
    suspend fun getAllCoursePartsUsingGET1(
        @retrofit2.http.Path("partitionId") partitionId: Long
    ): List<CoursePart>
    /**
     * getAllCoursesWithSuchDefaultPartition
     * The endpoint is owned by server REST api service owner
     * @param partitionId partitionId (required)
     */
    @Headers(
        "X-Operation-ID: getAllCoursesWithSuchDefaultPartitionUsingGET"
    )
    @GET("partitions/{partitionId}/courses")
    suspend fun getAllCoursesWithSuchDefaultPartitionUsingGET(
        @retrofit2.http.Path("partitionId") partitionId: Long
    ): List<Course>
    /**
     * getAllUsers
     * The endpoint is owned by server REST api service owner
     * @param partitionId partitionId (required)
     */
    @Headers(
        "X-Operation-ID: getAllUsersUsingGET_3"
    )
    @GET("partitions/{partitionId}/users")
    suspend fun getAllUsersUsingGET3(
        @retrofit2.http.Path("partitionId") partitionId: Long
    ): List<UserToPartition>
    /**
     * getAll
     * The endpoint is owned by server REST api service owner
     */
    @Headers(
        "X-Operation-ID: getAllUsingGET_3"
    )
    @GET("partitions/")
    suspend fun getAllUsingGET3(): List<Partition>
    /**
     * getPartition
     * The endpoint is owned by server REST api service owner
     * @param id id (required)
     */
    @Headers(
        "X-Operation-ID: getPartitionUsingGET"
    )
    @GET("partitions/{id}")
    suspend fun getPartitionUsingGET(
        @retrofit2.http.Path("id") id: Long
    ): Partition
    /**
     * getUser
     * The endpoint is owned by server REST api service owner
     * @param partitionId partitionId (required)
     * @param userId userId (required)
     */
    @Headers(
        "X-Operation-ID: getUserUsingGET_3"
    )
    @GET("partitions/{partitionId}/users/{userId}")
    suspend fun getUserUsingGET3(
        @retrofit2.http.Path("partitionId") partitionId: Long,
        @retrofit2.http.Path("userId") userId: Long
    ): UserToPartition
    /**
     * updatePartition
     * The endpoint is owned by server REST api service owner
     * @param id id (required)
     * @param partitionUpdateRequest partitionUpdateRequest (required)
     */
    @Headers(
        "X-Operation-ID: updatePartitionUsingPUT",
      "Content-Type: application/json"
    )
    @PUT("partitions/{id}")
    suspend fun updatePartitionUsingPUT(
        @retrofit2.http.Path("id") id: Long,
        @retrofit2.http.Body partitionUpdateRequest: PartitionUpdateRequest
    ): Partition
}
