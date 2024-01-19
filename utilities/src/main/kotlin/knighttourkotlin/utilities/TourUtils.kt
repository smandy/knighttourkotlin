package knighttourkotlin.utilities

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.util.concurrent.atomic.AtomicBoolean
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.jvm.internal.Ref.BooleanRef
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

class Grid private constructor(
    var movesMade: List<PInt>,
    val visited: Set<PInt>,
    val p: PInt
) {
    companion object {
        operator fun invoke() = Grid(
            emptyList(),
            emptySet(),
            PInt(0, 0)
        )
    }

    val drawAscii = AtomicBoolean(false)

    override fun toString() : String {
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
        val newP = p + move
        return Grid(
            movesMade + move,
            visited + newP,
            newP
        )
    }

    fun canMove(p: PInt): Boolean {
        return isOnBoard(p) && !contains(p)
    }

    fun pop(): Grid {
        //assert( !contains(pInt)) { "Logic error $pInt already on board" }
        require(movesMade.isNotEmpty()) { "Logic error pop from empty list" }

        val toPop = movesMade.last().also {
            require(isOnBoard(p + it)) { "Logic error invalid pop to ${p + it}" }
        }
        //val newP = p - t
        return Grid(
            movesMade.subList(0, movesMade.lastIndex),
            visited - toPop,
            p - toPop
        )
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

