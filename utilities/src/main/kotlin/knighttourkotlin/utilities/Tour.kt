package knighttourkotlin.utilities

class Tour {
    var g = Grid()
    var p = PInt(0, 0)

    var stack = mutableListOf<MutableList<PInt>>()
    var running = true
    fun run(cb : (Grid) -> Boolean = { true }) {
        while (running) {
            //println("$g vp = $p")
            val movesToMake = moves.filterTo(mutableListOf()) {
                g.canMove(p + it)
            }
            //println("MovesToMake = $movesToMake")
            if (movesToMake.isEmpty()) {
                running = false
            } else {
                val moveToMake = movesToMake.removeLast()
                g += moveToMake
                stack += movesToMake
                p += moveToMake
                running = cb(g)
            }
        }
    }


}