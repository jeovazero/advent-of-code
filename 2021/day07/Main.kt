fun sumAt(n: Int): Int = (n * (n + 1)) / 2

fun main() {
    val input = readln().split(",").map{ it.toInt() }

    val totalItems = input.size
    var maxPosition = 0
    var currentSum = 0

    input.forEach {
        currentSum += sumAt(it)
        maxPosition = maxOf(maxPosition, it)
    }

    val quantityCounter = IntArray(maxPosition + 1)

    input.forEach { quantityCounter[it]++ }

    var minSum = currentSum
    var smallers = 0
    var sumSmallers = 0
    var subSum = input.sum()

    for(position in 1..maxPosition) {
        val predIndex = position - 1
        val predCounter = quantityCounter[predIndex]

        smallers += predCounter 
        sumSmallers += predIndex * predCounter

        val biggers = totalItems - smallers
        currentSum = currentSum - subSum + (smallers * position - sumSmallers)
        subSum -= biggers

        minSum = minOf(currentSum, minSum)
    }

    println(minSum) // 342730
            
}
