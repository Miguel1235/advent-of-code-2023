import kotlin.math.pow

private fun part1(input: List<String>): Int {
    return input.fold(0) {acc, row -> acc+calculateArrangement(row) }
}

private fun obtainConditions(row: List<String>): String {
    return row[0]
}

private fun obtainGroups(row: List<String>): List<Int> {
    return row[1].split(",").map {it.toInt()}
}

private fun calculateArrangement(row: String): Int {
    val rowSplit = row.split(" ")
    var total = 0

    val conditions = obtainConditions(rowSplit)
    val groups = obtainGroups(rowSplit)

    val possibleResults = binaryCount(conditions)
    for(possibleR in possibleResults) {
        val groupCurrent = currentGroups(possibleR)
        if(groupCurrent.size != groups.size) continue
        if(groupCurrent == groups) total++
    }
    return total
}

private fun binaryCount(input: String): List<String> {
    var newStr = input
    val maxNumber = 2.0.pow(input.count {it == '?'}.toDouble()) -1

    val replacedI = input.replace("?", "x")
    val regex = Regex("""x""")

    val matches = regex.findAll(replacedI).toList()
    val indexR = matches.map { it.range.first }

    val possiblesStrings: MutableList<String> = mutableListOf()

    for(i in 0..maxNumber.toInt()) {
        val values2Replace = Integer.toBinaryString(i).padStart(input.count {it == '?'},'.').replace('1', '#').replace('0','.').toList()
        for(r in indexR.indices) {
            val index = indexR[r]
            val replace = values2Replace[r]
            newStr = newStr.replaceRange(index, index+1, replace.toString())
        }
        possiblesStrings.add(newStr)
    }
    return possiblesStrings
}

private fun currentGroups(line: String): List<Int> {
    val regex = Regex("""#+""")
    val matches = regex.findAll(line).toList()
    return matches.map {it.groups}.map { it[0]!!.value.length}
}

private fun unfoldRow(groups: String, conditions: String): String {
    var newGroup = groups
    var newConditions = conditions

    for(i in 0..3) {
        newGroup= "$newGroup,$groups"
        newConditions = "$newConditions?$conditions"
    }
    return "$newConditions $newGroup"
}

private fun expandInput(input: List<String>): List<String> {
    return input.map { r ->
        val (conditions, group) = r.split(" ")
        unfoldRow(group, conditions)
    }
}

private fun part2(input: List<String>): Int {
    val expandedInput = expandInput(input)
    val result = part1(expandedInput)
    println("The result is $result")
    return 0
}

fun main() {
    val testInput = readInput("Day12_test")
    // check(part1(testInput) == 21)
    part1(testInput).println()

    // part2(testInput)
    
    // val input = readInput("Day12")
    // check(part1(input) == 7506)

    // part2(input).println()
}
