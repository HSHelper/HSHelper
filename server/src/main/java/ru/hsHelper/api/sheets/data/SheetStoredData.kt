package ru.hsHelper.api.sheets.data

data class SheetStoredData(
    val sheetLink: SheetLinkData,
    val usersRange: UsersRangeData,
    val columns: MutableList<MarksColumnCachedData>
) : java.io.Serializable
