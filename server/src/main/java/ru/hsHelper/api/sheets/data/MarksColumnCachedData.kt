package ru.hsHelper.api.sheets.data

data class MarksColumnCachedData(val column: String, val workId: Long, val lastState: MutableList<Double?>) :
    java.io.Serializable
