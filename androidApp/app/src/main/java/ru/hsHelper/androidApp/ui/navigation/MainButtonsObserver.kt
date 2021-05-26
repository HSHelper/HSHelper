package ru.hsHelper.androidApp.ui.navigation

import android.app.Activity
import android.view.View
import android.widget.Space
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import ru.hsHelper.R
import ru.hsHelper.androidApp.data.ButtonData
import ru.hsHelper.androidApp.utils.getCurrentWindowWidth

class MainButtonsObserver(
    private val context: Activity,
    private val table: TableLayout
) : Observer<List<ButtonData>> {
    companion object {
        private const val mainButtonsRawSize = 2
        private val spaceLayoutParams =
            TableRow.LayoutParams().apply {
                weight = 1F
            }
    }

    private val padding = context.resources.getDimension(R.dimen.navigation_button_padding).toInt()
    private val buttonLayoutParams = buttonLayoutParams()

    private fun buttonLayoutParams(): TableRow.LayoutParams {
        val currentWindowWidth = getCurrentWindowWidth(context)
        val paddingsSum = padding * (mainButtonsRawSize + 1)
        val buttonSize = (currentWindowWidth - paddingsSum) / mainButtonsRawSize
        return TableRow.LayoutParams().apply {
            weight = 0F
            width = buttonSize
            height = buttonSize
        }
    }

    override fun onChanged(buttons: List<ButtonData>) {
        val tableRows = makeMainButtonsRows(buttons)
        table.removeAllViews()
        for (row in tableRows) {
            table.addView(row)
        }
    }

    private fun makeMainButtonsRows(buttons: List<ButtonData>): ArrayList<TableRow> {
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
            buttonsView.add(Space(context))
            if (buttonsView.size == mainButtonsRawSize) {
                tableRows.add(makeMainButtonsRow(buttonsView))
                buttonsView.clear()
            }
        }

        return tableRows
    }

    private fun makeMainButtonsRow(buttons: List<View>): TableRow {
        val row = TableRow(context).apply {
            setPadding(0, padding, 0, 0)
        }

        row.addView(Space(context), spaceLayoutParams)
        for (button in buttons) {
            row.addView(button, buttonLayoutParams)
            row.addView(Space(context), spaceLayoutParams)
        }

        return row
    }

    private fun makeMainButton(button: ButtonData): AppCompatButton {
        return AppCompatButton(context).apply {
            setBackgroundColor(resources.getColor(R.color.navigation_main_button_color))
            text = button.mainText
            setOnClickListener(button.listener)
        }
    }
}
