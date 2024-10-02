val obtainSeeds = { input: List<String> -> input[0].split(":")[1].trim().split(" ") }

private data class Mapper(val name: String, val ranges: List<Range>)
private data class Range(val ds: Long, val de: Long, val ss: Long, val se: Long, val range: Long, val diff: Long)

private fun obtainMappers(input: List<String>): List<Mapper> {
    var ranges: MutableList<Range> = mutableListOf()
    var cat: String = input[2]
    val mappers: MutableList<Mapper> = mutableListOf()
    for (i in 2 + 1..<input.size) {
        val line = input[i]
        if (line.contains("seeds") || line.isEmpty()) continue
        if (line.contains("-to-")) {
            mappers.add(Mapper(cat, ranges))
            cat = line
            ranges = mutableListOf()
        } else {
            val (ds, ss, r) = line.split(" ").map { it.toLong() }
            val de = ds + r - 1
            val se = ss + r - 1
            ranges.add(Range(ds, de, ss, se, r, ds - ss))
        }
    }
    mappers.add(Mapper(cat, ranges))
    return mappers
}

private fun applyMap(seed: Long, mapper: Mapper): Long {
    for (range in mapper.ranges) if (seed >= range.ss && seed <= range.se) return seed + range.diff
    return seed
}

private fun findFinalLocation(seed: Long, mappers: List<Mapper>): Long {
    var newSeedValue = seed
    for (map in mappers) newSeedValue = applyMap(newSeedValue, map)
    return newSeedValue
}

private fun part1(input: List<String>): Long {
    val seeds = obtainSeeds(input).map { it.toLong() }
    val mappers = obtainMappers(input)
    var min = 99999999999
    for (seed in seeds) {
        val r = findFinalLocation(seed, mappers)
        if (r < min) min = r
    }
    return min
}

private fun obtainAllSeeds(input: List<String>): List<Sequence<Long>> {
    val line = input[0].split(":")[1].trim().split(" ").map { it.toLong() }
    val seeds: MutableList<Sequence<Long>> = mutableListOf()

    for (i in line.indices step 2) {
        val startSeed = line[i]
        val items = line[i + 1]
        seeds.add(generateSequence(startSeed) { if (it < startSeed + items-1) it + 1 else null })
    }
    return seeds
}

private fun part2(input: List<String>): Long {
    val seeds = obtainAllSeeds(input)

    val mappers = obtainMappers(input)
    var min = 99999999999
    for (seed in seeds) {
        for(value in seed) {
            val r = findFinalLocation(value, mappers)
            if (r < min) min = r
        }
    }
    return min
}

fun main() {
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    check(part1(input) == 836040384L)
    check(part2(input) == 10834440L)
}
