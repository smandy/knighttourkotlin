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

data class Grid private constructor(
    var movesMade: List<PInt>
) {

    companion object {
        operator fun invoke() = Grid(
            emptyList(),
        )
    }

    val currentPoint : PInt
        get() = visited.last()

    val visited : List<PInt>
        get() = movesMade.runningFold(PInt(0, 0)) { a, b -> a + b }

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
            // This is supercool
             ret.map{ this + it }
                 .sortedBy { a -> - a.availableMoves(false).size }
                 .mapTo(mutableListOf()) { it.movesMade.last()}
        }
    }

    fun pop(): Grid {
        require(movesMade.isNotEmpty()) { "Logic error pop from empty list" }
        return Grid(
            movesMade.subList(0, movesMade.lastIndex),
        )
    }
}

operator fun PInt.plus(rhs: PInt) =
    Pair(this.first + rhs.first, this.second + rhs.second)

operator fun PInt.minus(rhs: PInt) =
    Pair(this.first - rhs.first, this.second - rhs.second)


