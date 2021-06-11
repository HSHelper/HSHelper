package ru.hsHelper.androidApp.ui.contributions.groups

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_get_many_roles.*
import kotlinx.coroutines.launch
import ru.hsHelper.R
import ru.hsHelper.androidApp.rest.RestProvider
import ru.hsHelper.androidApp.rest.codegen.models.Role

class GetManyRolesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_many_roles)
        Log.d("GETMANYROLESACTIVITY", "Create getManyRolesActivity")

        lifecycleScope.launch {
            //TODO(): add getAllMethodForRole() and remove body in get method
            val roleList = listOf(RestProvider.roleApi.getRoleByIdUsingGET(2))
            onResult(roleList)
        }
    }

    fun onResult(roleList: List<Role>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, roleList.map { it.roleType })
        roleListView.adapter = adapter
        roleListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        submitButton.setOnClickListener {
            val intent = Intent()
            val roles: ArrayList<Long> = ArrayList()
            with(roleListView.checkedItemPositions) {
                for (i in 0 until size()) {
                    val key = keyAt(i)
                    val value = this[key]
                    if (value) roles.add(roleList[key].id)
                }
            }
            if (roles.size == 0) {
                Toast.makeText(this, "You should select at least one role", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            intent.putExtra("roles", roles)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}