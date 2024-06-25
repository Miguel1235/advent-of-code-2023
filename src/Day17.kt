data class Edge(val source: Int, val destination: Int, val weight: Int)

class Graph {
    private var nodes = 0
    private var adjacencyList: MutableMap<Int, MutableList<Edge>> = mutableMapOf()

    fun addNode(node: Int) {
        adjacencyList.putIfAbsent(node, mutableListOf())
        nodes++
    }

    fun addEdge(from: Int, to: Int, weight: Int) {
        if (adjacencyList[from] == null) addNode(from)
        adjacencyList[from]?.add(Edge(from, to, weight))

        // if(adjacencyList[to] == null) addNode(to)
        // adjacencyList[to]?.add(Edge(to, from, weight))
    }

    fun prettyPrint() {
        for (oVertex in adjacencyList) {
            for (dVertex in oVertex.value) {
                println("${oVertex.key} --(${dVertex.weight})--> ${dVertex.destination}")
            }
        }
    }

    override fun toString(): String {
        return adjacencyList.toString()
    }
}

private enum class Direction {
    LEFT, RIGHT, UP, DOWN
}

private fun obtainMovePair(row: Int, col: Int, map: List<List<Int>>): Map<Direction, Int?> {
    return mapOf(
        Direction.UP to map.getOrNull(row - 1)?.getOrNull(col),
        Direction.DOWN to map.getOrNull(row + 1)?.getOrNull(col),
        Direction.LEFT to map.getOrNull(row)?.getOrNull(col - 1),
        Direction.RIGHT to map.getOrNull(row)?.getOrNull(col + 1),
    )
}

private fun parseInput(input: List<String>): List<List<Int>> {
    return input.map { r -> r.toList().map { it.toString().toInt()}}
}

private fun part1(input: List<List<Int>>): Int {
    println(input)
    for(rowIdx in input.indices) {
        val row = input[rowIdx]
        for(colIdx in row.indices) {
            val value = row[colIdx]
            println("row: $rowIdx, col: $colIdx, value: $value")
            val ps = obtainMovePair(rowIdx, colIdx, input)
            println(ps)
        }
    }
    return 0
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    // val gh = Graph()
    // gh.addNode(0)
    // gh.addEdge(0, 2, 9)
    // gh.prettyPrint()

    val testInput = readInput("Day17_test")
    check(part1(parseInput(testInput)) == 0)

    // val input = readInput("Day17")
    // part1(input).println()
    // part2(input).println()
}
