object Day01 {


    fun part1() {
        val input = FileReader().readFile("input01.txt")
        val result = input.split("\n").sumOf { line ->
            val a = line.find { it.isDigit() }
            val b = line.findLast { it.isDigit() }
            "$a$b".toInt()
        }
        println("Day 1 part 1: $result")
    }

    fun part2() {
        val words = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        val targets = (1..9).map { it.toString() } + words

        val input = FileReader().readFile("input01.txt")
        val result = input.split("\n").sumOf { line ->
            val a = line.findAnyOf(targets)?.let { it.second.toIntOrNull() ?: (words.indexOf(it.second) + 1) }
            val b = line.findLastAnyOf(targets)?.let { it.second.toIntOrNull() ?: (words.indexOf(it.second) + 1) }
            "$a$b".toInt()
        }
        println("Day 1 part 2: $result")
    }


}