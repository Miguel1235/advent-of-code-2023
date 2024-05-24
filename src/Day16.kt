private enum class Dirs {
    LEFT, RIGHT, UP, DOWN
}

private data class Beam(
    val moving: Dirs,
    var positions: MutableList<Pair<Int, Int>> = mutableListOf(Pair(1, 0)),
)

private class PoweredGrid {
    val activeBeams = mutableListOf(Beam(Dirs.RIGHT))
    val inactiveBeams = mutableListOf<Beam>()

    fun moveBeans(r: Int, c: Int) {
        for (currentBeam in activeBeams) {
            val lastPosition = currentBeam.positions[currentBeam.positions.lastIndex]
            when (currentBeam.moving) {
                Dirs.LEFT -> {
                    if (lastPosition.first == r) {
                        currentBeam.positions.add(Pair(lastPosition.first, lastPosition.second - 1))
                    }
                }

                Dirs.RIGHT -> {
                    if (lastPosition.first == r) {
                        currentBeam.positions.add(Pair(lastPosition.first, lastPosition.second + 1))
                    }
                }

                Dirs.UP -> {
                    if (lastPosition.second == c) {
                        currentBeam.positions.add(Pair(lastPosition.first - 1, lastPosition.second))
                    }
                }

                Dirs.DOWN -> {
                    if (lastPosition.second == c) {
                        currentBeam.positions.add(Pair(lastPosition.first + 1, lastPosition.second))
                    }
                }
            }
        }
    }

    fun splitBean() {
        val total = activeBeams.size
        for(i in 0..<total) {
            val beam = activeBeams[i]
            val lastPosition = beam.positions[beam.positions.lastIndex]
            inactiveBeams.add(beam)
            activeBeams.removeLast()
            activeBeams.add(Beam(Dirs.UP, mutableListOf(Pair(lastPosition.first - 1, lastPosition.second))))
            activeBeams.add(Beam(Dirs.DOWN, mutableListOf(Pair(lastPosition.first + 1, lastPosition.second))))
        }
    }

    override fun toString(): String {
        return "ActiveBeams: $activeBeams - InactiveBeams: $inactiveBeams"
    }

}

private fun part1(grid: List<String>): Int {
    val poweredGrid = PoweredGrid()

    for (i in grid.indices) {
        val row = grid[i]
        for (j in row.indices) {
            if (i == 0 && j == 0) continue
            val item = row[j]
            when (item) {
                '/' -> {
                    println("Right Mirror")
                }

                '\\' -> {
                    println("Left Mirror")
                }

                '-' -> {
                    println("Horizontal splitter")
                }

                '|' -> {
                    println("Vertical splitter")
                    poweredGrid.splitBean()
                }

                else -> {
                    println("space")
                    poweredGrid.moveBeans(i, j)
                }
            }
        }
    }
    println(poweredGrid)
    return 0
}

private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val testInput = readInput("Day16_test")
    check(part1(testInput) == 0)

    // val input = readInput("Day16")
    // part1(input).println()
    // part2(input).println()
}
