package knighttourkotlin.utilities.grids

import knighttourkotlin.utilities.PInt
import knighttourkotlin.utilities.minus
import knighttourkotlin.utilities.plus

/* Try to optimize - calculate currentPoint and visited */
data class Grid3 private constructor (
    override var movesMade: List<PInt>,
    override val visited : HashSet<PInt>,
    override val currentPoint : PInt
) : Grid {
    companion object {
        operator fun invoke() = Grid3(
            emptyList(),
            hashSetOf(PInt()),
            PInt()
        )
    }

    override operator fun plus(move: Pair<Int, Int>): Grid3 {
        val newP = currentPoint + move
        require( !visited.contains(newP)) { "Logic error $newP already on board" }
        return Grid3(
            movesMade + move,
            visited.toHashSet().apply{ add(newP) },
            newP
        )
    }

    override fun pop(): Grid3 {
        require(movesMade.isNotEmpty()) { "Logic error pop from empty list" }
        val toPop = movesMade.last()
        val oldPoint = currentPoint - toPop
        require( oldPoint in visited) {"Log error popping point $oldPoint"}
        require( currentPoint in visited) { "Log error $currentPoint not in $visited"}
        return Grid3(
            movesMade.subList(0, movesMade.lastIndex),
            visited.toHashSet().apply { remove(currentPoint) },
            oldPoint
        )
    }
}