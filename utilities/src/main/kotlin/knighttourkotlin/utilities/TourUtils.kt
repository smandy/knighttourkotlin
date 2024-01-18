package knighttourkotlin.utilities

import kotlin.math.absoluteValue

val BOARDSIZE = 8
typealias PInt = Pair<Int,Int>

class Grid {
    val movesMade = emptyList<PInt>()
    val xs = BooleanArray( BOARDSIZE) { false }
    val ys = BooleanArray( BOARDSIZE) { false }
}

val moves = run {
    val opts = listOf(-1, -2, 1, -2)
    opts.flatMap { a ->
        opts.map { b ->
            if ( a.absoluteValue != b.absoluteValue) {
                listOf(Pair(a,b))
            } else {
                emptyList()
            }
        }
    }.flatten()
}

operator fun PInt.plus( rhs : PInt ) =
    Pair( this.first + rhs.first, this.second + rhs.second)

fun main() {
    println(moves)
    println( PInt(1,2) + PInt(3,4))
}

fun PInt.isOnBoard() =
    first in 0 until BOARDSIZE &&
            second in 0 until BOARDSIZE
