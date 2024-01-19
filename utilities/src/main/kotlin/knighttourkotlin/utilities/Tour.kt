package knighttourkotlin.utilities

class Tour {
    //var g = Grid1()

    var stack = mutableListOf<MutableList<PInt>>()
    var running = true
    fun run(_grid : Grid, eager : Boolean, cb : (Grid) -> Boolean = { true }) {
        var grid = _grid

        var maxMoves = 0


        stack += grid.availableMoves()
        while (running) {
            //println("$g vp = $p")
            //println("MovesToMake = $movesToMake")
            //println("Stack : $stack")
            //println("G : $g")
            if (stack.last().isEmpty()) {
                if (grid.movesMade.size==BOARDSIZE * BOARDSIZE) {
                    running = false
                } else {
                    grid = grid.pop()
                    stack.removeLast()
                }
            } else {
                val moveToMake = stack.last().removeLast()
                grid += moveToMake
                //require( g.runningCoords().all { it in g.visited } ) { "Logic error inconsistent"}
                stack += grid.availableMoves()
            }

            if (eager) {
                running = cb(grid)
            }

            if (grid.movesMade.size==0 || grid.movesMade.size==63) {
                //print("Boom $g")
                //running = false
                running = cb(grid)
            }

            if ( grid.movesMade.size > maxMoves) {
                maxMoves = grid.movesMade.size
                //println("New max $maxMoves")
            }
        }
    }
}