private data class Mirror(val rows: List<String>, val cols: List<String>)

private fun obtainMirrors(input: List<String>): List<Mirror> {
    val mirrors: MutableList<Mirror> = mutableListOf()
    var data: MutableList<String> = mutableListOf()
    for (i in input) {
        if (i.isEmpty()) {
            mirrors.add(Mirror(data, obtainCols(data)))
            data = mutableListOf()
        } else {
            data.add(i)
        }
    }
    mirrors.add(Mirror(data, obtainCols(data))) // add the last mirror
    return mirrors
}

private fun obtainCols(data: List<String>): List<String> {
    val cols: MutableList<String> = mutableListOf()
    for (i in 0..<data[0].length) {
        var col = ""
        for (row in data) col += row[i]
        cols.add(col)
    }
    return cols
}

private val part1 = { input: List<String> -> obtainMirrors(input).fold(0) {acc, mirror -> acc+checkMirror(mirror.cols, true) + checkMirror(mirror.rows, false) } }

private fun isMirrorAt(input: List<String>, index: Int): Boolean {
    var m1 = index
    var m2 = index + 1
    while (m1 >= 0) {
        try {
            if (input[m1] != input[m2]) return false
        } catch (_: IndexOutOfBoundsException) {
        }
        m1--
        m2++
    }
    return true
}

private fun checkMirror(data: List<String>, isCol: Boolean): Int {
    for (i in 0..<data.size - 1) {
        val fv = data[i]
        val sv = data[i + 1]
        if (fv == sv && isMirrorAt(data, i)) {
            return if(!isCol) {
                (i+1)*100
            } else {
                i+1
            }
        }
    }
    return 0
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 405)

    val input = readInput("Day13")
    check(part1(input) == 32371)
    // part2(input).println()
}
