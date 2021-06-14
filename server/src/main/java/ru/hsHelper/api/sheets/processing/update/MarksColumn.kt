package ru.hsHelper.api.sheets.processing.update

class MarksColumn(
    private val column: String,
    private val workId: Long,
    private val usersRange: UsersRange,
    private val lastState: MutableList<Double?>
) : java.io.Serializable {
    val range get() = usersRange.range(column)

    fun updateState(state: List<Double?>): List<MarkUpdate> {
        assert(state.size == lastState.size)
        val diff = ArrayList<MarkUpdate>()
        for (i in lastState.indices) {
            if (lastState[i] != state[i]) {
                diff.add(MarkUpdate(workId, usersRange[i], state[i]))
                lastState[i] = state[i]
            }
        }
        return diff
    }
}