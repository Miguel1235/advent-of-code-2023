private fun createNodes(input: List<String>): Map<String, Pair<String, String>> {
    val nodeRegex = Regex("""[A-Z0-9]+""")

    val nodes: MutableMap<String, Pair<String, String>> = mutableMapOf()
    for (i in 2..<input.size) {
        val results = nodeRegex.findAll(input[i])
        val (name, left, right) = results.map { it.value }.toList()
        nodes[name] = Pair(left, right)
    }
    return nodes
}

private fun part1(
    nodes: Map<String, Pair<String, String>>,
    instructions: Sequence<Char>,
    start: String,
    onlyLast: Boolean
): Int {
    var curr = start
    val target = "ZZZ"
    var node = nodes[start]
    var steps = 0
    for (instruction in instructions) {
        if (!onlyLast && curr == target) break
        if (onlyLast && curr.endsWith('Z')) break
        if (instruction == 'L') curr = node!!.first
        if (instruction == 'R') curr = node!!.second
        node = nodes[curr]
        steps++
    }
    return steps
}

fun findLCM(n1: Long, n2: Long): Long {
    val steps = if (n1 > n2) n1 else n2
    val maxLcm = (n1 * n2)
    var lcm = steps
    while (lcm <= maxLcm) {
        if (lcm % n1 == 0L && lcm % n2 == 0L) return lcm
        lcm += steps
    }
    return maxLcm
}

fun findLCMS(numbers: List<Long>): Long {
    var result: Long = numbers[0]
    for (i in 1 until numbers.size) result = findLCM(result, numbers[i])
    return result
}

private fun part2(nodes: Map<String, Pair<String, String>>, instructions: Sequence<Char>): Long {
    val startingNodes = nodes.filter { n -> n.key.endsWith('A') }

    val steps: MutableList<Long> = mutableListOf()
    for (startNode in startingNodes) {
        steps.add(part1(nodes, instructions, startNode.key, true).toLong())
    }
    return findLCMS(steps)
}

private fun createInstructions(instructions: String): Sequence<Char> {
    return sequence { while (true) instructions.forEach { char -> yield(char) } }
}

fun main() {
    val testInput = readInput("Day08_test")
    val testNodes = createNodes(testInput)
    val testInstructions = createInstructions(testInput[0])
    // check(part1(testNodes, testInstructions) == 2)

    part2(testNodes, testInstructions)

    val input = readInput("Day08")
    val nodes = createNodes(input)
    val instructions = createInstructions(input[0])
    check(part1(nodes, instructions, "AAA", false) == 18023)
    check(part2(nodes, instructions) == 14449445933179)
}
