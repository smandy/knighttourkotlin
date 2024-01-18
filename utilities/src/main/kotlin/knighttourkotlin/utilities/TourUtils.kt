package knighttourkotlin.utilities

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.math.absoluteValue

val BOARDSIZE = 8
typealias PInt = Pair<Int, Int>

val moves = run {
    val opts = listOf(-1, -2, 1, 2)
    opts.flatMap { a ->
        opts.map { b ->
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
    val xs: BooleanArray,
    val ys: BooleanArray, val p: PInt
) {
    companion object {
        operator fun invoke() = Grid(
            emptyList(),
            BooleanArray(BOARDSIZE) { false },
            BooleanArray(BOARDSIZE) { false },
            PInt(0, 0)
        )
    }

    override fun toString() = "Grid($movesMade)"

    fun isOnBoard(p: PInt) =
        p.first in 0 until BOARDSIZE &&
                p.second in 0 until BOARDSIZE

    operator fun contains(pInt: Pair<Int, Int>): Boolean {
        require(isOnBoard(pInt)) { "Logic error $pInt not on board" }
        return xs[pInt.first] && ys[pInt.second]
    }

    operator fun plus(move: Pair<Int, Int>): Grid {
        //require( !contains(pInt)) { "Logic error $pInt already on board" }
        val newP = p + move
        return Grid(
            movesMade + move,
            xs.clone().apply { this[newP.first] = true },
            ys.clone().apply { this[newP.second] = true },
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
            xs.clone().apply { this[movesMade.last().first] = false },
            ys.clone().apply { this[movesMade.last().second] = false },
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

    val frame = JFrame("Tour")
    frame.setSize(320, 320)

    val panel = object : JPanel() {
        fun doImmediateRepaint() {
        }

        override fun paintComponent(graphics: Graphics?) {
            super.paintComponent(graphics)
            if (graphics is Graphics2D) {
                val squareSize = 40
                graphics.paint = Color.BLACK
                graphics.fillRect(0, 0, squareSize * BOARDSIZE,
                    squareSize * BOARDSIZE)
                graphics.paint = Color.WHITE

                for (x in 0 until BOARDSIZE) {
                    for (y in 0 until BOARDSIZE) {
                        //println("x + y ${ (x + y) % 2 }")
                        if ( ((x + y) % 2) == 0) {
                            val x0 = x * squareSize
                            val y0 = y * squareSize
                            graphics.fillRect(
                                x0,
                                y0,
                                squareSize,
                                squareSize
                            )
                        }
                    }
                }

                graphics.paint = Color.RED

                val coords = g.movesMade
                    .runningFold(PInt(0, 0)) { a, b -> a + b }
                    .toList()
                        .map {
                    println("Value is $it")
                    PInt(
                        squareSize / 2 + it.first * squareSize,
                        squareSize / 2 + it.second * squareSize
                    )
                }
                println("Moves made ${g.movesMade}")
                println("Coords made $coords")

                graphics.paint = Color.RED
                graphics.stroke = BasicStroke(10.0f)
                if (true) {
                    coords.zip(coords.drop(1)).map { (a, b) ->
                        graphics.drawLine(a.first, a.second, b.first, b.second)
                    }
                }

            }
        }
    }

    frame.add(panel)
    frame.isVisible = true
}

