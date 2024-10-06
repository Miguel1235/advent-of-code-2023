private fun part1(input: List<List<Char>>): Int {
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

    discoverNeighbour(input, pipes)

    return 0
}

private fun discoverNeighbour(input: List<List<Char>>, pipes: Map<Char, Pipe>){
    val r = 1
    val c = 1

    var currentPipe = pipes[input[r][c]]!!
    var currRPosition = r
    var currCPosition = c

    val alreadyCounted: MutableList<String> = mutableListOf("$r:$c")

    while (true) {
        println("****************")
        println("Pipe: $currentPipe - Starting point: ${input[currRPosition][currCPosition]} -$currRPosition-$currCPosition")
        println("Alreay counted: $alreadyCounted")

        val nbs = mapOf(
            "${currRPosition - 1}:${currCPosition}:north" to pipes[input.getOrNull(currRPosition - 1)?.getOrNull(currCPosition)], // top
            "${currRPosition + 1}:${currCPosition}:south" to pipes[input.getOrNull(currRPosition + 1)?.getOrNull(currCPosition)], // bottom
            "${currRPosition}:${currCPosition-1}:west" to pipes[input.getOrNull(currRPosition)?.getOrNull(currCPosition - 1)], // left
            "${currRPosition}:${currCPosition+1}:east" to pipes[input.getOrNull(currRPosition)?.getOrNull(currCPosition + 1)] // right
        )
            .filter { it.value != null }
            .filter { !alreadyCounted.contains(it.key.split(":").take(2).joinToString(":")) }

        println("Possible nbs: $nbs")
        // check that there is always 2 neighbors

        // TODO: FIX THIS THING BECAUSE OF NORTH AND SOUTH, IT DEPENDS ON WHERE IT IS - SEE F & J EXAMPLE POSITION
        val nextPipe = nbs.filter {
//            println("Checking if $it is a valid pipe for the current pipe: $currentPipe")
//            println(currentPipe.south?.contains(it.value!!.name))
            currentPipe.east?.contains(it.value!!.name) == true && it.key.split(":")[2] == "east" ||
            currentPipe.west?.contains(it.value!!.name) == true && it.key.split(":")[2] == "west"||
            currentPipe.south?.contains(it.value!!.name) == true && it.key.split(":")[2] == "south"||
            currentPipe.north?.contains(it.value!!.name) == true && it.key.split(":")[2] == "north"
        }
        println("All next pipes $nextPipe")

        if(nextPipe.isEmpty()) {
//            println("We finished the loop!!!")
            break
        }


//        println("The next pipe is ${nextPipe.values.first()}")

        currentPipe = nextPipe.values.first()!!


        val keys = nextPipe.keys.first().split(":")
        currRPosition = keys[0].toInt()
        currCPosition = keys[1].toInt()

        alreadyCounted.add("$currRPosition:$currCPosition")
    }

    println("The path is: $alreadyCounted - with farthest point from the start is ${alreadyCounted.size/2}")
}

enum class Coords {
    NORTH, SOUTH, EAST, WEST
}

data class Pipe(
    val name: Char,
    val north: List<Char>? = null,
    val south: List<Char>? = null,
    val east: List<Char>? = null,
    val west: List<Char>? = null,
)

private fun part2(input: List<String>): Int {
    return 0
}

private fun parseInput(input: List<String>): List<List<Char>> {
    return input.map { r -> r.toList() }
}

fun main() {
    val testInput = readInput("Day10_test")
    check(part1(parseInput(testInput)) == 0)
}
