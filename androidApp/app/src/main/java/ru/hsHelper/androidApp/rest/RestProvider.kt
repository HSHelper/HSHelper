package ru.hsHelper.androidApp.rest

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.hsHelper.androidApp.rest.codegen.apis.*
import ru.hsHelper.androidApp.rest.codegen.tools.GeneratedCodeConverters
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


object RestProvider {
    val userApi: UserControllerApi
        get() = retrofit.create(UserControllerApi::class.java)
    val courseApi: CourseControllerApi
        get() = retrofit.create(CourseControllerApi::class.java)
    val coursePartApi: CoursePartControllerApi
        get() = retrofit.create(CoursePartControllerApi::class.java)
    val groupApi: GroupControllerApi
        get() = retrofit.create(GroupControllerApi::class.java)
    val partitionApi: PartitionControllerApi
        get() = retrofit.create(PartitionControllerApi::class.java)
    val permissionApi: PermissionsControllerApi
        get() = retrofit.create(PermissionsControllerApi::class.java)
    val roleApi: RoleControllerApi
        get() = retrofit.create(RoleControllerApi::class.java)
    val workApi: WorkControllerApi
        get() = retrofit.create(WorkControllerApi::class.java)
    val notificationApi: NotificationControllerApi
        get() = retrofit.create(NotificationControllerApi::class.java)

    private fun getUnsafeOkHttpClient(): OkHttpClient {
        val trustAllCerts: Array<TrustManager> = arrayOf(
            object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )
        val sslContext: SSLContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { hostname, session -> true }
        return builder.build()
    }


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://84.252.137.106:1337/")
        .client(getUnsafeOkHttpClient())
        .addConverterFactory(GeneratedCodeConverters.converterFactory())
        .build()
}
