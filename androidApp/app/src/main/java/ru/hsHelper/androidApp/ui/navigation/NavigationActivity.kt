package ru.hsHelper.androidApp.ui.navigation

import android.os.Bundle
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
import ru.hsHelper.androidApp.utils.getCurrentWindowWidth


class NavigationActivity : AppCompatActivity() {
    companion object {
        private const val mainButtonsRawSize = 2
        const val nameKey = "NAME"
        const val pathKey = "PATH"
    }

    private val path = intent?.getStringExtra(pathKey) ?: "/"
    private lateinit var view: NavigationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar()
        setView()
    }

    private fun setToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = intent?.getStringExtra(nameKey)
            ?: path.substring(path.lastIndexOf('/') + 1)
        setSupportActionBar(toolbar)
    }

    private fun setView() {
        view = ViewModelProvider(this).get(NavigationViewModel::class.java).apply {
            mainButtonsState.observe(this@NavigationActivity) {
                setButtons(it)
            }
            postData(path)
        }
    }

    private fun makeMainButton(button: ButtonData): AppCompatButton {
        return AppCompatButton(this).apply {
            setBackgroundColor(context.getColor(R.color.navigation_activity_main_button_color))
            text = button.mainText
            setOnClickListener { button.listener }
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
                Toast
                    .makeText(this, "Settings is TODO", Toast.LENGTH_SHORT)
                    .show() // TODO: Run settings activity
            R.id.navigation_calendar ->
                Toast
                    .makeText(this, "Calendar is TODO", Toast.LENGTH_SHORT)
                    .show() // TODO: Run calendar activity
            R.id.navigation_contribute ->
                Toast
                    .makeText(this, "Contributions is TODO", Toast.LENGTH_SHORT)
                    .show() // TODO: Run contributions mode
        }
        return true
    }
}
