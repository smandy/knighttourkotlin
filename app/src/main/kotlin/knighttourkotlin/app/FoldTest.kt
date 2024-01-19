package knighttourkotlin.app

fun main() {
    println(listOf(1,2,3,4,5).runningFold(0) { a,b -> a + b})

    val xs = listOf(1,2,3,4,5)

    println(xs.subList(0, xs.lastIndex))

}