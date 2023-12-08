private enum class CardNames {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND
}

private enum class Letters {
    T,
    J,
    Q,
    K,
    A,
}

private fun determineHandName(card: Map<Char, Int>): CardNames {
    if (card.containsValue(5)) return CardNames.FIVE_OF_A_KIND
    if (card.containsValue(4)) return CardNames.FOUR_OF_A_KIND
    if (card.containsValue(3) && card.containsValue(2)) return CardNames.FULL_HOUSE
    if (card.containsValue(3)) return CardNames.THREE_OF_A_KIND
    if (card.filter { it.value == 2 }.size == 2) return CardNames.TWO_PAIR
    if (card.containsValue(2)) return CardNames.ONE_PAIR
    return CardNames.HIGH_CARD
}

private data class Hand(val name: CardNames, val cards: String, val bet: Int)

private val makeHand = {handString: String, bet: Int ->
    val cardMap = handString.groupBy { it }.mapValues { it.value.size }
    Hand(determineHandName(cardMap), handString, bet)
}

private val makeHands = { input: List<String> ->
    input.map { line ->
        val (strHand, bet) = line.split(" ")
        makeHand(strHand, bet.toInt())
    }
}


private fun compareHands(h1: Hand, h2: Hand): Int {
    if (h1.name.ordinal > h2.name.ordinal) return 1
    if (h1.name.ordinal < h2.name.ordinal) return -1

    // card types are equal, so we are going to compare card by card
    val hand1 = h1.cards.toList()
    val hand2 = h2.cards.toList()

    for (i in hand1.indices) {
        val card1 = hand1[i]
        val card2 = hand2[i]

        if (!card1.isDigit() && card2.isDigit()) return 1
        if (!card2.isDigit() && card1.isDigit()) return -1

        if (!card1.isDigit() && !card2.isDigit()) {
            val l1 = Letters.valueOf(card1.toString())
            val l2 = Letters.valueOf(card2.toString())
            if (l1.ordinal > l2.ordinal) return 1
            if (l2.ordinal > l1.ordinal) return -1
            continue
        }

        // the two cards are digits, so compare them by its value
        if (card1.code > card2.code) return 1
        if (card2.code > card1.code) return -1
    }
    return 0
}

private val calculateTotalWinnings = { hands: List<Hand> -> hands.mapIndexed { i, hand -> hand.bet * (i + 1) }.sum() }

private val allParts =
    { hands: List<Hand> -> calculateTotalWinnings(hands.sortedWith { h1, h2 -> compareHands(h1, h2) }) }

private val improveHands = {hands: List<Hand> -> hands.map {hand ->
        val jCount = hand.cards.count { it == 'J' }
        val improvedCard = when (hand.name) {
            CardNames.HIGH_CARD -> CardNames.ONE_PAIR
            CardNames.ONE_PAIR -> CardNames.THREE_OF_A_KIND
            CardNames.TWO_PAIR -> if (jCount == 1) CardNames.FULL_HOUSE else CardNames.FOUR_OF_A_KIND
            CardNames.THREE_OF_A_KIND -> CardNames.FOUR_OF_A_KIND
            else -> CardNames.FIVE_OF_A_KIND
        }

        if (jCount == 0) hand else Hand(improvedCard, hand.cards.replace('J', '1'), hand.bet)
    }
}

fun main() {
    val testInput = readInput("Day07_test")
    val testHands = makeHands(testInput)
    val testImprovedHands = improveHands(testHands)

    check(allParts(testHands) == 6440)
    check(allParts(testImprovedHands) == 5905)

    val input = readInput("Day07")
    val hands = makeHands(input)
    val improvedHands = improveHands(hands)

    check(allParts(hands) == 253603890)
    check(allParts(improvedHands) == 253630098)
}
