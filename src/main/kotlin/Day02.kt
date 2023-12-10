object Day02 {

    data class Game(
        val id: Int,
        val rounds: List<Map<String, Int>>
    )

    fun part1() {
        val input = FileReader().readFile("input02.txt")
        val games = parseGames(input)
        val result = games.filter { game ->
            game.rounds.all { round ->
                round.getOrElse("red") { 0 } <= 12 &&
                        round.getOrElse("green") { 0 } <= 13 &&
                        round.getOrElse("blue") { 0 } <= 14
            }
        }.sumOf { it.id }

        println("Day 2 part 1: $result")
    }

    fun part2() {
        val input = FileReader().readFile("input02.txt")
        val games = parseGames(input)
        val result = games.sumOf { game ->
            val maxRed = game.rounds.maxOf { it.getOrElse("red") { 0 } }
            val maxGreen = game.rounds.maxOf { it.getOrElse("green") { 0 } }
            val maxBlue = game.rounds.maxOf { it.getOrElse("blue") { 0 } }
            maxRed * maxGreen * maxBlue
        }

        println("Day 2 part 2: $result")
    }

    private fun parseGames(input: String): List<Game> = input.split("\n").map { line ->
        val a = line.split(":")
        val id = a[0].trim().replace("Game ", "").toInt()
        val games = a[1].split(";").map { desc ->
            desc.split(",").associate { item ->
                item.trim().split("\\s+".toRegex()).let { b -> b[1] to b[0].toInt() }
            }
        }
        Game(id, games)
    }

}