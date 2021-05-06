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
import ru.hsHelper.androidApp.rest.codegen.models.CourseCreateRequest
import ru.hsHelper.androidApp.rest.codegen.models.CourseUpdateRequest
import ru.hsHelper.androidApp.rest.codegen.models.ObjectsWithRoleAddRequest

@JvmSuppressWildcards
interface CourseControllerApi {
    /**
     * addUsers
     * The endpoint is owned by integration service owner
     * @param id id (required)
     * @param objectsWithRoleAddRequest objectsWithRoleAddRequest (required)
     */
    @Headers(
        "X-Operation-ID: addUsersUsingPUT",
      "Content-Type: application/json"
    )
    @PUT("courses/{id}/users")
    suspend fun addUsersUsingPUT(
        @retrofit2.http.Path("id") id: Long,
        @retrofit2.http.Body objectsWithRoleAddRequest: ObjectsWithRoleAddRequest
    ): Course
    /**
     * createCourse
     * The endpoint is owned by integration service owner
     * @param courseCreateRequest courseCreateRequest (required)
     */
    @Headers(
        "X-Operation-ID: createCourseUsingPOST",
      "Content-Type: application/json"
    )
    @POST("courses/")
    suspend fun createCourseUsingPOST(
        @retrofit2.http.Body courseCreateRequest: CourseCreateRequest
    ): Course
    /**
     * deleteCourse
     * The endpoint is owned by integration service owner
     * @param id id (required)
     */
    @Headers(
        "X-Operation-ID: deleteCourseUsingDELETE"
    )
    @DELETE("courses/{id}")
    suspend fun deleteCourseUsingDELETE(
        @retrofit2.http.Path("id") id: Long
    ): Unit
    /**
     * deleteUsers
     * The endpoint is owned by integration service owner
     * @param id id (required)
     * @param userIds userIds (required)
     */
    @Headers(
        "X-Operation-ID: deleteUsersUsingDELETE"
    )
    @DELETE("courses/{id}/users")
    suspend fun deleteUsersUsingDELETE(
        @retrofit2.http.Path("id") id: Long,
        @retrofit2.http.Body userIds: List<Long>
    ): Course
    /**
     * getAll
     * The endpoint is owned by integration service owner
     */
    @Headers(
        "X-Operation-ID: getAllUsingGET"
    )
    @GET("courses/")
    suspend fun getAllUsingGET(): List<Course>
    /**
     * getCourse
     * The endpoint is owned by integration service owner
     * @param id id (required)
     */
    @Headers(
        "X-Operation-ID: getCourseUsingGET"
    )
    @GET("courses/{id}")
    suspend fun getCourseUsingGET(
        @retrofit2.http.Path("id") id: Long
    ): Course
    /**
     * updateCourse
     * The endpoint is owned by integration service owner
     * @param courseUpdateRequest courseUpdateRequest (required)
     * @param id id (required)
     */
    @Headers(
        "X-Operation-ID: updateCourseUsingPUT",
      "Content-Type: application/json"
    )
    @PUT("courses/{id}")
    suspend fun updateCourseUsingPUT(
        @retrofit2.http.Body courseUpdateRequest: CourseUpdateRequest,
        @retrofit2.http.Path("id") id: Long
    ): Course
}