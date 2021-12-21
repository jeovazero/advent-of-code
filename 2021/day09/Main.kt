fun part1(input: List<String>) {
  val colLen = input[0].length
  val cmpVertical = BooleanArray(colLen,{true})

  val endRow = input.size - 1
  val endCol = colLen - 1

  var totalSum = 0
  for(row in 0..endRow) {
    val curRow = input[row]
    val nextRow = if (row >= endRow) null else input[row + 1]
    var cmpLeft = true
    for(col in 0..endCol) {
      var check = 0
      val value = curRow[col]
      val nextValue = if (col >= endCol) null else curRow[col + 1]
      val bottomValue = nextRow?.get(col)

      // top      
      if (cmpVertical[col]) check++

      // left
      if (cmpLeft) check++

      // right
      if (nextValue != null) {
        if (nextValue > value) {
          check++
          cmpLeft = false
        } else if (value > nextValue) {
          cmpLeft = true
        }
      } else {
        check++
      }

      // bottom
      if (bottomValue != null) {
        if (bottomValue > value) {
          check++
          cmpVertical[col] = false
        } else if(value > bottomValue) {
          cmpVertical[col] = true
        } else {
          cmpVertical[col] = false
        }
      } else {
        check++
      }


      if (check == 4) {
        totalSum = totalSum + (value.code - '0'.code) + 1
      }
    }
  }
  println(totalSum)
}

// https://gist.github.com/jeovazero/5eec9b3a498ae5c7e2f5f3c4e0e0bbc3
class UnionFind(size: Int) {
    val roots = IntArray(size)
    val sizes = IntArray(size)
    init {
        for(i in 0..(size - 1)) {
            roots[i] = i
            sizes[i] = 1
        }
    }
    fun find(node: Int): Int {
        var i = node
        while(roots[i] != i) {
            roots[i] = roots[roots[i]]
            i = roots[i]
        }
        return i
    }
    fun union(a: Int, b: Int) {
        val fa = find(a)
        val fb = find(b)

        if (fa == fb) return

        if (sizes[fa] < sizes[fb]) {
            roots[fa] = fb
            sizes[fb] += sizes[fa]
        } else {
            roots[fb] = fa
            sizes[fa] += sizes[fb]
        }
    }
}

fun part2(input: List<String>) {
  val colLen = input[0].length
  val rowLen = input.size

  fun pos(row: Int, col: Int) = row * colLen + col

  val UF = UnionFind(colLen * rowLen + 1)
  val colLim = colLen - 1
  val rowLim = rowLen - 1
  for(row in 0..rowLim) {
    val curRow = input[row]
    val nextRow = if (row >= rowLim) null else input[row + 1]
    for(col in 0..colLim) {
      val value = curRow[col]
      val nextValue = if (col >= colLim) null else curRow[col + 1]
      val bottomValue = nextRow?.get(col)
      
      if (value != '9') {
        if (nextValue != null && nextValue != '9' && value - nextValue != 0) {
          UF.union(pos(row,col),pos(row,col + 1))
        }

        if (bottomValue != null && bottomValue != '9' && value - bottomValue != 0) {
          UF.union(pos(row,col),pos(row + 1,col))
        }
      }
    }
  }

  UF.sizes.sortDescending()

  val max3 = UF.sizes.slice(0..2).reduce{acc,cur -> acc * cur}

  println(max3)
}

fun main() {
  val input = generateSequence(::readlnOrNull).toList()
  part1(input)
  part2(input)
}
