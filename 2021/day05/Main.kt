data class Triple<T>(
    val start: T,
    val end: T,
    val vector: T
)

fun direction(x: Int) = when {
    x < 0  -> -1
    x == 0 -> 0
    else   -> 1
}

fun abs(x: Int) = when {
    x < 0 -> x * -1
    else -> x
}

fun main() {
    var maxRow: Int = 0
    var maxCol: Int = 0

    val input = generateSequence(::readlnOrNull)
        .map {
            val (p1,p2) = it.split(" -> ").map{
                val (col,row) = it.split(",").map{ it.toInt() }
                maxRow = maxOf(maxRow, row)
                maxCol = maxOf(maxCol, col)
                Pair(col,row)
            }

            val (p1Col,p1Row) = p1
            val (p2Col,p2Row) = p2

            val diffCol = p2Col - p1Col
            val diffRow = p2Row - p1Row

            if (diffCol == 0 || diffRow == 0 || abs(diffCol) == abs(diffRow)) {
                Triple(p1, p2, Pair(direction(diffCol),direction(diffRow)))
            } else {
                null
            }
        }
        .filterNotNull()
        .toList()


    val mat = Array(maxRow + 1,{IntArray(maxCol + 1,{0})})

    val (linear,diagonal) = input.partition{ it.vector.first == 0 || it.vector.second == 0 }

    fun count(list: List<Triple<Pair<Int,Int>>>): Int {
        var overlap = 0
        list.forEach{
            val (start,end,dir) = it
            var (c,r) = start
            val cEnd = end.first + dir.first
            val rEnd = end.second + dir.second

            while(c != cEnd || r != rEnd) {
                val v = mat[r][c]

                if (v == 1) {
                    mat[r][c] = v + 1
                    overlap += 1
                } else if(v < 1) {
                    mat[r][c] = v + 1
                }
                c += dir.first
                r += dir.second
            }
        }
        return overlap
    }

    val part1Answer = count(linear)

    val overlapDiagonal = count(diagonal)
    val part2Answer = part1Answer + overlapDiagonal

    println(part1Answer)
    println(part2Answer)
}
