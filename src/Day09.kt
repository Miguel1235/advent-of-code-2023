private fun obtainNewSequence(sequence: List<Int>): List<Int> {
    val newSequence: List<Int> = mutableListOf()
    for (i in 1..<sequence.size) {
        val curr = sequence[i]
        val prev = sequence[i - 1]
        newSequence.addLast(curr - prev)
    }
    return newSequence
}

private val string2IntList = { input: String -> input.split(" ").map { it.toInt() } }

private val isZeroSequence = { sequence: List<Int> -> sequence.filter { it == 0 }.size == sequence.size }

private fun obtainSequences(history: String): List<List<Int>> {
    val sequence = string2IntList(history)
    val sequences: List<List<Int>> = mutableListOf(sequence)

    do {
        val newSeq = obtainNewSequence(sequences.last())
        sequences.addLast(newSeq)
    } while (!isZeroSequence(newSeq))
    return sequences
}

private val predictNextHistoryValue =
    { sequences: List<List<Int>> -> sequences.fold(0) { acc: Int, sq: List<Int> -> acc + sq.last() } }


private fun predictValPart2(sequences: List<List<Int>>): Int {
    val sp = fillPlaceHolders(sequences)
    for (i in sp.size - 1 downTo 1) {
        val prev = sp[i]
        val curr = sp[i - 1]
        val x = curr[1] - prev.first()
        sp[i - 1][0] = x
    }
    return sp.first().first()
}

private fun fillPlaceHolders(sequences: List<List<Int>>): List<MutableList<Int>> {
    val sequenceWithPlaceHolders: MutableList<MutableList<Int>> = mutableListOf()
    for (sequence in sequences) {
        if (isZeroSequence(sequence)) {
            sequence.addFirst(0)
            sequenceWithPlaceHolders.addLast(sequence.toMutableList())
            continue
        }
        sequence.addFirst(999)
        sequenceWithPlaceHolders.addLast(sequence.toMutableList())
    }
    return sequenceWithPlaceHolders
}

private val part1 = { input: List<String> ->
    input.fold(0) { acc: Int, history -> acc + predictNextHistoryValue(obtainSequences(history)) }
}

private val part2 =
    { input: List<String> -> input.fold(0) { acc: Int, history -> acc + predictValPart2(obtainSequences(history)) } }

fun main() {
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)

    val input = readInput("Day09")
    check(part1(input) == 1581679977)
    check(part2(input) == 889)
}
