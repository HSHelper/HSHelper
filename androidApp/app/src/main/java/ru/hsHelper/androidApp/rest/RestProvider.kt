package ru.hsHelper.androidApp.rest

import retrofit2.Retrofit
import ru.hsHelper.androidApp.rest.codegen.apis.*
import ru.hsHelper.androidApp.rest.codegen.tools.GeneratedCodeConverters

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

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://84.252.137.106:1337/")
        .addConverterFactory(GeneratedCodeConverters.converterFactory())
        .build()

}
