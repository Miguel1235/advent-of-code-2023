plugins {
    kotlin("jvm") version "1.9.22"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
    task("nextDay") {
        doLast {
            val prevDay = fileTree("$projectDir/src")
                .matching { include("Day*.kt") }
                .maxOf {
                    val (prevDay) = Regex("Day(\\d\\d)").find(it.name)!!.destructured
                    prevDay.toInt()
                }
            val currDay = String.format("%02d", prevDay + 1)
            File("$projectDir/src", "Day$currDay.kt").writeText(
                """
private fun part1(input: List<String>): Int {
    return 0
}
private fun part2(input: List<String>): Int {
    return 0
}

fun main() {
    val testInput = readInput("Day${currDay}_test")
    check(part1(testInput) == 0)
    
    val input = readInput("Day$currDay")
    part1(input).println()
    part2(input).println()
}
"""
            )
        }
    }
}
