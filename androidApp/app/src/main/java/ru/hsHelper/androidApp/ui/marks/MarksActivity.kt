package ru.hsHelper.androidApp.ui.marks

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import ru.hsHelper.R
import ru.hsHelper.androidApp.ui.settings.SettingsActivity

class MarksActivity : AppCompatActivity() {
    companion object {
        private const val titleKey = "TITLE"
        private const val pathKey = "PATH"

        fun launcher(name: String, path: String) = { view: View ->
            val intent = Intent(view.context, MarksActivity::class.java).apply {
                putExtra(titleKey, name)
                putExtra(pathKey, path)
            }
            view.context.startActivity(intent)
        }
    }

    private lateinit var marksViewModel: MarksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marks)
        setToolbar()
        setView()
    }

    private fun setToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = intent.getStringExtra(titleKey)!!
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setView() {
        val summaryObserver = SummaryObserver(findViewById(R.id.marks_summary))
        val marksTableObserver = MarksTableObserver(this, findViewById(R.id.marks_table))
        marksViewModel = ViewModelProvider(this).get(MarksViewModel::class.java).apply {
            summary.observe(this@MarksActivity, summaryObserver)
            tableContent.observe(this@MarksActivity, marksTableObserver)
            postData(intent.getStringExtra(pathKey)!!)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_settings -> startActivity(Intent(this, SettingsActivity::class.java))
            android.R.id.home -> this.finish()
            else -> return false
        }
        return true
    }
}
