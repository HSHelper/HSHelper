package ru.hsHelper.androidApp.ui.marks

import android.content.Context
import android.graphics.Color
import android.widget.Space
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import org.threeten.bp.format.DateTimeFormatter
import ru.hsHelper.R
import ru.hsHelper.androidApp.data.ButtonData

class MarksTableObserver(
    private val context: Context,
    private val table: TableLayout
) : Observer<MarksTableContent> {
    companion object {
        private val dateFormat = DateTimeFormatter.ofPattern("dd MMM")
        private const val fontSize = 12F
        private val rowBigSpaceLayoutParams =
            TableRow.LayoutParams().apply {
                weight = 10F
            }
        private val rowSmallSpaceLayoutParams =
            TableRow.LayoutParams().apply {
                weight = 1F
            }
    }

    private val rowPadding =
        context.resources.getDimension(R.dimen.marks_row_padding).toInt()
    private val rowElementLayoutParams =
        TableRow.LayoutParams().apply {
            width = TableRow.LayoutParams.WRAP_CONTENT
            height = context.resources.getDimension(R.dimen.marks_row_size).toInt()
            weight = 1F
        }


    override fun onChanged(data: MarksTableContent) {
        table.removeAllViews()
        when (data) {
            is MarksTableContent.Group -> {
                for (mark in data.content) {
                    table.addView(makeRow(mark))
                }
            }
            is MarksTableContent.Course -> {
                for (mark in data.content) {
                    table.addView(makeRow(mark))
                }
            }
            is MarksTableContent.CoursePart -> {
                for (mark in data.content) {
                    table.addView(makeRow(mark))
                }
            }
        }
    }


    private fun makeInTableButton(button: ButtonData): AppCompatButton {
        return AppCompatButton(context).apply {
            text = button.mainText
            setOnClickListener(button.listener)
            textSize = fontSize
            setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun makeInTableTextView(viewText: String): TextView {
        return TextView(context).apply {
            text = viewText
            textSize = fontSize
        }
    }


    private fun makeRow(data: MarksRowContent.Course): TableRow {
        return TableRow(context).apply {
            setPadding(rowPadding, rowPadding, rowPadding, rowPadding)
            addView(makeInTableButton(data.viewLauncher), rowElementLayoutParams)
            addView(Space(context), rowBigSpaceLayoutParams)
            addView(makeInTableTextView(data.mark.toString()), rowElementLayoutParams)
        }
    }

    private fun makeRow(data: MarksRowContent.CoursePart): TableRow {
        return TableRow(context).apply {
            setPadding(rowPadding, rowPadding, rowPadding, rowPadding)
            addView(makeInTableButton(data.viewLauncher), rowElementLayoutParams)
            addView(Space(context), rowBigSpaceLayoutParams)
            addView(makeInTableTextView(data.mark.toString()), rowElementLayoutParams)
            addView(Space(context), rowSmallSpaceLayoutParams)
            addView(makeInTableTextView(data.weight.toString()), rowElementLayoutParams)
        }
    }

    private fun makeRow(data: MarksRowContent.Work): TableRow {
        return TableRow(context).apply {
            setPadding(rowPadding, rowPadding, rowPadding, rowPadding)
            addView(makeInTableTextView(data.title), rowElementLayoutParams)
            addView(Space(context), rowBigSpaceLayoutParams)
            addView(makeInTableTextView(data.deadline.format(dateFormat)), rowElementLayoutParams)
            addView(Space(context), rowSmallSpaceLayoutParams)
            addView(makeInTableButton(data.statement), rowElementLayoutParams)
            addView(Space(context), rowSmallSpaceLayoutParams)
            addView(makeInTableButton(data.solution), rowElementLayoutParams)
            addView(Space(context), rowSmallSpaceLayoutParams)
            addView(makeInTableTextView(data.mark?.toString() ?: "-"), rowElementLayoutParams)
            addView(Space(context), rowSmallSpaceLayoutParams)
            addView(makeInTableTextView(data.weight.toString()), rowElementLayoutParams)
        }
    }
}
