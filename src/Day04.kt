private class Card(val id: Int, val winningNumbers: List<Int>, val ownNumbers: List<Int>) {
    val ownWinnerNumbers = winningNumbers.filter { w -> ownNumbers.contains(w) }
    val points = calculatePoints(ownWinnerNumbers.size)
    private fun calculatePoints(size: Int): Int {
        if (size <= 2) return size
        return 2 * calculatePoints(size - 1)
    }
    fun calculateCopies(cards: List<Card>): Int {
        var copies = 0
        val stack: MutableList<Card> = mutableListOf(this)
        while (stack.isNotEmpty()) {
            copies++
            val card = stack.removeLast()
            val copies2Make = card.ownWinnerNumbers.size
            for (i in card.id + 1..card.id + copies2Make) {
                stack.add(cards[i-1])
            }
        }
        return copies
    }
}

private val obtainNumbers = { input: String -> input.trim().replace(Regex("\\s+"), " ").split(" ").map { it.toInt() } }

private fun createCards(input: List<String>): List<Card> {
    val cardRegex = Regex("""(\d+): ([\d\s]+) \| ([\d\s]+)""")
    val cards: MutableList<Card> = mutableListOf()

    for (line in input) {
        val results = cardRegex.findAll(line)
        for (r in results) {
            val g = r.groupValues
            cards.add(Card(g[1].toInt(), obtainNumbers(g[2]), obtainNumbers(g[3])))
        }
    }
    return cards
}

private val part1 = { cards: List<Card> -> cards.fold(0) { acc, card -> acc + card.points } }

private val part2 = { cards: List<Card> -> cards.fold(0) { acc, card -> acc + card.calculateCopies(cards) } }

fun main() {
    val inputTest = readInput("Day04_test")
    val cardsTest = createCards(inputTest)
    check(part1(cardsTest) == 13)
    check(part2(cardsTest) == 30)

    val input = readInput("Day04")
    val cards = createCards(input)
    check(part1(cards) == 28538)
    check(part2(cards) == 9425061)
}