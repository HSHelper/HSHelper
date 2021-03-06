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
import ru.hsHelper.androidApp.rest.codegen.models.CoursePart
import ru.hsHelper.androidApp.rest.codegen.models.CourseUpdateRequest
import ru.hsHelper.androidApp.rest.codegen.models.ObjectsWithRoleAddRequest
import ru.hsHelper.androidApp.rest.codegen.models.UserCourseRole

@JvmSuppressWildcards
interface CourseControllerApi {
    /**
     * addUsers
     * The endpoint is owned by server REST api service owner
     * @param courseId courseId (required)
     * @param objectsWithRoleAddRequest objectsWithRoleAddRequest (required)
     */
    @Headers(
        "X-Operation-ID: addUsersUsingPUT",
      "Content-Type: application/json"
    )
    @PUT("courses/{courseId}/users")
    suspend fun addUsersUsingPUT(
        @retrofit2.http.Path("courseId") courseId: Long,
        @retrofit2.http.Body objectsWithRoleAddRequest: ObjectsWithRoleAddRequest
    ): Course
    /**
     * createCourse
     * The endpoint is owned by server REST api service owner
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
     * The endpoint is owned by server REST api service owner
     * @param courseId courseId (required)
     */
    @Headers(
        "X-Operation-ID: deleteCourseUsingDELETE"
    )
    @DELETE("courses/{courseId}")
    suspend fun deleteCourseUsingDELETE(
        @retrofit2.http.Path("courseId") courseId: Long
    ): Unit
    /**
     * deleteUsers
     * The endpoint is owned by server REST api service owner
     * @param courseId courseId (required)
     * @param userIds userIds (required)
     */
    @Headers(
        "X-Operation-ID: deleteUsersUsingPOST",
      "Content-Type: application/json"
    )
    @POST("courses/{courseId}/users")
    suspend fun deleteUsersUsingPOST(
        @retrofit2.http.Path("courseId") courseId: Long,
        @retrofit2.http.Body userIds: List<Long>
    ): Course
    /**
     * getAllCourseParts
     * The endpoint is owned by server REST api service owner
     * @param courseId courseId (required)
     */
    @Headers(
        "X-Operation-ID: getAllCoursePartsUsingGET"
    )
    @GET("courses/{courseId}/course-parts")
    suspend fun getAllCoursePartsUsingGET(
        @retrofit2.http.Path("courseId") courseId: Long
    ): List<CoursePart>
    /**
     * getAllCourses
     * The endpoint is owned by server REST api service owner
     */
    @Headers(
        "X-Operation-ID: getAllCoursesUsingGET"
    )
    @GET("courses/")
    suspend fun getAllCoursesUsingGET(): List<Course>
    /**
     * getAllUserCourseRoles
     * The endpoint is owned by server REST api service owner
     * @param courseId courseId (required)
     */
    @Headers(
        "X-Operation-ID: getAllUserCourseRolesUsingGET"
    )
    @GET("courses/{courseId}/users")
    suspend fun getAllUserCourseRolesUsingGET(
        @retrofit2.http.Path("courseId") courseId: Long
    ): List<UserCourseRole>
    /**
     * getCourse
     * The endpoint is owned by server REST api service owner
     * @param courseId courseId (required)
     */
    @Headers(
        "X-Operation-ID: getCourseUsingGET"
    )
    @GET("courses/{courseId}")
    suspend fun getCourseUsingGET(
        @retrofit2.http.Path("courseId") courseId: Long
    ): Course
    /**
     * getUserCourseRole
     * The endpoint is owned by server REST api service owner
     * @param courseId courseId (required)
     * @param userId userId (required)
     */
    @Headers(
        "X-Operation-ID: getUserCourseRoleUsingGET"
    )
    @GET("courses/{courseId}/users/{userId}")
    suspend fun getUserCourseRoleUsingGET(
        @retrofit2.http.Path("courseId") courseId: Long,
        @retrofit2.http.Path("userId") userId: Long
    ): UserCourseRole
    /**
     * updateCourse
     * The endpoint is owned by server REST api service owner
     * @param courseId courseId (required)
     * @param courseUpdateRequest courseUpdateRequest (required)
     */
    @Headers(
        "X-Operation-ID: updateCourseUsingPUT",
      "Content-Type: application/json"
    )
    @PUT("courses/{courseId}")
    suspend fun updateCourseUsingPUT(
        @retrofit2.http.Path("courseId") courseId: Long,
        @retrofit2.http.Body courseUpdateRequest: CourseUpdateRequest
    ): Course
}
