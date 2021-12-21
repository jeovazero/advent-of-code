data class Num(
  val board: Int,
  val row: Int,
  val col: Int
)

data class BoardData(
    var sum: Int = 0,
    val count: IntArray = IntArray(10,{0})
)

val numsMap = HashMap<Int,MutableList<Num>>()

fun main() {
    val numsInput = readln().split(",").map{ it.toInt() }
    val boardsInput =
        generateSequence(::readlnOrNull)
            .windowed(size=6, step=6)
            .map{it.drop(1).map{it.trim().split(Regex("\\s+")).map{it.toInt()}}}
            .toList()

    val boardsQtt = boardsInput.size
    val boards = List(boardsQtt,{BoardData()})

    boardsInput.forEachIndexed { boardIndex,board ->
        var sumBoard = 0
        board.forEachIndexed { rowIndex,row ->
            row.forEachIndexed { colIndex,value ->
                val curNum = Num(boardIndex,rowIndex,colIndex)
                val nums = numsMap.get(value)
                if (nums == null) {
                    numsMap.put(value,mutableListOf(curNum))
                } else {
                    nums.add(curNum)
                }
                sumBoard += value
            }
        }
        boards.get(boardIndex).sum = sumBoard
    }

    val nonWinners = HashSet(List(boardsQtt,{it}))

    var part1Answer: Int? = null
    var part2Answer: Int? = null

    for(value in numsInput) {
        val nums = numsMap.get(value)

        if (nums != null) {
            for (num in nums) {
                val board = boards.get(num.board)
                board.sum -= value
                val counterRow = board.count[num.row]
                val counterCol = board.count[num.col + 5]

                if (counterRow == 4 || counterCol == 4) {
                    // first winner
                    if (part1Answer == null) {
                        part1Answer = value * board.sum
                    }
                    nonWinners.remove(num.board)

                    // last winner
                    if (nonWinners.isEmpty()) {
                        part2Answer = value * board.sum
                        break
                    }
                }

                board.count[num.row] = counterRow + 1
                board.count[num.col + 5] = counterCol + 1
            }
        }

        if (part2Answer != null) {
            break
        }
    }

    println(part1Answer)
    println(part2Answer)
}
