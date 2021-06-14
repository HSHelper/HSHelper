package ru.hsHelper.api.sheets.processing

import com.google.api.services.sheets.v4.Sheets
import ru.hsHelper.api.services.UserService
import ru.hsHelper.api.services.WorkService
import ru.hsHelper.api.sheets.data.SheetStoredData
import ru.hsHelper.api.sheets.processing.update.MarksColumn
import ru.hsHelper.api.sheets.processing.update.Sheet
import ru.hsHelper.api.sheets.processing.update.SheetLink
import ru.hsHelper.api.sheets.processing.update.UsersRange

class UpdateProcessor(
    private val userService: UserService,
    private val workService: WorkService,
    private val sheet: Sheets
) {
    fun update(sheetData: SheetStoredData) {
        val link = SheetLink(sheetData.sheetLink.id, sheetData.sheetLink.page, sheet)
        val range = UsersRange(sheetData.usersRange.userRangeBegin, sheetData.usersRange.userIds)
        val columns = sheetData.columns.map { MarksColumn(it.column, it.workId, range, it.lastState) }
        Sheet(link, columns).update().forEach { it.submit(userService, workService) }
    }
}
