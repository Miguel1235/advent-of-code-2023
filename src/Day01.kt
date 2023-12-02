val findFirstNumber = { word: String -> Regex("""\d""").find(word)!!.value }

private fun text2Numbers(word: String): String {
    val regexNumbers = Regex("""(?=(one|two|three|four|five|six|seven|eight|nine))""")

    val text2Number = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9"
    )

    return regexNumbers.replace(word) { r -> text2Number[r.groupValues[1]]!! }
}


private val part1 = { words: List<String> -> words.fold(0) { acc, word -> acc + (findFirstNumber(word) + findFirstNumber(word.reversed())).toInt() } }

private val part2 = { words: List<String> -> part1(words.map { word -> text2Numbers(word) }) }

fun main() {
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}