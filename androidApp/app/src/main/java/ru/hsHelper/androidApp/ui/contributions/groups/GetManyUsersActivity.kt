package ru.hsHelper.androidApp.ui.contributions.groups

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_get_many_users.*
import kotlinx.coroutines.launch
import ru.hsHelper.R
import ru.hsHelper.androidApp.rest.RestProvider
import ru.hsHelper.androidApp.rest.codegen.models.User

class GetManyUsersActivity : AppCompatActivity() {
    private val roles: HashMap<Long, ArrayList<Long>> = HashMap()
    private var currentRoles: ArrayList<Long> = ArrayList()
    var selectRolesLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data == null) return@registerForActivityResult
            val roles = data.getSerializableExtra("roles")
            if (roles == null) return@registerForActivityResult
            currentRoles = roles as ArrayList<Long>
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_many_users)
        Log.d("GETMANYUSERSACTIVITY", "Create getManyUsersActivity")

        lifecycleScope.launch {
            val userList = RestProvider.userApi.getAllUsingGET4()
            onResult(userList)
        }
    }

    fun onResult(userList: List<User>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, userList.map { it.email })
        userListView.adapter = adapter
        userListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        userListView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, GetManyRolesActivity::class.java)
            selectRolesLauncher.launch(intent)
            roles.put(position.toLong(), currentRoles)
        }
        submitButton.setOnClickListener {
            val intent = Intent()
            val users: ArrayList<Long> = ArrayList()
            val actualRoles: HashMap<Long, ArrayList<Long>> = HashMap()
            intent.putExtra("roles", roles)
            with(userListView.checkedItemPositions) {
                for (i in 0 until size()) {
                    val key = keyAt(i)
                    val value = this[key]
                    if (value) {
                        users.add(userList[key].id)
                        actualRoles.put(key.toLong(), roles.get(key.toLong())!!)
                    }
                }
            }
            if (users.size == 0) {
                Toast.makeText(this, "You should select at least one user", Toast.LENGTH_LONG).show()
            }
            intent.putExtra("users", users)
            intent.putExtra("roles", roles)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}