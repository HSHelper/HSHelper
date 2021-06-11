package ru.hsHelper.androidApp.ui.contributions.groups

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.android.synthetic.main.activity_add_users_to_group.*
import kotlinx.android.synthetic.main.activity_add_users_to_group.submitButton
import kotlinx.android.synthetic.main.activity_get_new_group_name.*
import ru.hsHelper.R

class AddUsersToGroupActivity : AppCompatActivity() {
    private var group: Long? = null
    private var users: ArrayList<Long> = ArrayList()
    private var roles: HashMap<Long, ArrayList<Long>> = HashMap()

    var selectGroupLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data == null) return@registerForActivityResult
            val groupId: Long = data.getLongExtra("groupId", -1)
            if (groupId == -1L) return@registerForActivityResult
            group = groupId
        }
    }

    var selectUsersLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data == null) return@registerForActivityResult
            val usersIds = data.getSerializableExtra("users")
            val rolesIds = data.getSerializableExtra("roles")
            if (rolesIds == null || usersIds == null) return@registerForActivityResult
            users = usersIds as ArrayList<Long>
            roles = rolesIds as HashMap<Long, ArrayList<Long>>

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_users_to_group)
        Log.d("ADDUSERSACTIVITY", "Create addUsersToGroupActivity")

        selectGroupButton.setOnClickListener {
            val intent = Intent(this, GetExistGroupActivity::class.java)
            selectGroupLauncher.launch(intent)
        }

        selectUsersButton.setOnClickListener {
            val intent = Intent(this, GetManyUsersActivity::class.java)
            selectUsersLauncher.launch(intent)
        }

        submitButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra("roles", roles)
            intent.putExtra("users", users)
            intent.putExtra("group", group)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}