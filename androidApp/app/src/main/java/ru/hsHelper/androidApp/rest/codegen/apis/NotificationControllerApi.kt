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
import ru.hsHelper.androidApp.rest.codegen.models.Notification
import ru.hsHelper.androidApp.rest.codegen.models.NotificationCreateRequest
import ru.hsHelper.androidApp.rest.codegen.models.User

@JvmSuppressWildcards
interface NotificationControllerApi {
    /**
     * createNotification
     * The endpoint is owned by server REST api service owner
     * @param notificationCreateRequest notificationCreateRequest (required)
     */
    @Headers(
        "X-Operation-ID: createNotificationUsingPOST",
      "Content-Type: application/json"
    )
    @POST("notifications/")
    suspend fun createNotificationUsingPOST(
        @retrofit2.http.Body notificationCreateRequest: NotificationCreateRequest
    ): Notification
    /**
     * deletePermission
     * The endpoint is owned by server REST api service owner
     * @param notificationId notificationId (required)
     */
    @Headers(
        "X-Operation-ID: deletePermissionUsingDELETE"
    )
    @DELETE("notifications/{notificationId}")
    suspend fun deletePermissionUsingDELETE(
        @retrofit2.http.Path("notificationId") notificationId: Long
    ): Unit
    /**
     * getAllNotifications
     * The endpoint is owned by server REST api service owner
     */
    @Headers(
        "X-Operation-ID: getAllNotificationsUsingGET"
    )
    @GET("notifications/")
    suspend fun getAllNotificationsUsingGET(): List<Notification>
    /**
     * getAllUsers
     * The endpoint is owned by server REST api service owner
     * @param notificationId notificationId (required)
     */
    @Headers(
        "X-Operation-ID: getAllUsersUsingGET"
    )
    @GET("notifications/{notificationId}/users")
    suspend fun getAllUsersUsingGET(
        @retrofit2.http.Path("notificationId") notificationId: Long
    ): List<User>
    /**
     * getNotificationById
     * The endpoint is owned by server REST api service owner
     * @param notificationId notificationId (required)
     */
    @Headers(
        "X-Operation-ID: getNotificationByIdUsingGET"
    )
    @GET("notifications/{notificationId}")
    suspend fun getNotificationByIdUsingGET(
        @retrofit2.http.Path("notificationId") notificationId: Long
    ): Notification
    /**
     * getNotificationByNotificationType
     * The endpoint is owned by server REST api service owner
     * @param notificationType notificationType (required)
     */
    @Headers(
        "X-Operation-ID: getNotificationByNotificationTypeUsingGET"
    )
    @GET("notifications/permission-types/{notificationType}")
    suspend fun getNotificationByNotificationTypeUsingGET(
        @retrofit2.http.Path("notificationType") notificationType: String
    ): Notification
}
