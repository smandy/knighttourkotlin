package knighttourkotlin.utilities

import kotlin.math.absoluteValue

val BOARDSIZE = 8
typealias PInt = Pair<Int, Int>

val moves = run {
    val opts = listOf(-1, -2, 1, 2)
    opts.asSequence().map { a ->
        opts.asSequence().flatMap { b ->
            if (a.absoluteValue != b.absoluteValue) {
                listOf(Pair(a, b))
            } else {
                emptyList()
            }
        }
    }.flatten()
}

fun PInt(): Pair<Int, Int> {
    return PInt(0,0)
}

operator fun PInt.plus(rhs: PInt) =
    Pair(this.first + rhs.first, this.second + rhs.second)

operator fun PInt.minus(rhs: PInt) =
    Pair(this.first - rhs.first, this.second - rhs.second)


