package ru.hsHelper.api.sheets

import java.util.*

fun <T> Map<Long, T>.newKey(): Long {
    val random = Random()
    var key = random.nextLong()
    while (this.containsKey(key)) {
        key = random.nextLong()
    }
    return key
}
