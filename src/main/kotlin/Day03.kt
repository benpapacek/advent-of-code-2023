object Day03 {

    data class Item(val row: Int, val cols: IntRange, val value: String)

    private fun getMatches(rows: List<String>) = rows.mapIndexed { i, row ->
        "\\d+".toRegex().findAll(row).toList().map { mr -> Item(i, mr.range, mr.value) }
    }.flatten()

    fun part1() {
        val input = FileReader().readFile("input03.txt")
        val rows = input.split("\n")

        fun Char.isSymbol() = !isDigit() && this != '.'
        fun symbolAt(i: Int, j: Int): Boolean {
            return i >= 0 && i < rows.size && j >= 0 && j < rows[0].length && rows[i][j].isSymbol()
        }

        val matches = getMatches(rows)

        val filteredMatches = matches.filter { m ->
            val i = m.row
            m.cols.any { j ->
                symbolAt(i - 1, j - 1) ||
                symbolAt(i - 1, j) ||
                symbolAt(i - 1, j + 1) ||
                symbolAt(i, j - 1) ||
                symbolAt(i, j + 1) ||
                symbolAt(i + 1, j - 1) ||
                symbolAt(i + 1, j) ||
                symbolAt(i + 1, j + 1)
            }
        }

        val result = filteredMatches.sumOf { it.value.toInt() }
        println("Day 3 part 1: $result")
    }


    fun part2() {
        val input = FileReader().readFile("input03.txt")
        val rows = input.split("\n")
        val matches = getMatches(rows)

        var result = 0
        rows.forEachIndexed { i, row ->
            row.forEachIndexed { j, c ->
                if (c == '*') {
                    val neighbours = matches.filter { m ->
                        m.row >= i - 1 && m.row <= i + 1 &&
                                j in ((m.cols.first - 1)..(m.cols.last + 1))
                    }
                    if (neighbours.size > 2) {
                        throw IllegalStateException()
                    } else if (neighbours.size > 1) {
                        result += neighbours[0].value.toInt() * neighbours[1].value.toInt()
                    }
                }
            }
        }
        println("Day 3 part 2: $result")
    }

}