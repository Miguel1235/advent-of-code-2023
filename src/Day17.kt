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

    fun findShortestPath() {
        for(vertex in adjacencyList) {
            println(vertex)
        }
    }

    override fun toString(): String {
        return adjacencyList.toString()
    }
}

private enum class Direction {
    LEFT, RIGHT, UP, DOWN
}

private fun obtainPossibleMoves(row: Int, col: Int, map: List<List<Node>>): Map<Direction, Node?> {
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

data class Node(val name: Int, val value: Int)

private fun makeNodes(input: List<List<Int>>): List<List<Node>> {
    var currNode = 0
    val newList: MutableList<List<Node>> = mutableListOf()
    for(rowIdx in input.indices) {
        val row = input[rowIdx]
        val subList: MutableList<Node> = mutableListOf()
        for(colIdx in row.indices) {
            subList.add(Node(currNode, row[colIdx]))
            currNode++
        }
        newList.add(subList)
    }
    return newList
}

private fun part1(input: List<List<Int>>): Int {
    val nodes = makeNodes(input)
    val gh = Graph()
    var nodeName = 0
    for(rowIdx in input.indices) {
        val row = input[rowIdx]
        for(colIdx in row.indices) {
            gh.addNode(nodeName)
            val ps = obtainPossibleMoves(rowIdx, colIdx, nodes)
            for(possibleMove in ps) {
                if(possibleMove.value == null) continue
                gh.addEdge(nodeName, possibleMove.value!!.name, possibleMove.value!!.value)
            }
            nodeName++
        }
    }
    // gh.prettyPrint()
    gh.findShortestPath()
    return 0
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    // gh.addNode(0)
    // gh.addEdge(0, 2, 9)
    // gh.prettyPrint()

    val testInput = readInput("Day17_test")
    check(part1(parseInput(testInput)) == 0)

    // val input = readInput("Day17")
    // part1(input).println()
    // part2(input).println()
}
