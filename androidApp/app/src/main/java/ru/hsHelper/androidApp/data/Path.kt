package ru.hsHelper.androidApp.data

sealed class Path {
    companion object {
        fun serialize(path: Path): String {
            return when (path) {
                is Root -> ""
                is Group -> "G${path.id}"
                is Course -> "C${path.id}"
                is CoursePart -> "P${path.id}"
            }
        }

        fun deserialize(str: String): Path {
            return if (str.isEmpty()) {
                Root
            } else {
                when (str[0]) {
                    'G' -> Group(str.substring(1).toLong())
                    'C' -> Course(str.substring(1).toLong())
                    'P' -> CoursePart(str.substring(1).toLong())
                    else -> throw IllegalArgumentException("Not a valid path")
                }
            }
        }
    }

    object Root : Path()
    class Group(val id: Long) : Path()
    class Course(val id: Long) : Path()
    class CoursePart(val id: Long) : Path()
}

fun Path.serialize() = Path.serialize(this)
