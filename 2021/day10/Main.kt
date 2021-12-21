val openSymbols = "([{<"

val closeMapping = mapOf(
  '(' to ')',
  '[' to ']',
  '{' to '}',
  '<' to '>'
)

fun part1(input: List<String>) {
    val pointsMapping = mapOf(
      ')' to 3,
      ']' to 57,
      '}' to 1197,
      '>' to 25137
    )

    fun String.getPoints(): Int {
      val stack = mutableListOf<Char>()

      fun last() = if (stack.isNotEmpty()) stack[stack.lastIndex] else null
      for (c in this) {
        if (openSymbols.contains(c)) {
          stack.add(c)
        } else {
          val lastElem = last()
          if (lastElem != null) {
            if (closeMapping.get(lastElem) != c) {
              return pointsMapping.getOrDefault(c, 0)
            } else {
              stack.removeLast()
            }
          }
        } 
      }

      return 0
    }

   println(input.map{ it.getPoints() }.sum())
}

fun part2(input: List<String>) {
  val pointsMapping = mapOf(
    '(' to 1L,
    '[' to 2L,
    '{' to 3L,
    '<' to 4L
  )

  fun String.getPoints(): Long {
    val stack = mutableListOf<Char>()

    fun last() = if (stack.isNotEmpty()) stack[stack.lastIndex] else null
    for (c in this) {
      if (openSymbols.contains(c)) {
        stack.add(c)
      } else {
        val lastElem = last()
        if (lastElem != null) {
          if (closeMapping.get(lastElem) != c) {
            return 0
          } else {
            stack.removeLast()
          }
        }
      } 
    }

    return stack.foldRight(0){ cur, acc -> acc * 5 + pointsMapping.getOrDefault(cur,0) }
  }

  val list = input.map{ it.getPoints() }.filter{ it != 0L }.sorted()
  val mid = list.size / 2
  println(list[mid])
}

fun main() {
  val input = generateSequence(::readlnOrNull).toList()

  part1(input)
  part2(input)
}
