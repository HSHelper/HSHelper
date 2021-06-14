package ru.hsHelper.api.sheets.data

data class SheetInitData(
    val sheetLink: SheetLinkData,
    val usersRange: UsersRangeData,
    val columns: MutableList<MarksColumnData>
)
