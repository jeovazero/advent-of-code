val dir = listOf(
  Pair(-1,-1),Pair(-1,0),Pair(-1,1),
  Pair(0,-1),           Pair(0,1),
  Pair(1,-1),Pair(1,0),Pair(1,1)
)

fun main() {
  val mut = generateSequence(::readlnOrNull).map{ it.toMutableList() }.toMutableList()

  val rowLen = mut.size
  val rowLim = rowLen - 1
  val colLen = mut[0].size
  val colLim = colLen - 1
  val totalGrid = rowLen * colLen

  tailrec fun lookAround(flashedSet: HashSet<Int>, acc: Int): Int {
    if(flashedSet.isEmpty()) return acc
    val nextFlashedSet =  HashSet<Int>()
    var partial = 0
    flashedSet.forEach { code -> 
      val r = code / colLen
      val c = code % colLen
      for((ri,ci) in dir) {
        val rn = r + ri
        val cn = c + ci
        if (rn >= 0 && rn <= rowLim && cn >= 0 && cn <= colLim) {
          val value = mut[rn][cn]
          val nextValue = when(value) {
            '0' -> '0'
            '9' -> {
              nextFlashedSet.add(rn * colLen + cn)
              partial += 1
              '0'
            }
            else -> value.inc()
          }
          mut[rn][cn] = nextValue
        }
      }
    }
    return lookAround(nextFlashedSet, acc + partial)
  }

  val flashedSet = HashSet<Int>()
  var totalFlashed = 0
  var lastFlashed = 0
  var step = 0
  var part1Answer = 0
  while(lastFlashed != totalGrid) {
    lastFlashed = 0
    for((r,row) in mut.withIndex()) {
      for((c,value) in row.withIndex()) {
        val nextValue = when(value) {
          '9' -> {
            flashedSet.add(r * colLen + c)
            lastFlashed += 1
            '0'
          }
          else -> value.inc()
        } 
        mut[r][c] = nextValue
      }
    }
    lastFlashed += lookAround(flashedSet, 0)
    totalFlashed += lastFlashed
    flashedSet.clear()
    step += 1
    if (step == 100) {
      part1Answer = totalFlashed
    }
  }
  println(part1Answer)
  println(step)
}
