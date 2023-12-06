private class Race(val time: Long, val record: Long)

private fun parseInput(input: List<String>): List<Race> {
    val dRegex = Regex("""\d+""")
    val times = dRegex.findAll(input[0]).map { it.value.toLong() }.toList()
    val records = dRegex.findAll(input[1]).map { it.value.toLong() }.toList()

    val races: MutableList<Race> = mutableListOf()
    for (i in times.indices) {
        val time = times[i]
        val record = records[i]
        races.add(Race(time, record))
    }
    return races
}

private fun countWays2Win(race: Race): Int {
    var ways2Win = 0
    for (timeHold in 0..race.time) {
        val distance = timeHold * (race.time - timeHold)
        if (distance > race.record) {
            ways2Win++
        }
    }
    return ways2Win
}



private val joinAndObtain = {line: String -> line.replace("\\s".toRegex(), "").split(":")[1].toLong() }
private val removeKerning = {input:List<String> -> Race( joinAndObtain(input[0]), joinAndObtain(input[1]) ) }


private val part1 = {input: List<String> -> parseInput(input).fold(1) { acc, race -> acc * countWays2Win(race) } }
private val part2 = {input: List<String> -> countWays2Win(removeKerning(input)) }

fun main() {
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)

    val input = readInput("Day06")
    check(part1(input) == 293046)
    check(part2(input) == 35150181)
}
