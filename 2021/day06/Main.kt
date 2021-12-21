
fun main() {
  val memo = List(2,{MutableList(9,{0L})})

  readln()
    .split(",")
    .forEach{
      val timer = it.toInt()
      memo[0][timer] += 1L
    }

  var root = 0
  var nextRoot = 0
  var part1Answer = 0L

  for(day in 1..256) {
    nextRoot = root xor 1

    memo[root].forEachIndexed { i,qtimer ->
      when(i) {
        0 -> {
          memo[nextRoot][8] = qtimer
          memo[nextRoot][6] = qtimer
        }
        7 -> {
          memo[nextRoot][6] += qtimer
        }
        else -> {
          memo[nextRoot][i - 1] = qtimer
        }
      }
    }

    root = nextRoot
    if (day == 80) {
        part1Answer = memo[root].sum()
    }
  }

  val part2Answer = memo[root].sum()

  println(part1Answer)
  println(part2Answer)
}
