package ru.hsHelper.androidApp.ui.navigation

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import ru.hsHelper.R
import ru.hsHelper.androidApp.data.Path
import ru.hsHelper.androidApp.data.serialize
import ru.hsHelper.androidApp.ui.contributions.groups.GroupActivity
import ru.hsHelper.androidApp.ui.settings.SettingsActivity


class NavigationActivity : AppCompatActivity() {
    companion object {
        private const val titleKey = "TITLE"
        private const val pathKey = "PATH"

        fun launcher(name: String, path: Path) = { view: View ->
            val intent = Intent(view.context, NavigationActivity::class.java).apply {
                putExtra(titleKey, name)
                putExtra(pathKey, path.serialize())
            }
            view.context.startActivity(intent)
        }
    }

    private lateinit var navigationViewModel: NavigationViewModel

    internal lateinit var title: String
    internal lateinit var path: Path

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        readIntent()
        setToolbar()
        setView()
        val myIntent = Intent(this, GroupActivity::class.java)
        startActivity(myIntent)
    }

    private fun readIntent() {
        title = intent.getStringExtra(titleKey) ?: resources.getString(R.string.groups)
        path = Path.deserialize(intent.getStringExtra(pathKey) ?: "")
    }

    private fun setToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = title
        setSupportActionBar(toolbar)
    }

    private fun setView() {
        val mainButtonsObserver =
            MainButtonsObserver(this, findViewById(R.id.navigation_main_buttons))
        navigationViewModel = ViewModelProvider(this).get(NavigationViewModel::class.java)
        navigationViewModel.mainButtonsState.observe(this, mainButtonsObserver)
        navigationViewModel.postData(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_settings -> startActivity(Intent(this, SettingsActivity::class.java))
            else -> return false
        }
        return true
    }
}
