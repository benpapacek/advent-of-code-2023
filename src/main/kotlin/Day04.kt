import kotlin.math.pow

object Day04 {

    data class Card(
        val id: Int,
        val targets: Set<Int>,
        val candidates: Set<Int>,
    ) {
        val score = candidates.intersect(targets).size
    }

    private fun parseCards(input: String) = input.split("\n").mapIndexed { i, line ->
        val parts = line.split(":")[1].split("|")
        val targets = parts[0].split("\\s+".toRegex()).filter { it.isNotBlank() }.map { it.toInt() }.toSet()
        val candidates = parts[1].split("\\s+".toRegex()).filter { it.isNotBlank() }.map { it.toInt() }.toSet()
        Card((i + 1), targets, candidates)
    }

    fun part1() {
        val input = FileReader().readFile("input04.txt")
        val cards = parseCards(input)
        val result = cards.sumOf {
            val count = it.targets.intersect(it.candidates).size
            if (count == 0) 0 else 2.toDouble().pow(count - 1).toInt()
        }
        println("Day 4 part 1: $result")
    }

    fun part2() {
        val input = FileReader().readFile("input04.txt")
        val cards = parseCards(input)

        val n = mutableListOf<Card>()
        cards.indices.forEach { i ->
            recurse(cards, i, n)
        }
        val result = n.size
        println("Day 4 part 2: $result")
    }

    private fun recurse(list: List<Card>, index: Int, items: MutableList<Card>) {
        val card = list[index]
        items.add(card)
        (1..card.score).forEach { i ->
            recurse(list, index + i, items)
        }
    }
}