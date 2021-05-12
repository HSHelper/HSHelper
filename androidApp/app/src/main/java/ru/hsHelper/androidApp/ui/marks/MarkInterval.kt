package ru.hsHelper.androidApp.ui.marks

data class MarkInterval(val max: Double, val average: Double, val min: Double) {
    companion object {
        fun calculate(sum: Double, weight: Double): MarkInterval {
            return if (weight > 0.99) {
                MarkInterval(sum / weight)
            } else {
                MarkInterval(sum + 10 * (1 - weight), sum / weight, sum)
            }
        }
    }

    constructor(exactValue: Double) : this(exactValue, exactValue, exactValue)

    override fun toString(): String {
        return if (max - min > 0.1) {
            "$min..$average..$max"
        } else {
            max.toString()
        }
    }
}
