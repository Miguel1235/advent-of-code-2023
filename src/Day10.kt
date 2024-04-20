private fun part1(input: List<List<Char>>): Int {
    // val pipes = listOf(
    //     Pipe('|', Coordinates.NORTH, Coordinates.SOUTH),
    //     Pipe('-', Coordinates.EAST, Coordinates.WEST),
    //     Pipe('L', Coordinates.NORTH, Coordinates.EAST),
    //     Pipe('J', Coordinates.NORTH, Coordinates.WEST),
    //     Pipe('7', Coordinates.SOUTH, Coordinates.WEST),
    //     Pipe('F', Coordinates.SOUTH, Coordinates.EAST),
    // )

    val pipes = mapOf(
        '|' to Pipe('|', Coords.NORTH, Coords.SOUTH),
        '-' to Pipe('-', Coords.EAST, Coords.WEST),
        'L' to Pipe('L', Coords.NORTH, Coords.EAST),
        'J' to Pipe('J', Coords.NORTH, Coords.WEST),
        '7' to Pipe('7', Coords.SOUTH, Coords.WEST),
        'F' to Pipe('F', Coords.SOUTH, Coords.EAST),
    )

    val orientation = Coords.EAST

    val r = 1
    val c = 1
    val nh = discoverNeighbour(input, pipes['F']!!,r,c, pipes)

    return 0
}

private fun prettyPrint(input: List<List<Char>>) {
    for (row in input) {
        println(row.joinToString(""))
    }
}

private fun discoverNeighbour(input: List<List<Char>>, pipe: Pipe, r: Int, c: Int, pipes: Map<Char, Pipe>){


    val nbs = listOf(
        input.getOrNull(r - 1)?.getOrNull(c) ?: '.', // top
        input.getOrNull(r - 1)?.getOrNull(c - 1) ?: '.', // topLeft
        input.getOrNull(r - 1)?.getOrNull(c + 1) ?: '.', // topRight
        input.getOrNull(r + 1)?.getOrNull(c) ?: '.', // bottom
        input.getOrNull(r + 1)?.getOrNull(c - 1) ?: '.', // bottomLeft
        input.getOrNull(r + 1)?.getOrNull(c + 1) ?: '.', // bottomRight

        input.getOrNull(r)?.getOrNull(c - 1) ?: '.', // left
        input.getOrNull(r)?.getOrNull(c + 1) ?: '.' // right
    ).filter { n -> n != '.' }.map {p -> pipes[p]}

    println(nbs)
    println(pipe)

    // for(row in input.indices) {
    //     for(col in input[row].indices) {
    //         val title = input[row][col]
    //         println("Row: $row - Col: $col - title: $title")
    //     }
    // }

}

enum class Coords {
    NORTH, SOUTH, EAST, WEST
}

data class Pipe(val name: Char, val i1: Coords, val i2: Coords)

private fun part2(input: List<String>): Int {
    return 0
}

private fun parseInput(input: List<String>): List<List<Char>> {
    return input.map { r -> r.toList() }
}

fun main() {
    val testInput = readInput("Day10_test")
    check(part1(parseInput(testInput)) == 0)


    // val input = readInput("Day10")
    // part1(input).println()
    // part2(input).println()
}
