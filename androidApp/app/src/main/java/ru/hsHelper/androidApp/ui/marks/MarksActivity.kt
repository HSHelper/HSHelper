package ru.hsHelper.androidApp.ui.marks

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import org.threeten.bp.format.DateTimeFormatter
import ru.hsHelper.R
import ru.hsHelper.androidApp.data.ButtonData
import ru.hsHelper.androidApp.ui.settings.SettingsActivity

class MarksActivity : AppCompatActivity() {
    companion object {
        const val titleKey = "TITLE"
        const val pathKey = "PATH"
        val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM")

        fun launcher(name: String, path: String) = { view: View ->
            val intent = Intent(view.context, MarksActivity::class.java).apply {
                putExtra(titleKey, name)
                putExtra(pathKey, path)
            }
            view.context.startActivity(intent)
        }
    }

    private lateinit var view: MarksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marks)
        setToolbar()
        setLayoutParams()
        setView()
    }

    private fun setToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = intent.getStringExtra(titleKey)!!
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setView() {
        view = ViewModelProvider(this).get(MarksViewModel::class.java).apply {
            summary.observe(this@MarksActivity, this@MarksActivity::setSummary)
            tableContent.observe(this@MarksActivity, this@MarksActivity::setTable)
            postData(intent.getStringExtra(pathKey)!!)
        }
    }

    private fun setSummary(data: MarkInterval?) {
        val summary = findViewById<TextView>(R.id.mark_summary)
        if (data != null) {
            summary.text = data.toString()
        } else {
            summary.text = ""
            summary.height = 0
        }
    }

    private fun setTable(data: MarksTableContent) {
        val table = findViewById<TableLayout>(R.id.mark_table)
        table.removeAllViews()
        when (data) {
            is MarksTableContent.ExpandableContent -> {
                for (mark in data.content) {
                    table.addView(makeRow(mark))
                }
            }
            is MarksTableContent.WorkContent -> {
                for (mark in data.content) {
                    table.addView(makeRow(mark))
                }
            }
        }
    }

    private fun makeInTableButton(button: ButtonData): AppCompatButton {
        return AppCompatButton(this).apply {
            text = button.mainText
            setOnClickListener(button.listener)
            textSize = fontSize
            setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun makeTextView(viewText: String): TextView {
        return TextView(this).apply {
            text = viewText
            textSize = fontSize
        }
    }

    private fun setLayoutParams() {
        rowPadding = resources.getDimension(R.dimen.activity_marks_row_padding).toInt()
        rowHeight = resources.getDimension(R.dimen.activity_marks_row_size).toInt()
        rowBigSpaceLayoutParams = TableRow.LayoutParams().apply {
            weight = 10F
        }
        rowSmallSpaceLayoutParams = TableRow.LayoutParams().apply {
            weight = 1F
        }
        rowElementLayoutParams = TableRow.LayoutParams().apply {
            width = TableRow.LayoutParams.WRAP_CONTENT
            height = rowHeight
            weight = 1F
        }
    }

    private val fontSize = 12F
    private var rowPadding: Int = 0
    private var rowHeight: Int = 0
    private lateinit var rowBigSpaceLayoutParams: TableRow.LayoutParams
    private lateinit var rowSmallSpaceLayoutParams: TableRow.LayoutParams
    private lateinit var rowElementLayoutParams: TableRow.LayoutParams

    private fun makeRow(data: ExpandableMark): TableRow {
        return TableRow(this).apply {
            setPadding(rowPadding, rowPadding, rowPadding, rowPadding)
            addView(makeInTableButton(data.subGroup), rowElementLayoutParams)
            addView(Space(this@MarksActivity), rowBigSpaceLayoutParams)
            addView(makeTextView(data.mark.toString()), rowElementLayoutParams)
            addView(Space(this@MarksActivity), rowSmallSpaceLayoutParams)
            addView(makeTextView(data.weight.toString()), rowElementLayoutParams)
        }
    }

    private fun makeRow(data: WorkMark): TableRow {
        return TableRow(this).apply {
            setPadding(rowPadding, rowPadding, rowPadding, rowPadding)
            addView(makeTextView(data.title), rowElementLayoutParams)
            addView(Space(this@MarksActivity), rowBigSpaceLayoutParams)
            addView(makeTextView(data.deadline.format(dateFormat)), rowElementLayoutParams)
            addView(Space(this@MarksActivity), rowSmallSpaceLayoutParams)
            addView(makeInTableButton(data.statement), rowElementLayoutParams)
            addView(Space(this@MarksActivity), rowSmallSpaceLayoutParams)
            addView(makeInTableButton(data.solution), rowElementLayoutParams)
            addView(Space(this@MarksActivity), rowSmallSpaceLayoutParams)
            addView(makeTextView(data.mark?.toString() ?: "-"), rowElementLayoutParams)
            addView(Space(this@MarksActivity), rowSmallSpaceLayoutParams)
            addView(makeTextView(data.weight.toString()), rowElementLayoutParams)
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
            R.id.navigation_calendar ->
                Toast
                    .makeText(this, "Calendar is TODO", Toast.LENGTH_SHORT)
                    .show() // TODO: Run calendar activity
            R.id.navigation_contribute ->
                Toast
                    .makeText(this, "Contributions is TODO", Toast.LENGTH_SHORT)
                    .show() // TODO: Run contributions mode
            android.R.id.home -> this.finish()
            else -> return false
        }
        return true
    }
}