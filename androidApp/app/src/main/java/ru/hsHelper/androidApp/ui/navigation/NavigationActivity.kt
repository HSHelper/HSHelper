package ru.hsHelper.androidApp.ui.navigation

import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Space
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import ru.hsHelper.R
import ru.hsHelper.androidApp.data.ButtonData
import ru.hsHelper.androidApp.ui.settings.SettingsActivity
import ru.hsHelper.androidApp.utils.getCurrentWindowWidth


class NavigationActivity : AppCompatActivity() {
    companion object {
        private const val mainButtonsRawSize = 2
        const val titleKey = "TITLE"
        const val pathKey = "PATH"

        fun launcher(name: String, path: String) = { view: View ->
            val intent = Intent(view.context, NavigationActivity::class.java).apply {
                putExtra(titleKey, name)
                putExtra(pathKey, path)
            }
            view.context.startActivity(intent)
        }
    }

    private lateinit var view: NavigationViewModel

    private val title: String
        get() = intent.getStringExtra(titleKey) ?: resources.getString(R.string.groups)
    private val path: String
        get() = intent.getStringExtra(pathKey) ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar()
        setView()
    }

    private fun setToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = title
        setSupportActionBar(toolbar)
    }

    private fun setView() {
        view = ViewModelProvider(this).get(NavigationViewModel::class.java).apply {
            mainButtonsState.observe(
                this@NavigationActivity,
                this@NavigationActivity::setButtons
            )
            postData(path, title)
        }
    }

    private fun makeMainButton(button: ButtonData): AppCompatButton {
        return AppCompatButton(this).apply {
            setBackgroundColor(resources.getColor(R.color.navigation_activity_main_button_color))
            text = button.mainText
            setOnClickListener(button.listener)
        }
    }

    private fun makeMainButtonsRow(buttons: List<View>): TableRow {
        val padding = resources.getDimension(R.dimen.navigation_activity_button_padding).toInt()
        val buttonSize =
            (getCurrentWindowWidth(this) - padding * (mainButtonsRawSize + 1)) / mainButtonsRawSize
        val spaceLayoutParams = TableRow.LayoutParams().apply {
            weight = 1F
        }
        val buttonLayoutParams = TableRow.LayoutParams().apply {
            width = buttonSize
            height = buttonSize
            weight = 0F
        }
        val row = TableRow(this).apply {
            setPadding(0, padding, 0, 0)
            addView(Space(this@NavigationActivity), spaceLayoutParams)
        }
        for (button in buttons) {
            row.apply {
                addView(button, buttonLayoutParams)
                addView(Space(this@NavigationActivity), spaceLayoutParams)
            }
        }
        return row
    }

    private fun setButtons(buttons: List<ButtonData>) {
        val buttonsView = ArrayList<View>()
        val tableRows = ArrayList<TableRow>()
        for (button in buttons) {
            buttonsView.add(makeMainButton(button))
            if (buttonsView.size == mainButtonsRawSize) {
                tableRows.add(makeMainButtonsRow(buttonsView))
                buttonsView.clear()
            }
        }
        while (buttonsView.isNotEmpty()) {
            buttonsView.add(Space(this))
            if (buttonsView.size == mainButtonsRawSize) {
                tableRows.add(makeMainButtonsRow(buttonsView))
                buttonsView.clear()
            }
        }
        val table: TableLayout = findViewById(R.id.main_table)
        table.removeAllViews()
        for (row in tableRows) {
            table.addView(row)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_settings ->
                startActivity(Intent(this, SettingsActivity::class.java))
            R.id.navigation_calendar -> {
                val startMillis: Long = System.currentTimeMillis()
                val builder: Uri.Builder = CalendarContract.CONTENT_URI.buildUpon()
                    .appendPath("time")
                ContentUris.appendId(builder, startMillis)
                val intent = Intent(Intent.ACTION_VIEW)
                    .setData(builder.build())
                startActivity(intent)
            }
            R.id.navigation_contribute ->
                Toast
                    .makeText(this, "Contributions is TODO", Toast.LENGTH_SHORT)
                    .show() // TODO: Run contributions mode
            else -> return false
        }
        return true
    }
}
