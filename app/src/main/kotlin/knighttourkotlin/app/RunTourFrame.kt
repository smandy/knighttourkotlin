package knighttourkotlin.app

import knighttourkotlin.utilities.*
import knighttourkotlin.utilities.grids.Grid2
import knighttourkotlin.utilities.grids.Grid3

fun main() {
    val f = TourFrame()

    Tour().run(Grid3(), eager = true) {
        Thread.sleep(20)
        f.applyGrid(it)
        true
    }
}

