val segmentsUniqueSize = listOf(2,3,4,7)

/*
 seg | num
  2 -> 1
  3 -> 7
  4 -> 4
  5 -> 2, 3, 5
  6 -> 0, 6, 9
  7 -> 8
*/

fun part1(input: List<String>) {
  val part1Answer = input
    .map{
       it.split(" | ")
         .component2()
         .split(" ")
         .map{ it.length }
         .filter{ it in segmentsUniqueSize }
         .size
    }.sum()
  
    println(part1Answer)
}

fun Int.bits1(): Int {
  var count = 0
  for(i in 0..7) {
    if (this and (1 shl i) != 0) count++
  }
  return count
}

fun Int.qttBits1(arg: Int) = (this and arg).bits1()

fun CharSequence.segmentToInt(): Int {
  var value = 0
  for(char in this) {
    val i = char.code - 'a'.code
    value = value or (1 shl i)
  }
  return value
}


val maskMapping = IntArray(128)

fun solveCasePart2(input: String): Int {
  val (leftSegs, rightSegs) = input
    .split(" | ")
    .map{
      it.split(" ")
        .map{ Pair(it.segmentToInt(), it.length) }
    }

  val (knownSegs,unknownSegs) = leftSegs.partition{ it.second in segmentsUniqueSize }
  
  var seg4Mask = 0
  var seg7Mask = 0

  knownSegs.forEach{
    val (mask,length) = it
    val value = when(length) {
      2 -> 1
      3 -> {
        seg7Mask = mask
        7
      }
      4 -> {
        seg4Mask = mask
        4
      }
      else -> 8
    }

    maskMapping[mask] = value
  }

  unknownSegs.forEach{
    val (mask,length) = it
    val value =
      when(length){
        5 ->
          when {
            mask.qttBits1(seg7Mask) == 3 -> 3
            else -> {
              when {
                mask.qttBits1(seg4Mask) == 3 -> 5
                else -> 2
              }
            }
          }

        // 6
        else ->
          when {
            mask.qttBits1(seg4Mask) == 4 -> 9
            else -> {
              when {
                mask.qttBits1(seg7Mask) == 3 -> 0
                else -> 6
              }
            }
          }

      } 
    maskMapping[mask] = value
  }

  return rightSegs.fold(0){acc,cur-> acc * 10 + maskMapping[cur.first]}
}

fun part2(input: List<String>) {
  println(input.map{ solveCasePart2(it) }.sum())
}

fun main() {
  val input = generateSequence(::readlnOrNull).toList()

  part1(input)
  part2(input)
}
