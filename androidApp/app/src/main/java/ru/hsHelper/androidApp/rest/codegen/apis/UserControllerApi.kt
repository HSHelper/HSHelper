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
import ru.hsHelper.androidApp.rest.codegen.models.Notification
import ru.hsHelper.androidApp.rest.codegen.models.ObjectsWithRoleAddRequest
import ru.hsHelper.androidApp.rest.codegen.models.ObjectsWithSolutionsAddRequest
import ru.hsHelper.androidApp.rest.codegen.models.PartitionAddRequest
import ru.hsHelper.androidApp.rest.codegen.models.User
import ru.hsHelper.androidApp.rest.codegen.models.UserCoursePartRole
import ru.hsHelper.androidApp.rest.codegen.models.UserCourseRole
import ru.hsHelper.androidApp.rest.codegen.models.UserCreateRequest
import ru.hsHelper.androidApp.rest.codegen.models.UserGroupRole
import ru.hsHelper.androidApp.rest.codegen.models.UserToPartition
import ru.hsHelper.androidApp.rest.codegen.models.UserUpdateRequest
import ru.hsHelper.androidApp.rest.codegen.models.UserWork
import ru.hsHelper.androidApp.rest.codegen.models.UserWorkUpdateRequest

@JvmSuppressWildcards
interface UserControllerApi {
    /**
     * addCourseParts
     * The endpoint is owned by server REST api service owner
     * @param id id (required)
     * @param objectsWithRoleAddRequest objectsWithRoleAddRequest (required)
     */
    @Headers(
        "X-Operation-ID: addCoursePartsUsingPUT",
      "Content-Type: application/json"
    )
    @PUT("users/{id}/course-parts")
    suspend fun addCoursePartsUsingPUT(
        @retrofit2.http.Path("id") id: Long,
        @retrofit2.http.Body objectsWithRoleAddRequest: ObjectsWithRoleAddRequest
    ): User
    /**
     * addCourses
     * The endpoint is owned by server REST api service owner
     * @param id id (required)
     * @param objectsWithRoleAddRequest objectsWithRoleAddRequest (required)
     */
    @Headers(
        "X-Operation-ID: addCoursesUsingPUT",
      "Content-Type: application/json"
    )
    @PUT("users/{id}/courses")
    suspend fun addCoursesUsingPUT(
        @retrofit2.http.Path("id") id: Long,
        @retrofit2.http.Body objectsWithRoleAddRequest: ObjectsWithRoleAddRequest
    ): User
    /**
     * addGroups
     * The endpoint is owned by server REST api service owner
     * @param id id (required)
     * @param objectsWithRoleAddRequest objectsWithRoleAddRequest (required)
     */
    @Headers(
        "X-Operation-ID: addGroupsUsingPUT",
      "Content-Type: application/json"
    )
    @PUT("users/{id}/groups")
    suspend fun addGroupsUsingPUT(
        @retrofit2.http.Path("id") id: Long,
        @retrofit2.http.Body objectsWithRoleAddRequest: ObjectsWithRoleAddRequest
    ): User
    /**
     * addNotifications
     * The endpoint is owned by server REST api service owner
     * @param notificationIds notificationIds (required)
     * @param userId userId (required)
     */
    @Headers(
        "X-Operation-ID: addNotificationsUsingPUT",
      "Content-Type: application/json"
    )
    @PUT("users/{userId}/notifications")
    suspend fun addNotificationsUsingPUT(
        @retrofit2.http.Body notificationIds: List<Long>,
        @retrofit2.http.Path("userId") userId: Long
    ): User
    /**
     * addToPartitions
     * The endpoint is owned by server REST api service owner
     * @param id id (required)
     * @param partitionAddRequest partitionAddRequest (required)
     */
    @Headers(
        "X-Operation-ID: addToPartitionsUsingPUT",
      "Content-Type: application/json"
    )
    @PUT("users/{id}/partitions")
    suspend fun addToPartitionsUsingPUT(
        @retrofit2.http.Path("id") id: Long,
        @retrofit2.http.Body partitionAddRequest: PartitionAddRequest
    ): User
    /**
     * addWorks
     * The endpoint is owned by server REST api service owner
     * @param id id (required)
     * @param objectsWithSolutionsAddRequest objectsWithSolutionsAddRequest (required)
     */
    @Headers(
        "X-Operation-ID: addWorksUsingPUT",
      "Content-Type: application/json"
    )
    @PUT("users/{id}/works")
    suspend fun addWorksUsingPUT(
        @retrofit2.http.Path("id") id: Long,
        @retrofit2.http.Body objectsWithSolutionsAddRequest: ObjectsWithSolutionsAddRequest
    ): User
    /**
     * createUser
     * The endpoint is owned by server REST api service owner
     * @param userCreateRequest userCreateRequest (required)
     */
    @Headers(
        "X-Operation-ID: createUserUsingPOST",
      "Content-Type: application/json"
    )
    @POST("users/")
    suspend fun createUserUsingPOST(
        @retrofit2.http.Body userCreateRequest: UserCreateRequest
    ): User
    /**
     * deleteCourseParts
     * The endpoint is owned by server REST api service owner
     * @param coursePartIds coursePartIds (required)
     * @param id id (required)
     */
    @Headers(
        "X-Operation-ID: deleteCoursePartsUsingPOST",
      "Content-Type: application/json"
    )
    @POST("users/{id}/course-parts")
    suspend fun deleteCoursePartsUsingPOST(
        @retrofit2.http.Body coursePartIds: List<Long>,
        @retrofit2.http.Path("id") id: Long
    ): User
    /**
     * deleteCourses
     * The endpoint is owned by server REST api service owner
     * @param courseIds courseIds (required)
     * @param id id (required)
     */
    @Headers(
        "X-Operation-ID: deleteCoursesUsingPOST",
      "Content-Type: application/json"
    )
    @POST("users/{id}/courses")
    suspend fun deleteCoursesUsingPOST(
        @retrofit2.http.Body courseIds: List<Long>,
        @retrofit2.http.Path("id") id: Long
    ): User
    /**
     * deleteGroups
     * The endpoint is owned by server REST api service owner
     * @param groupIds groupIds (required)
     * @param id id (required)
     */
    @Headers(
        "X-Operation-ID: deleteGroupsUsingPOST",
      "Content-Type: application/json"
    )
    @POST("users/{id}/groups")
    suspend fun deleteGroupsUsingPOST(
        @retrofit2.http.Body groupIds: List<Long>,
        @retrofit2.http.Path("id") id: Long
    ): User
    /**
     * deleteNotifications
     * The endpoint is owned by server REST api service owner
     * @param notificationIds notificationIds (required)
     * @param userId userId (required)
     */
    @Headers(
        "X-Operation-ID: deleteNotificationsUsingPOST",
      "Content-Type: application/json"
    )
    @POST("users/{userId}/notifications")
    suspend fun deleteNotificationsUsingPOST(
        @retrofit2.http.Body notificationIds: List<Long>,
        @retrofit2.http.Path("userId") userId: Long
    ): User
    /**
     * deletePartitions
     * The endpoint is owned by server REST api service owner
     * @param id id (required)
     * @param partitionIds partitionIds (required)
     */
    @Headers(
        "X-Operation-ID: deletePartitionsUsingPOST",
      "Content-Type: application/json"
    )
    @POST("users/{id}/partitions")
    suspend fun deletePartitionsUsingPOST(
        @retrofit2.http.Path("id") id: Long,
        @retrofit2.http.Body partitionIds: List<Long>
    ): User
    /**
     * deleteUser
     * The endpoint is owned by server REST api service owner
     * @param id id (required)
     */
    @Headers(
        "X-Operation-ID: deleteUserUsingDELETE"
    )
    @DELETE("users/{id}")
    suspend fun deleteUserUsingDELETE(
        @retrofit2.http.Path("id") id: Long
    ): Unit
    /**
     * deleteWorks
     * The endpoint is owned by server REST api service owner
     * @param groupIds groupIds (required)
     * @param id id (required)
     */
    @Headers(
        "X-Operation-ID: deleteWorksUsingPOST",
      "Content-Type: application/json"
    )
    @POST("users/{id}/works")
    suspend fun deleteWorksUsingPOST(
        @retrofit2.http.Body groupIds: List<Long>,
        @retrofit2.http.Path("id") id: Long
    ): User
    /**
     * getAllCourseParts
     * The endpoint is owned by server REST api service owner
     * @param userId userId (required)
     */
    @Headers(
        "X-Operation-ID: getAllCoursePartsUsingGET_2"
    )
    @GET("users/{userId}/course-parts")
    suspend fun getAllCoursePartsUsingGET2(
        @retrofit2.http.Path("userId") userId: Long
    ): List<UserCoursePartRole>
    /**
     * getAllCourses
     * The endpoint is owned by server REST api service owner
     * @param userId userId (required)
     */
    @Headers(
        "X-Operation-ID: getAllCoursesUsingGET_1"
    )
    @GET("users/{userId}/courses")
    suspend fun getAllCoursesUsingGET1(
        @retrofit2.http.Path("userId") userId: Long
    ): List<UserCourseRole>
    /**
     * getAllGroups
     * The endpoint is owned by server REST api service owner
     * @param userId userId (required)
     */
    @Headers(
        "X-Operation-ID: getAllGroupsUsingGET"
    )
    @GET("users/{userId}/groups")
    suspend fun getAllGroupsUsingGET(
        @retrofit2.http.Path("userId") userId: Long
    ): List<UserGroupRole>
    /**
     * getAllNotifications
     * The endpoint is owned by server REST api service owner
     * @param id id (required)
     */
    @Headers(
        "X-Operation-ID: getAllNotificationsUsingGET_1"
    )
    @GET("users/{id}/notifications")
    suspend fun getAllNotificationsUsingGET1(
        @retrofit2.http.Path("id") id: Long
    ): List<Notification>
    /**
     * getAllPartitions
     * The endpoint is owned by server REST api service owner
     * @param userId userId (required)
     */
    @Headers(
        "X-Operation-ID: getAllPartitionsUsingGET_1"
    )
    @GET("users/{userId}/partitions")
    suspend fun getAllPartitionsUsingGET1(
        @retrofit2.http.Path("userId") userId: Long
    ): List<UserToPartition>
    /**
     * getAll
     * The endpoint is owned by server REST api service owner
     */
    @Headers(
        "X-Operation-ID: getAllUsingGET_4"
    )
    @GET("users/")
    suspend fun getAllUsingGET4(): List<User>
    /**
     * getAllWorks
     * The endpoint is owned by server REST api service owner
     * @param userId userId (required)
     */
    @Headers(
        "X-Operation-ID: getAllWorksUsingGET_1"
    )
    @GET("users/{userId}/works")
    suspend fun getAllWorksUsingGET1(
        @retrofit2.http.Path("userId") userId: Long
    ): List<UserWork>
    /**
     * getCoursePart
     * The endpoint is owned by server REST api service owner
     * @param coursePartId coursePartId (required)
     * @param userId userId (required)
     */
    @Headers(
        "X-Operation-ID: getCoursePartUsingGET_1"
    )
    @GET("users/{userId}/course-parts/{coursePartId}")
    suspend fun getCoursePartUsingGET1(
        @retrofit2.http.Path("coursePartId") coursePartId: Long,
        @retrofit2.http.Path("userId") userId: Long
    ): UserCoursePartRole
    /**
     * getCourse
     * The endpoint is owned by server REST api service owner
     * @param courseId courseId (required)
     * @param userId userId (required)
     */
    @Headers(
        "X-Operation-ID: getCourseUsingGET_1"
    )
    @GET("users/{userId}/courses/{courseId}")
    suspend fun getCourseUsingGET1(
        @retrofit2.http.Path("courseId") courseId: Long,
        @retrofit2.http.Path("userId") userId: Long
    ): UserCourseRole
    /**
     * getGroup
     * The endpoint is owned by server REST api service owner
     * @param groupId groupId (required)
     * @param userId userId (required)
     */
    @Headers(
        "X-Operation-ID: getGroupUsingGET_1"
    )
    @GET("users/{userId}/groups/{groupId}")
    suspend fun getGroupUsingGET1(
        @retrofit2.http.Path("groupId") groupId: Long,
        @retrofit2.http.Path("userId") userId: Long
    ): UserGroupRole
    /**
     * getPartition
     * The endpoint is owned by server REST api service owner
     * @param partitionId partitionId (required)
     * @param userId userId (required)
     */
    @Headers(
        "X-Operation-ID: getPartitionUsingGET_1"
    )
    @GET("users/{userId}/partitions/{partitionId}")
    suspend fun getPartitionUsingGET1(
        @retrofit2.http.Path("partitionId") partitionId: Long,
        @retrofit2.http.Path("userId") userId: Long
    ): UserToPartition
    /**
     * getUserByEmail
     * The endpoint is owned by server REST api service owner
     * @param email email (required)
     */
    @Headers(
        "X-Operation-ID: getUserByEmailUsingGET"
    )
    @GET("users/email")
    suspend fun getUserByEmailUsingGET(
        @retrofit2.http.Query("email") email: String
    ): User
    /**
     * getUser
     * The endpoint is owned by server REST api service owner
     * @param id id (required)
     */
    @Headers(
        "X-Operation-ID: getUserUsingGET_4"
    )
    @GET("users/{id}")
    suspend fun getUserUsingGET4(
        @retrofit2.http.Path("id") id: Long
    ): User
    /**
     * getWork
     * The endpoint is owned by server REST api service owner
     * @param userId userId (required)
     * @param workId workId (required)
     */
    @Headers(
        "X-Operation-ID: getWorkUsingGET"
    )
    @GET("users/{userId}/works/{workId}")
    suspend fun getWorkUsingGET(
        @retrofit2.http.Path("userId") userId: Long,
        @retrofit2.http.Path("workId") workId: Long
    ): UserWork
    /**
     * updateUser
     * The endpoint is owned by server REST api service owner
     * @param id id (required)
     * @param userUpdateRequest userUpdateRequest (required)
     */
    @Headers(
        "X-Operation-ID: updateUserUsingPUT",
      "Content-Type: application/json"
    )
    @PUT("users/{id}")
    suspend fun updateUserUsingPUT(
        @retrofit2.http.Path("id") id: Long,
        @retrofit2.http.Body userUpdateRequest: UserUpdateRequest
    ): User
    /**
     * updateUserWork
     * The endpoint is owned by server REST api service owner
     * @param userId userId (required)
     * @param userWorkUpdateRequest userWorkUpdateRequest (required)
     * @param workId workId (required)
     */
    @Headers(
        "X-Operation-ID: updateUserWorkUsingPUT",
      "Content-Type: application/json"
    )
    @PUT("users/{userId}/works/{workId}")
    suspend fun updateUserWorkUsingPUT(
        @retrofit2.http.Path("userId") userId: Long,
        @retrofit2.http.Body userWorkUpdateRequest: UserWorkUpdateRequest,
        @retrofit2.http.Path("workId") workId: Long
    ): UserWork
}
