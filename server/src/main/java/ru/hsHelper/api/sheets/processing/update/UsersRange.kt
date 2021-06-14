package ru.hsHelper.api.sheets.processing.update

class UsersRange(private val userRangeBegin: Int, private val userIds: List<Long>) : java.io.Serializable {
    val size get() = userIds.size

    operator fun get(index: Int) = userIds[index]

    fun range(column: String): ColumnRange {
        return ColumnRange(column, userRangeBegin, userRangeBegin + userIds.size)
    }
}