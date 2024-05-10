private enum class Directions {
    NORTH, SOUTH, EAST, WEST
}

private fun obtainEndPosition(startPosition: Pair<Int, Int>, direction: Directions): Pair<Int, Int> {
    return when (direction) {
        Directions.NORTH -> {
            Pair(startPosition.first - 1, startPosition.second)
        }

        Directions.SOUTH -> {
            Pair(startPosition.first + 1, startPosition.second)
        }

        Directions.EAST -> {
            Pair(startPosition.first, startPosition.second + 1)
        }

        Directions.WEST -> {
            Pair(startPosition.first, startPosition.second - 1)
        }
    }
}

private fun canMove(input: List<String>, endPosition: Pair<Int, Int>): Boolean {
    return input.getOrNull(endPosition.first)?.getOrNull(endPosition.second) == '.'
}

private fun moveRock(
    input: MutableList<String>,
    startPosition: Pair<Int, Int>,
    endPosition: Pair<Int, Int>
): List<String> {
    val newRow = input[startPosition.first].replaceRange(startPosition.second, startPosition.second + 1, ".")
    input[startPosition.first] = newRow

    val movedR = input[endPosition.first].replaceRange(endPosition.second, endPosition.second + 1, "O")
    input[endPosition.first] = movedR

    return input
}

private fun calculateLoad(input: List<String>): Int {
    val rev = input.reversed()
    var total = 0
    for (i in rev.indices) {
        val row = rev[i]
        total += row.count { it == 'O' } * (i + 1)
    }
    return total
}

private fun part1(input: List<String>): Int {
    var ipt = input
    while (true) {
        val total = ipt.size * ipt[0].length
        var currTotal = 0
        for (i in ipt.indices) {
            val row = ipt[i]
            for (j in row.indices) {
                val possibleRock = row[j]
                if (possibleRock.equals('O')) {
                    val startPosition = Pair(i, j)
                    val endPosition = obtainEndPosition(startPosition, Directions.NORTH)
                    if (canMove(ipt, endPosition)) {
                        ipt = moveRock(ipt.toMutableList(), startPosition, endPosition)
                    } else {
                        currTotal++
                    }
                } else {
                    currTotal++
                }
            }
        }
        if (currTotal == total) break
    }
    return calculateLoad(ipt)
}

private fun part2(input: List<String>): Int {
    var ipt = input
    val directions = listOf(Directions.NORTH, Directions.WEST, Directions.SOUTH, Directions.EAST)
    for (loop in 1..1000){
        for (direction in directions) {
            while (true) {
                val total = ipt.size * ipt[0].length
                var currTotal = 0
                for (i in ipt.indices) {
                    val row = ipt[i]
                    for (j in row.indices) {
                        val possibleRock = row[j]
                        if (possibleRock.equals('O')) {
                            val startPosition = Pair(i, j)
                            val endPosition = obtainEndPosition(startPosition, direction)
                            if (canMove(ipt, endPosition)) {
                                ipt = moveRock(ipt.toMutableList(), startPosition, endPosition)
                            } else {
                                currTotal++
                            }
                        } else {
                            currTotal++
                        }
                    }
                }
                if (currTotal == total) break
            }
        }
    }
    return calculateLoad(ipt)
}

fun main() {
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 136)
    check(part2(testInput) == 64)

    val input = readInput("Day14")
    check(part1(input) == 108144)
    check(part2(input) == 108404)
}
