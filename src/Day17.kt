data class Node(val name: String)
data class Edge(val source: Node, val destination: Node, val weight: Int, val direction: Direction)

enum class Direction {
    LEFT, RIGHT, TOP, BOTTOM
}

data class Options(var left: Edge? = null, var right: Edge? = null, var bottom: Edge? = null, var top: Edge? = null)

class Graph {
    private val adjacencyMap = mutableMapOf<Node, Options>()

    private val edges: List<Edge>
        get() = adjacencyMap.map { (_, opts) ->
            listOfNotNull(opts.top, opts.left, opts.right, opts.bottom)
        }.flatten()


    fun createNode(name: String): Node {
        val node = Node(name)
        adjacencyMap[node] = Options()
        return node
    }

    fun findNode(name: String): Node? {
        for(key in adjacencyMap.keys) {
            if(key.name == name) return key
        }
        return null
    }

    fun addEdge(source: Node, destination: Node, weight: Int, direction: Direction) {
        val edge = Edge(source, destination, weight, direction)
        when (direction) {
            Direction.LEFT -> adjacencyMap[source]?.left = edge
            Direction.TOP -> adjacencyMap[source]?.top = edge
            Direction.RIGHT -> adjacencyMap[source]?.right = edge
            Direction.BOTTOM -> adjacencyMap[source]?.bottom = edge
        }
    }

    fun shortestPath(source: Node, target: Node) {
        val dist = mutableMapOf<Node, Int>()
        val prev = mutableMapOf<Node, Pair<Node?, Direction>?>()
        val q = findDistinctNodes()

        q.forEach { v ->
            dist[v] = Integer.MAX_VALUE
            prev[v] = null
        }
        dist[source] = 0

        while (q.isNotEmpty()) {
            val u = q.minByOrNull { dist[it] ?: 0 }
            q.remove(u)
            if (u == target) {
                break
            }

            val fe = edges
                .filter { it.source == u }

            for (edge in fe) {

//                val count = prev.values.filterNotNull().takeLast(3).map {it.second}.groupingBy { it }.eachCount()

                val m = prev.values.filterNotNull().groupBy { it.first?.name ?: "" }.map { (_, group) -> group.last() }
//                println(m)
                val count = m.takeLast(3).map {it.second}.groupingBy { it }.eachCount()

                println(count)

                if(count.containsKey(edge.direction) && count[edge.direction]!! >= 3) {
                        println("cannot go more down")
                        continue
                }

                val v = edge.destination
                val alt = (dist[u] ?: 0) + edge.weight
                if (alt < (dist[v] ?: 0)) {
                    dist[v] = alt
                    prev[v] = Pair(u, edge.direction)
                }
            }
        }

        val r = dist.filter { it.key == target }
        println("The result is $r")
//        println(prev)
    }

    private fun findDistinctNodes(): MutableSet<Node> {
        val nodes = mutableSetOf<Node>()
        edges.forEach {
            nodes.add(it.source)
            nodes.add(it.destination)
        }
        return nodes
    }
}

private fun parseInput(input: List<String>): List<List<Int>> {
    return input.map { r -> r.toList().map { it.toString().toInt()}}
}

data class InfoPosition(val node: Node?, val weight: Int?)

private fun findNeighbors(graph: Graph, r: Int, c: Int, input: List<List<Int>>): Map<Direction, InfoPosition> {
    return mapOf(
        Direction.BOTTOM to InfoPosition(graph.findNode("${r+1}-${c}"), input.getOrNull(r + 1)?.getOrNull(c)),
        Direction.TOP to InfoPosition(graph.findNode("${r-1}-${c}"), input.getOrNull(r - 1)?.getOrNull(c)),
        Direction.LEFT to InfoPosition(graph.findNode("${r}-${c-1}"), input.getOrNull(r)?.getOrNull(c-1)),
        Direction.RIGHT to InfoPosition(graph.findNode("${r}-${c+1}"), input.getOrNull(r)?.getOrNull(c+1)),
    )
        .filterValues { it.node != null && it.weight != null }
}


private fun part1(input: List<List<Int>>): Int {
    val graph = Graph()


    for(iRow in input.indices) {
        val row = input[iRow]
        for(iCol in row.indices) {
            graph.createNode("$iRow-$iCol")
        }
    }

    for(iRow in input.indices) {
        val row = input[iRow]
        for(iCol in row.indices) {
                for ((k,v) in findNeighbors(graph, iRow, iCol, input).entries){
                    val source = graph.findNode("$iRow-$iCol")!!
//                    println(source)
                    graph.addEdge(source, v.node!!, v.weight!!, k)
                }
        }
    }

    val startNode = graph.findNode("0-0")!!
    val endNode = graph.findNode("${input.size-1}-${input[0].size-1}")!!

    graph.shortestPath(startNode, endNode)

    return 0
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val testInput = readInput("Day17_test")
    check(part1(parseInput(testInput)) == 0)

    // val input = readInput("Day17")
    // part1(input).println()
    // part2(input).println()
}
