package ru.hsHelper.api.sheets.processing

import ru.hsHelper.api.sheets.data.MarksColumnCachedData
import ru.hsHelper.api.sheets.data.SheetInitData
import ru.hsHelper.api.sheets.data.SheetStoredData

class CreateProcessor {
    fun create(initData: SheetInitData): SheetStoredData {
        val size = initData.usersRange.userIds.size
        val cachedColumns = initData.columns.asSequence()
            .map { MarksColumnCachedData(it.column, it.workId, MutableList(size) { null }) }
            .toMutableList()
        return SheetStoredData(initData.sheetLink, initData.usersRange, cachedColumns)
    }
}