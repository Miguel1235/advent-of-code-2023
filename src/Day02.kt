data class Game(val id: Int, val cubes: List<Cubes>) {
    fun isGamePossible(): Boolean {
        for (cube in cubes) {
            val (blue, red, green) = cube
            if (red > 12 || green > 13 || blue > 14) return false
        }
        return true
    }

    fun maxColor(color: String): Int {
        var maxValue = 0
        for (bag in cubes) {
            if (bag[color] > maxValue) maxValue = bag[color]
        }
        return maxValue
    }
}

data class Cubes(var blue: Int, var red: Int, var green: Int) {
    operator fun get(color: String): Int {
        return when (color) {
            "blue" -> blue
            "red" -> red
            "green" -> green
            else -> 0
        }
    }
}

private fun makeGame(line: String): Game {
    val colorsRegex = Regex("""(\d+) (red|green|blue)""")

    val cubes: MutableList<Cubes> = mutableListOf()
    val inputBags = line.split(":")[1].split(";")
    for (ibag in inputBags) {
        val bagCount = mutableMapOf("green" to 0, "red" to 0, "blue" to 0)
        val results = colorsRegex.findAll(ibag)
        for (r in results) {
            val color = r.groupValues[2]
            val count = r.groupValues[1].toInt()
            bagCount[color] = bagCount[color]!! + count
        }
        cubes.add(Cubes(bagCount["blue"]!!, bagCount["red"]!!, bagCount["green"]!!))
    }

    val gameId = Regex("""Game \d+""").find(line)!!.value.split(" ")[1].toInt()
    return Game(gameId, cubes)
}

private val part1 = { games: List<Game> -> games.fold(0) { acc, game -> if (game.isGamePossible()) acc + game.id else acc } }

private val part2 = { games: List<Game> ->
    games.fold(0) { acc, game ->
        acc + (game.maxColor("red") * game.maxColor("green") * game.maxColor("blue"))
    }
}


fun main() {
    val testInput = readInput("Day02_test")
    val gamesTest = testInput.map { game -> makeGame(game) }
    check(part1(gamesTest) == 8)
    check(part2(gamesTest) == 2286)

    val input = readInput("Day02")
    val games = input.map { game -> makeGame(game) }
    part1(games).println()
    part2(games).println()
}