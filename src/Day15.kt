private val obtainHash = { word: String -> word.fold(0) { currentValue, c -> (currentValue + c.code) * 17 % 256 } }

private val part1 = { input: List<String> -> input.fold(0) { acc, word -> acc + obtainHash(word) } }

private fun calculatePower(boxes: MutableMap<Int, MutableMap<String, Int>>): Int {
    var total = 0
    for(box in boxes) {
        var currentSlot = 1
        box.value.values.forEach { focalLength ->
            total += (1+box.key) * currentSlot *focalLength
            currentSlot++
        }
    }
    return total
}

private fun part2(input: List<String>): Int {
    val regex = Regex("""([a-z]*)([-=])([0-9])*""")
    val boxes: MutableMap<Int, MutableMap<String, Int>> = mutableMapOf()

    for (instruction in input) {
        val (label, op, focal) = regex.findAll(instruction).map { it.groups }.toList()[0]
            .filterIndexed { i, _ -> i != 0 }
            .map { it?.value }

        val box = obtainHash(label.toString())

        when(op) {
            "=" -> {
                if(!boxes.containsKey(box)) {
                    // println("adding new box")
                    boxes[box] = mutableMapOf(Pair(label.toString(), focal!!.toInt()))
                } else {
                    val focals = boxes[box]!!
                    focals[label.toString()] = focal!!.toInt()
                    boxes[box] = focals
                }
            }
            "-" -> {
                val f = boxes[box]
                f?.remove(label.toString())
            }
        }
    }
    return calculatePower(boxes)
}

private val obtainValues = { input: List<String> -> input[0].split(",") }


fun main() {
    val testInput = obtainValues(readInput("Day15_test"))
    check(part1(testInput) == 1320)
    check(part2(testInput) == 145)

    val input = obtainValues(readInput("Day15"))
    check(part1(input) == 514281)
    check(part2(input) == 244199)
}
