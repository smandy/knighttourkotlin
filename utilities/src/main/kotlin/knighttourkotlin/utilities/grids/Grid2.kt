package knighttourkotlin.utilities.grids

import knighttourkotlin.utilities.PInt
import knighttourkotlin.utilities.plus

/* Try to optimize - calculate currentPoint and visited */
data class Grid2 private constructor (
    override var movesMade: List<PInt>,
    override val visited : Set<PInt>
) : Grid {
    companion object {
        operator fun invoke() = Grid2(
            emptyList(),
            setOf(PInt())
        )
    }

    override val currentPoint : PInt
        get() = visited.last()

/*
    override val visited : Collection<PInt>
        get() = movesMade.runningFold(PInt(0, 0)) { a, b -> a + b }
*/

    override operator fun plus(move: Pair<Int, Int>): Grid2 {
        val newP = currentPoint + move
        require( !visited.contains(newP)) { "Logic error $newP already on board" }
        return Grid2(
            movesMade + move,
            visited.plus(newP)
        )
    }

    override fun pop(): Grid2 {
        require(movesMade.isNotEmpty()) { "Logic error pop from empty list" }
        require( currentPoint in visited)
        return Grid2(
            movesMade.subList(0, movesMade.lastIndex),
            visited - currentPoint
        )
    }
}