package ru.hsHelper.androidApp.ui.marks

data class MarkInterval(val max: Double, val average: Double, val min: Double) {
    constructor(exactValue: Double) : this(exactValue, exactValue, exactValue)

    operator fun plus(other: MarkInterval): MarkInterval =
        MarkInterval(max + other.max, average + other.average, min + other.min)

    operator fun times(weight: Double): MarkInterval =
        MarkInterval(max * weight, average * weight, min * weight)

    override fun toString(): String {
        return if (max - min > 0.1) {
            "$min..$average..$max"
        } else {
            max.toString()
        }
    }
}
