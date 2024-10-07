private fun findStatingPoint(input: List<List<Char>>): Pair<Int, Int> {
    for(rowIdx in input.indices) {
        val row = input[rowIdx]
        for(charIdx in row.indices) {
            val char = row[charIdx]
            if(char == 'S') {
                return Pair(rowIdx, charIdx)
            }
        }
    }
    return Pair(0,0)
}

private fun discoverNeighbour(input: List<List<Char>>, pipes: Map<Char, Pipe>, startingPoint: Pair<Int, Int>): Int {
    val (r,c) = startingPoint
    var currentPipe = pipes[input[r][c]]!!
    var currRPosition = r
    var currCPosition = c
    val alreadyCounted: MutableList<String> = mutableListOf("$r:$c")

    while (true) {
//        println("****************")
//        println("Pipe: $currentPipe - Starting point: ${input[currRPosition][currCPosition]} -$currRPosition-$currCPosition")
//        println("Alreay counted: $alreadyCounted")

        val nbs = mapOf(
            "${currRPosition - 1}:${currCPosition}:north" to pipes[input.getOrNull(currRPosition - 1)?.getOrNull(currCPosition)], // top
            "${currRPosition + 1}:${currCPosition}:south" to pipes[input.getOrNull(currRPosition + 1)?.getOrNull(currCPosition)], // bottom
            "${currRPosition}:${currCPosition-1}:west" to pipes[input.getOrNull(currRPosition)?.getOrNull(currCPosition - 1)], // left
            "${currRPosition}:${currCPosition+1}:east" to pipes[input.getOrNull(currRPosition)?.getOrNull(currCPosition + 1)] // right
        )
            .filter { it.value != null }
            .filter { !alreadyCounted.contains(it.key.split(":").take(2).joinToString(":")) }

//        println("Possible nbs: $nbs")

        // TODO: FIX THIS THING BECAUSE OF NORTH AND SOUTH, IT DEPENDS ON WHERE IT IS - SEE F & J EXAMPLE POSITION
        val nextPipe = nbs.filter {
//            println("Checking if $it is a valid pipe for the current pipe: $currentPipe")
//            println(currentPipe.south?.contains(it.value!!.name))
            currentPipe.east?.contains(it.value!!.name) == true && it.key.split(":")[2] == "east" ||
            currentPipe.west?.contains(it.value!!.name) == true && it.key.split(":")[2] == "west"||
            currentPipe.south?.contains(it.value!!.name) == true && it.key.split(":")[2] == "south"||
            currentPipe.north?.contains(it.value!!.name) == true && it.key.split(":")[2] == "north"
        }
//        println("All next pipes $nextPipe")

        if(nextPipe.isEmpty()) {
//            println("We finished the loop!!!")
            break
        }

        currentPipe = nextPipe.values.first()!!


        val keys = nextPipe.keys.first().split(":")
        currRPosition = keys[0].toInt()
        currCPosition = keys[1].toInt()

        alreadyCounted.add("$currRPosition:$currCPosition")
    }
    println("The path is: $alreadyCounted - with farthest point from the start is ${alreadyCounted.size/2}")
    return alreadyCounted.size/2
}

data class Pipe(
    val name: Char,
    val north: List<Char>? = null,
    val south: List<Char>? = null,
    val east: List<Char>? = null,
    val west: List<Char>? = null,
)

private fun parseInput(input: List<String>): MutableList<MutableList<Char>> {
    return input.map { r -> r.toList().toMutableList() }.toMutableList()
}

private fun part1(input: MutableList<MutableList<Char>>): Int {
    val north = listOf('7','|', 'F')
    val west = listOf('-', 'F', 'L')
    val east = listOf('-', 'J', '7')
    val south = listOf('J', 'L', '|')

    val pipes = mapOf(
        '|' to Pipe('|', north = north, south = south),
        '-' to Pipe('-', east = east, west = west),
        'L' to Pipe('L', east = east, north = north),
        'J' to Pipe('J', west = west, north = north),
        '7' to Pipe('7', south = south, west = west),
        'F' to Pipe('F', south = south, east = east),
    )

    val startingPoint = findStatingPoint(input)
    input[startingPoint.first][startingPoint.second] = 'F' // We should write some code to automatically detect where it should start
    return discoverNeighbour(input, pipes, startingPoint)
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val testInput = readInput("Day10_test")
    check(part1(parseInput(testInput)) == 8)

    val input = readInput("Day10")
    check(part1(parseInput(input)) == 7173)
}
