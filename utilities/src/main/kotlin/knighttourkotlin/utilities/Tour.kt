package knighttourkotlin.utilities

class Tour {
    var g = Grid()
    //var p = PInt(0, 0)

    var stack = mutableListOf<MutableList<PInt>>()
    var running = true
    fun run(eager : Boolean, cb : (Grid) -> Boolean = { true }) {

/*
        fun availableMoves() = moves.filterTo(mutableListOf()) {
            g.canMove(g.currentPoint + it)
        }
*/


        stack += g.availableMoves()
        while (running) {
            //println("$g vp = $p")
            //println("MovesToMake = $movesToMake")
            //println("Stack : $stack")
            //println("G : $g")
            if (stack.last().isEmpty()) {
                if (g.movesMade.size==BOARDSIZE * BOARDSIZE) {
                    running = false
                } else {
                    g = g.pop()
                    stack.removeLast()
                }
            } else {
                val moveToMake = stack.last().removeLast()
                g += moveToMake
                //require( g.runningCoords().all { it in g.visited } ) { "Logic error inconsistent"}
                stack += g.availableMoves()
            }

            if (eager) {
                running = cb(g)
            }

            if (g.movesMade.size==0 || g.movesMade.size==64) {
                print("Boom $g")
                running = false
            }
        }
    }


}