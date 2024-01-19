package knighttourkotlin.utilities

import java.util.concurrent.atomic.AtomicBoolean
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

data class Grid private constructor(
    var movesMade: List<PInt>
    //val visited: Set<PInt>,
    //val currentPoint: PInt
) {

    companion object {
        val pzero = PInt(0,0)

        operator fun invoke() = Grid(
            emptyList(),
            //setOf(pzero),
            //pzero
        )
    }

    val currentPoint : PInt
        get() = visited.last()

    val visited : List<PInt>
        get() = movesMade.runningFold(PInt(0, 0)) { a, b -> a + b }


val drawAscii = AtomicBoolean(false)

    fun _toString() : String {
        val grid = run {
            if (drawAscii.get()) {
                val sb = StringBuilder()

                for (x in 0 until BOARDSIZE) {
                    for (y in 0 until BOARDSIZE) {
                        if (PInt(x, y) in visited) {
                            sb.append('X')
                        } else {
                            sb.append(' ')
                        }
                    }
                    sb.append('\n')
                }
                sb.toString()
            } else {
                ""
            }
        }
       return  "Grid($movesMade)\n$grid"
    }

    fun isOnBoard(p: PInt) =
        p.first in 0 until BOARDSIZE &&
                p.second in 0 until BOARDSIZE

    operator fun contains(pInt: Pair<Int, Int>): Boolean {
        require(isOnBoard(pInt)) { "Logic error $pInt not on board" }
        return pInt in visited
    }

    operator fun plus(move: Pair<Int, Int>): Grid {
        //require( !contains(pInt)) { "Logic error $pInt already on board" }
        //val newP = currentPoint + move
        return Grid(
            movesMade + move
        )
    }

    fun canMove(p: PInt): Boolean {
        return isOnBoard(p) && !contains(p)
    }
    fun availableMoves(applyWandsDorf : Boolean = true): MutableList<Pair<Int, Int>> {
        val ret = moves.filterTo(mutableListOf()) {
            canMove(currentPoint + it)
        }
        return if (!applyWandsDorf || ret.isEmpty()) {
            ret
        } else {
             ret.map { this + it }
                 .sortedBy { a -> - a.availableMoves(false).size }
                 .mapTo(mutableListOf()) { it.movesMade.last()}
        }
    }

    fun pop(): Grid {
        //assert( !contains(pInt)) { "Logic error $pInt already on board" }

        //println("Popping")
        require(movesMade.isNotEmpty()) { "Logic error pop from empty list" }

/*        val toPop = movesMade.last().also {
            require(isOnBoard(currentPoint - it)) {
                """
                |Logic error invalid pop to ${currentPoint - it}
                |MovesMade $movesMade
                |runningCoorde=${visited}""".trimIndent() }
        }*/
        //val newP = p - t
        //val newPoint = currentPoint - toPop
        //require(newPoint in visited) { "Logic error - popping nonvisited point" }
        return Grid(
            movesMade.subList(0, movesMade.lastIndex),
            //visited - newPoint,
            //newPoint
        )/*.also {
            print(it)
        } */
    }

}

operator fun PInt.plus(rhs: PInt) =
    Pair(this.first + rhs.first, this.second + rhs.second)

operator fun PInt.minus(rhs: PInt) =
    Pair(this.first - rhs.first, this.second - rhs.second)

fun main() {
    println(moves)
    println(PInt(1, 2) + PInt(3, 4))

    var g = Grid()
    var p = PInt(0, 0)

    var running = true
    var stack = mutableListOf<MutableList<PInt>>()

    while (running) {
        println("$g p = $p")
        val movesToMake = moves.filterTo(mutableListOf()) {
            g.canMove(p + it)
        }
        println("MovesToMake = $movesToMake")
        if (movesToMake.isEmpty()) {
            running = false
        } else {
            val moveToMake = movesToMake.removeLast()
            g += moveToMake
            stack += movesToMake
            p += moveToMake
        }
    }

}

