package ru.hsHelper.androidApp.ui.contributions.groups

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import ru.hsHelper.R
import kotlinx.android.synthetic.main.activity_group.*
import kotlinx.coroutines.launch
import ru.hsHelper.androidApp.rest.RestProvider
import ru.hsHelper.androidApp.rest.codegen.models.GroupCreateRequest
import ru.hsHelper.androidApp.rest.codegen.models.ObjectsWithRoleAddRequest

class GroupActivity : AppCompatActivity() {
    var createGroupLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data == null) return@registerForActivityResult
            val groupName = data.getStringExtra("groupName")
            if (groupName == null) return@registerForActivityResult
            lifecycleScope.launch {
                RestProvider.groupApi.createGroupUsingPOST(GroupCreateRequest(groupName))
            }
        }
    }

    var deleteGroupLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data == null) return@registerForActivityResult
            val groupId = data.getLongExtra("groupId", -1)
            if (groupId == -1L) return@registerForActivityResult
            lifecycleScope.launch {
                RestProvider.groupApi.deleteGroupUsingDELETE(groupId)
            }
        }
    }

    var addUsersToGroupLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data == null) return@registerForActivityResult
            val group = data.getLongExtra("group", -1)
            val roles: Map<Long, List<Long>>? = data.getSerializableExtra("roles") as HashMap<Long, List<Long>>?
            val users: List<Long>? = data.getSerializableExtra("users") as ArrayList<Long>?
            if (users == null || roles == null || group == -1L) return@registerForActivityResult
            lifecycleScope.launch {
                RestProvider.groupApi.addUsersUsingPUT2(group, ObjectsWithRoleAddRequest(users, roles.mapKeys { it.toString() }))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)
        Log.d("GROUP", "Create GroupActivity")
        createGroupButton.setOnClickListener {
            val intent = Intent(this, GetNewGroupNameActivity::class.java)
            createGroupLauncher.launch(intent)
        }
        deleteGroupButton.setOnClickListener {
            val intent = Intent(this, GetExistGroupActivity::class.java)
            deleteGroupLauncher.launch(intent)
        }
        addUsersButton.setOnClickListener {
            val intent = Intent(this, AddUsersToGroupActivity::class.java)
            addUsersToGroupLauncher.launch(intent)
        }
    }
}