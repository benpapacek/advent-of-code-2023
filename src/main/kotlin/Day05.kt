object Day05 {

    data class LookupRange(val value: Long, val key: Long, val range: Long)

    class Lookup(private val ranges: List<LookupRange>) {
        fun get(key: Long): Long = ranges.firstOrNull {
            key >= it.key && key <= it.key + it.range
        }?.let { it.value + (key - it.key) } ?: key
    }

    class Almanac(input: String) {
        private val seedToSoil = parseLookup(input, "seed-to-soil")
        private val soilToFertiliser = parseLookup(input, "soil-to-fertilizer")
        private val fertiliserToWater = parseLookup(input, "fertilizer-to-water")
        private val waterToLight = parseLookup(input, "water-to-light")
        private val lightToTemperature = parseLookup(input, "light-to-temperature")
        private val temperatureToHumidity = parseLookup(input, "temperature-to-humidity")
        private val humidityToLocation = parseLookup(input, "humidity-to-location")

        fun locationForSeed(seed: Long): Long {
            val soil = seedToSoil.get(seed)
            val fertiliser = soilToFertiliser.get(soil)
            val water = fertiliserToWater.get(fertiliser)
            val light = waterToLight.get(water)
            val temperature = lightToTemperature.get(light)
            val humidity = temperatureToHumidity.get(temperature)
            return humidityToLocation.get(humidity)
        }

        private fun parseLookup(input: String, mapName: String): Lookup {
            val lines = input.split("\n")
            var n = lines.indexOfFirst { it.contains(mapName) }
            val lookupRanges = mutableListOf<LookupRange>()
            while(lines[++n].isNotBlank()) {
                val line = lines[n]
                val (value, key, range) = line.split("\\s+".toRegex()).map { it.toLong() }
                lookupRanges.add(LookupRange(value, key, range))
            }
            return Lookup(lookupRanges)
        }
    }

    fun part1() {
        val input = FileReader().readFile("input05.txt")

        val almanac = Almanac(input)

        val seeds = input.split("\n").first { it.contains("seeds:") }
            .split(":")[1].split("\\s+".toRegex()).filter { it.isNotBlank() }.map { it.toLong() }

        val minLocation = seeds.minOfOrNull { almanac.locationForSeed(it) }
        println("Day 5 part 1: $minLocation")
    }
}
