package ru.hsHelper.androidApp.ui.navigation

import android.content.Intent
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
import ru.hsHelper.R
import ru.hsHelper.androidApp.data.ButtonData
import ru.hsHelper.androidApp.data.NavigationActivityDataProvider
import ru.hsHelper.androidApp.ui.login.LoginActivity
import ru.hsHelper.androidApp.ui.settings.SettingsActivity
import ru.hsHelper.androidApp.utils.getCurrentWindowWidth


class NavigationActivity : AppCompatActivity() {
    private val mainButtonsRawSize = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // TODO : Get data from service
        val data: NavigationActivityDataProvider = object : NavigationActivityDataProvider {
            override val mainButtons: List<ButtonData>
                get() = listOf(
                    ButtonData("Java") {},
                    ButtonData("Calculus") {},
                    ButtonData("C++") {},
                    ButtonData("Algebra") {},
                    ButtonData("Haskell") {})

        }

        setButtons(data.mainButtons)
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
            R.id.navigation_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
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
