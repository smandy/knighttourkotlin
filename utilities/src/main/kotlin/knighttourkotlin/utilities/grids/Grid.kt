package knighttourkotlin.utilities.grids

import knighttourkotlin.utilities.BOARDSIZE
import knighttourkotlin.utilities.PInt
import knighttourkotlin.utilities.moves
import knighttourkotlin.utilities.plus

/* Grab most of the griddy stuff in the interface */
interface Grid {
    val movesMade : List<PInt>
    val currentPoint : PInt
    val visited : Collection<PInt>
    fun coords : Collection<PInt> {
        return movesMade.runningFold( PInt() ) { a,b -> a + b }
    }

    fun isOnBoard(p: PInt) =
        p.first in 0 until BOARDSIZE &&
                p.second in 0 until BOARDSIZE

    operator fun contains(pInt: Pair<Int, Int>): Boolean {
        require(isOnBoard(pInt)) { "Logic error $pInt not on board" }
        return pInt in visited
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

    operator fun plus(it: Pair<Int, Int>) : Grid

    fun canMove(p: PInt): Boolean {
        return isOnBoard(p) && !contains(p)
    }

    fun pop() : Grid
}