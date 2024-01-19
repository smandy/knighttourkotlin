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

/* Grab most of the griddy stuff in the interface */
interface Grid {
    val movesMade : List<PInt>
    val currentPoint : PInt
    val visited : Collection<PInt>

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

    //abstract fun availablMoves(b: Boolean): Any

    operator fun plus(it: Pair<Int, Int>) : Grid

    //operator fun minus(it: Pair<Int, Int>)

    fun canMove(p: PInt): Boolean {
        return isOnBoard(p) && !contains(p)
    }

    fun pop() : Grid
}

data class Grid1 private constructor (
    override var movesMade: List<PInt>
) : Grid {
    companion object {
        operator fun invoke() = Grid1(
            emptyList()
        )
    }

    override val currentPoint : PInt
        get() = visited.last()

    override val visited : Collection<PInt>
        get() = movesMade.runningFold(PInt(0, 0)) { a, b -> a + b }

    override operator fun plus(move: Pair<Int, Int>): Grid1 {
        //require( !contains(pInt)) { "Logic error $pInt already on board" }
        //val newP = currentPoint + move
        return Grid1(
            movesMade + move
        )
    }

    override fun pop(): Grid1 {
        require(movesMade.isNotEmpty()) { "Logic error pop from empty list" }
        return Grid1(
            movesMade.subList(0, movesMade.lastIndex),
        )
    }
}

operator fun PInt.plus(rhs: PInt) =
    Pair(this.first + rhs.first, this.second + rhs.second)

operator fun PInt.minus(rhs: PInt) =
    Pair(this.first - rhs.first, this.second - rhs.second)


