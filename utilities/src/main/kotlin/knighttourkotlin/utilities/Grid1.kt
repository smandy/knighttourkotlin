package knighttourkotlin.utilities

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