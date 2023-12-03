private fun findCords(r: Int, c: Int, engine: List<List<Char>>): Map<String, Char?> {
    return mapOf(
        "bottom" to engine.getOrNull(r + 1)?.getOrNull(c),
        "top" to engine.getOrNull(r - 1)?.getOrNull(c),
        "left" to engine.getOrNull(r)?.getOrNull(c - 1),
        "leftBottom" to engine.getOrNull(r + 1)?.getOrNull(c - 1),
        "leftTop" to engine.getOrNull(r - 1)?.getOrNull(c - 1),
        "right" to engine.getOrNull(r)?.getOrNull(c + 1),
        "rightBottom" to engine.getOrNull(r + 1)?.getOrNull(c + 1),
        "rightTop" to engine.getOrNull(r - 1)?.getOrNull(c + 1)
    )
}

private fun checkAdjacent(r: Int, c: Int, engine: List<List<Char>>): Boolean {
    for (cord in findCords(r, c, engine).values) {
        if (cord != null && cord != '.' && !cord.isDigit()) return true
    }

    return false
}

private fun part1(input: List<String>, engine: List<List<Char>>): Number {
    val numsRegex = Regex("""\d+""")
    var total = 0
    for (i in input.indices) {
        val row = input[i]
        val results = numsRegex.findAll(row)
        for (r in results) {
            val range = r.range
            for (col in range.first..range.last) {
                if (!checkAdjacent(i, col, engine)) continue
                total += r.value.toInt()
                break
            }
        }
    }
    return total
}

private class PartNumber(val r: Int, val cs: Int, val ce: Int, val num: Int)

private fun findNumbers(input: List<String>): List<PartNumber> {
    val numsRegex = Regex("""\d+""")
    val partNumbers: MutableList<PartNumber> = mutableListOf()
    for (i in input.indices) {
        val row = input[i]
        val results = numsRegex.findAll(row)
        for (r in results) {
            partNumbers.add(PartNumber(i, r.range.first, r.range.last, r.value.toInt()))
        }
    }
    return partNumbers
}

private class Asterisk(val r: Int, val c: Int)

private fun findPossibleGears(input: List<String>): List<Asterisk> {
    val gearRegex = Regex("""\*""")
    val asterisks: MutableList<Asterisk> = mutableListOf()
    for (i in input.indices) {
        val row = input[i]
        val gearFinds = gearRegex.findAll(row)

        for (gearFind in gearFinds) {
            asterisks.add(Asterisk(i, gearFind.range.first))
        }
    }
    return asterisks
}

private fun findN(r: Int, c: Int, pn: List<PartNumber>): List<PartNumber> {
    // bottom
    val rb = pn.filter { p -> p.r == r + 1 && c >= p.cs - 1 && c <= p.ce + 1 }

    // top
    val rt = pn.filter { p -> p.r == r - 1 && c >= p.cs - 1 && c <= p.ce + 1 }

    // left & right
    val rlr = pn.filter { p -> p.r == r && c >= p.cs - 1 && c <= p.ce + 1 }
    return rb + rt + rlr
}


private fun part2(input: List<String>): Number {
    val partNumbers = findNumbers(input)
    val gears = findPossibleGears(input).map {pg -> findN(pg.r, pg.c, partNumbers) }.filter { pg -> pg.size == 2 }
    return gears.fold(0) {acc, gear -> acc + (gear[0].num*gear[1].num)  }
}

fun main() {
    val inputTest = readInput("Day03_test")
    val engineTest: List<List<Char>> = inputTest.map { row -> row.toCharArray().toList() }
    check(part1(inputTest, engineTest) == 4361)
    check(part2(inputTest) == 467835)

    val input = readInput("Day03")
    val engine: List<List<Char>> = input.map { row -> row.toCharArray().toList() }
    part1(input, engine).println() // 557705
    part2(input).println() // 84266818
}