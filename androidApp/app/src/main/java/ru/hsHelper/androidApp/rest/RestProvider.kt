package ru.hsHelper.androidApp.rest

import retrofit2.Retrofit
import ru.hsHelper.androidApp.rest.codegen.apis.*
import ru.hsHelper.androidApp.rest.codegen.tools.GeneratedCodeConverters

object RestProvider {
    val userApi
        get() = retrofit.create(UserControllerApi::class.java)
    val courseApi
        get() = retrofit.create(CourseControllerApi::class.java)
    val coursePartApi
        get() = retrofit.create(CoursePartControllerApi::class.java)
    val groupApi
        get() = retrofit.create(GroupControllerApi::class.java)
    val partitionApi
        get() = retrofit.create(PartitionControllerApi::class.java)
    val permissionApi
        get() = retrofit.create(PermissionsControllerApi::class.java)
    val roleApi
        get() = retrofit.create(RoleControllerApi::class.java)
    val workApi
        get() = retrofit.create(WorkControllerApi::class.java)

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/")
        .addConverterFactory(GeneratedCodeConverters.converterFactory())
        .build()

}
