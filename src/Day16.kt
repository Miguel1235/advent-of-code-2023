private enum class Dirs {
    LEFT, RIGHT, UP, DOWN
}

private data class Position(val r: Int, val c: Int)

private data class Beam(
    val movingInDirectionOf: Dirs = Dirs.RIGHT,
    var positions: MutableList<Position> = mutableListOf(Position(0, 0)),
    var currentItem: Char = '-',
    var isActive: Boolean = true,
)

private class PoweredGrid(val maxR: Int, val maxC: Int) {
    val beams = mutableListOf(Beam(Dirs.RIGHT, currentItem = '-'))
    fun splitBean(draw: Char, r: Int, c: Int): List<Beam> {
        val newBeams = mutableListOf<Beam>()
        for (beam in beams) {
            println("The last position of the beam was ${beam.positions.last()} and the split is on $r-$c")

            val lastBeamPosition = beam.positions.last()

            if (lastBeamPosition.r == r && lastBeamPosition.c == c) {
                if (beam.currentItem == '-' && draw == '|') {
                    // TODO: check if its possible to add a beam on that new position because of grid constraints
                    if(r-1 >= 0) {
                        newBeams.add(Beam(Dirs.UP, mutableListOf(Position(r - 1, c)), currentItem = draw))
                    }

                    newBeams.add(Beam(Dirs.DOWN, mutableListOf(Position(r + 1, c)), currentItem = draw))
                    beam.isActive = false
                }
            }
        }
        return newBeams
    }


    fun moveBeam(beam: Beam) {

        val lastP = beam.positions.last()
        if(!beam.isActive) return
        when (beam.movingInDirectionOf) {
                Dirs.LEFT -> {
//                    if (lastPosition.r == r) {
//                        currentBeam.positions.add(Pair(lastPosition.first, lastPosition.second - 1))
//                    }
                }

                Dirs.RIGHT -> {
                    println("Moving beam to ->")
                    beam.positions.add(Position(lastP.r, lastP.c + 1))
                    if(lastP.c + 1 == maxC) {
                        println("Going to desactivate the beam because it reached the end")
                        beam.isActive = false
                    }
                }

                Dirs.UP -> {
                    println("Moving up")
//                    if (lastPosition.second == c) {
//                        currentBeam.positions.add(Pair(lastPosition.first - 1, lastPosition.second))
//                    }
                }

                Dirs.DOWN -> {
                    println("Moving v")


                    beam.positions.add(Position(lastP.r+1, lastP.c))

                    if(lastP.r + 1 == maxR) {
                        println("Going to desactivate the beam because it reached the end of the row")
                        beam.isActive = false
                    }
                }
            }
    }

}

private fun part1(grid: List<String>): Int {
    val maxR = grid.size
    val maxC = grid[0].length

    val poweredGrid = PoweredGrid(maxR, maxC)


    var activeBeams = 1

//    while(activeBeams >= 1) {
//
//        activeBeams = poweredGrid.beams.fold(0) {acc, beam -> acc + if(beam.isActive) 1 else 0}
//
//        println("Number of active beams: $activeBeams")
//        val beam = poweredGrid.beams.first { beam -> beam.isActive }
//        val lastP = beam.positions.last()
//
//        if (beam.currentItem == '-') {
//            for (i in lastP.c until maxC) {
//                println("The beam is on the position ${lastP.r}-$i")
//                val item = grid[lastP.r][i]
//                when (item) {
//                    '/' -> {
//                        println("/")
//                    }
//
//                    '\\' -> {
//                        println("|")
//                    }
//
//                    '-' -> {
//                        println("-")
//                    }
//
//                    '|' -> {
//                        println("|")
//                        val newBeams = poweredGrid.splitBean('|', lastP.r, i)
//                        println("The new beams to add are: $newBeams")
//
////                        println("Beams: ${poweredGrid.beams}")
////                        println("NewBeams: $newBeams")
//                        poweredGrid.beams.addAll(newBeams)
//                    }
//                    else -> {
//                        println(".")
//                        println("Moving the beam to the left/right")
//                        poweredGrid.moveBeam(beam)
//                    }
//                }
//            }
//        } else {
//            // The beam is |
//            println("The beam is |")
//
//            for (i in lastP.r until maxR) {
//                println("The beam is on the position ${lastP.r}-$i")
//                val item = grid[lastP.r][i]
//                println("The item on the grid is $item")
//                when (item) {
//                    '/' -> {
//                        println("/")
//                    }
//
//                    '\\' -> {
//                        println("|")
//                    }
//
//                    '-' -> {
//                        println("-")
//                        val newBeams = poweredGrid.splitBean('|', lastP.r, i)
//                        println("The new beams to add are: $newBeams")
//                        poweredGrid.beams.addAll(newBeams)
//                    }
//
//                    else -> {
//                        println("|")
//                        poweredGrid.moveBeam(beam)
//                    }
//                }
//            }
//
//        }
//    }

//    println(poweredGrid)
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
