fun main() {
    val input = readln().split(",").map{ it.toInt() }

    val totalItems = input.size
    var totalSum = 0L
    var maxPosition = 0

    input.forEach {
        totalSum += it
        maxPosition = maxOf(maxPosition, it)
    }

    val quantityCounter = IntArray(maxPosition + 1)

    input.forEach { quantityCounter[it]++ }

    var minSum: Long = totalSum
    var smallers = 0
    var sumSmallers = 0

    for(position in 1..maxPosition) {
        val predIndex = position - 1
        val predCounter = quantityCounter[predIndex]

        smallers += predCounter 
        sumSmallers += predIndex * predCounter

        val biggers = totalItems - smallers
        val currentSum = totalSum - (biggers * position) + (smallers * position - 2 * sumSmallers)

        minSum = minOf(currentSum, minSum)
    }

    println(minSum)
    // 417136
}
