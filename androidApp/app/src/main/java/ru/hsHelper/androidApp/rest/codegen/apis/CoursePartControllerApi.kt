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
import ru.hsHelper.androidApp.rest.codegen.models.CoursePart
import ru.hsHelper.androidApp.rest.codegen.models.CoursePartCreateRequest
import ru.hsHelper.androidApp.rest.codegen.models.CoursePartUpdateRequest
import ru.hsHelper.androidApp.rest.codegen.models.ObjectsWithRoleAddRequest
import ru.hsHelper.androidApp.rest.codegen.models.UserCoursePartRole
import ru.hsHelper.androidApp.rest.codegen.models.Work

@JvmSuppressWildcards
interface CoursePartControllerApi {
    /**
     * addUsers
     * The endpoint is owned by server REST api service owner
     * @param coursePartId coursePartId (required)
     * @param objectsWithRoleAddRequest objectsWithRoleAddRequest (required)
     */
    @Headers(
        "X-Operation-ID: addUsersUsingPUT_1",
      "Content-Type: application/json"
    )
    @PUT("course-parts/{coursePartId}/users")
    suspend fun addUsersUsingPUT1(
        @retrofit2.http.Path("coursePartId") coursePartId: Long,
        @retrofit2.http.Body objectsWithRoleAddRequest: ObjectsWithRoleAddRequest
    ): CoursePart
    /**
     * createCoursePart
     * The endpoint is owned by server REST api service owner
     * @param coursePartCreateRequest coursePartCreateRequest (required)
     */
    @Headers(
        "X-Operation-ID: createCoursePartUsingPOST",
      "Content-Type: application/json"
    )
    @POST("course-parts/")
    suspend fun createCoursePartUsingPOST(
        @retrofit2.http.Body coursePartCreateRequest: CoursePartCreateRequest
    ): CoursePart
    /**
     * deleteCoursePart
     * The endpoint is owned by server REST api service owner
     * @param coursePartId coursePartId (required)
     */
    @Headers(
        "X-Operation-ID: deleteCoursePartUsingDELETE"
    )
    @DELETE("course-parts/{coursePartId}")
    suspend fun deleteCoursePartUsingDELETE(
        @retrofit2.http.Path("coursePartId") coursePartId: Long
    ): Unit
    /**
     * deleteUsers
     * The endpoint is owned by server REST api service owner
     * @param coursePartId coursePartId (required)
     * @param userIds userIds (required)
     */
    @Headers(
        "X-Operation-ID: deleteUsersUsingPOST_1",
      "Content-Type: application/json"
    )
    @POST("course-parts/{coursePartId}/users")
    suspend fun deleteUsersUsingPOST1(
        @retrofit2.http.Path("coursePartId") coursePartId: Long,
        @retrofit2.http.Body userIds: List<Long>
    ): CoursePart
    /**
     * getAllCourseParts
     * The endpoint is owned by server REST api service owner
     */
    @Headers(
        "X-Operation-ID: getAllCoursePartsUsingGET_1"
    )
    @GET("course-parts/")
    suspend fun getAllCoursePartsUsingGET1(): List<CoursePart>
    /**
     * getAllUserCoursePartRoles
     * The endpoint is owned by server REST api service owner
     * @param coursePartId coursePartId (required)
     */
    @Headers(
        "X-Operation-ID: getAllUserCoursePartRolesUsingGET"
    )
    @GET("course-parts/{coursePartId}/users")
    suspend fun getAllUserCoursePartRolesUsingGET(
        @retrofit2.http.Path("coursePartId") coursePartId: Long
    ): List<UserCoursePartRole>
    /**
     * getAllWorks
     * The endpoint is owned by server REST api service owner
     * @param coursePartId coursePartId (required)
     */
    @Headers(
        "X-Operation-ID: getAllWorksUsingGET"
    )
    @GET("course-parts/{coursePartId}/works")
    suspend fun getAllWorksUsingGET(
        @retrofit2.http.Path("coursePartId") coursePartId: Long
    ): List<Work>
    /**
     * getCoursePart
     * The endpoint is owned by server REST api service owner
     * @param coursePartId coursePartId (required)
     */
    @Headers(
        "X-Operation-ID: getCoursePartUsingGET"
    )
    @GET("course-parts/{coursePartId}")
    suspend fun getCoursePartUsingGET(
        @retrofit2.http.Path("coursePartId") coursePartId: Long
    ): CoursePart
    /**
     * getUserCoursePartRole
     * The endpoint is owned by server REST api service owner
     * @param coursePartId coursePartId (required)
     * @param userId userId (required)
     */
    @Headers(
        "X-Operation-ID: getUserCoursePartRoleUsingGET"
    )
    @GET("course-parts/{coursePartId}/users/{userId}")
    suspend fun getUserCoursePartRoleUsingGET(
        @retrofit2.http.Path("coursePartId") coursePartId: Long,
        @retrofit2.http.Path("userId") userId: Long
    ): UserCoursePartRole
    /**
     * updateCoursePart
     * The endpoint is owned by server REST api service owner
     * @param coursePartId coursePartId (required)
     * @param coursePartUpdateRequest coursePartUpdateRequest (required)
     */
    @Headers(
        "X-Operation-ID: updateCoursePartUsingPUT",
      "Content-Type: application/json"
    )
    @PUT("course-parts/{coursePartId}")
    suspend fun updateCoursePartUsingPUT(
        @retrofit2.http.Path("coursePartId") coursePartId: Long,
        @retrofit2.http.Body coursePartUpdateRequest: CoursePartUpdateRequest
    ): CoursePart
}
