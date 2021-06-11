package ru.hsHelper.androidApp.ui.contributions.groups

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_get_new_group_name.*
import ru.hsHelper.R

class GetNewGroupNameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_new_group_name)
        Log.d("GETNEWGROUPNAMEACTIVITY", "Create GetNewGroupNameActivity")

        submitButton.setOnClickListener {
            // TODO(): Handle event when group with such name already exist
            val intent = Intent()
            intent.putExtra("groupName", nameInput.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}