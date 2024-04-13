import kotlin.math.abs

data class Galaxy(val number: Int, val x: Int, val y: Int)

val part = { input: List<String>, expansion: Long -> obtainSum(findGalaxies(input), obtainRows(input), obtainCols(input), expansion - 1L) }

private val obtainRows = { input: List<String> -> input.foldIndexed(mutableListOf<Int>()) { index, acc, row -> if(row.count {c-> c== '#'} == 0) acc.add(index); acc } }

private fun findGalaxies(image: List<String>): List<Galaxy> {
    val galaxies: MutableList<Galaxy> = mutableListOf()
    var id = 1
    for (i in image.indices) {
        val hashtag = Regex("""#""")
        val galaxyRow = hashtag.findAll(image[i])
        for (g in galaxyRow) {
            galaxies.add(Galaxy(id, i, g.range.first))
            id++
        }
    }
    return galaxies
}

private fun obtainSum(galaxies: List<Galaxy>, rows: List<Int>, cols: List<Int>, expansion: Long): Long {
    var total = 0L
    for (i in galaxies.indices) {
        for (j in i + 1..<galaxies.size) {
            val g1 = galaxies[i]
            val g2 = galaxies[j]

            val rows2Add = rows.filter { r -> r > g1.x && r < g2.x || (r > g2.x && r < g1.x) }
            val cols2Add = cols.filter { c -> (c > g1.y && c < g2.y) || (c > g2.y && c < g1.y) }

            val x = g1.x - g2.x
            val y = g1.y - g2.y

            total += abs(x).toLong() + abs(y).toLong() + (rows2Add.size * expansion) + (cols2Add.size * expansion)
        }
    }
    return total
}

private fun obtainCols(input: List<String>): List<Int> {
    val cols: MutableList<Int> = mutableListOf()
    for (i in 0..<input[0].length) {
        var col = ""
        for (row in input) col += row[i]
        val count = col.count { char -> char == '#' }
        if (count == 0) cols.add(i)
    }
    return cols
}


fun main() {
    val testInput = readInput("Day11_test")
    check(part(testInput, 2) == 374L)
    check(part(testInput, 1_000_000) == 82000210L)

    val input = readInput("Day11")
    check(part(input, 2) == 10490062L)
    check(part(input, 1_000_000) == 382979724122L)
}
