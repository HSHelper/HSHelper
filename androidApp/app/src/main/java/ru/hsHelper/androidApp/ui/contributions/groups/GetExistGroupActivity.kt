package ru.hsHelper.androidApp.ui.contributions.groups

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.lifecycle.lifecycleScope
import ru.hsHelper.R
import kotlinx.android.synthetic.main.activity_get_exist_group_name.*
import kotlinx.coroutines.launch
import ru.hsHelper.androidApp.rest.RestProvider
import ru.hsHelper.androidApp.rest.codegen.models.Group

class GetExistGroupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_exist_group_name)
        Log.d("GETEXISTGROUPNAMEACTIVITY", "Create getExistGroupNameActivity")

        lifecycleScope.launch {
            val groupList = RestProvider.groupApi.getAllUsingGET2()
            onResult(groupList)
        }
    }

    fun onResult(groupList: List<Group>) {
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, groupList.map { it.name })
        groupListView.adapter = adapter
        groupListView.choiceMode = ListView.CHOICE_MODE_SINGLE
        submitButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra("groupId", groupList[groupListView.checkedItemPosition].id)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}