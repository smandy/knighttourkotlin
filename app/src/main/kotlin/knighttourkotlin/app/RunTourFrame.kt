package knighttourkotlin.app

import knighttourkotlin.utilities.*

fun main() {
    val f = TourFrame()

    Tour().run(Grid1(), eager = false) {
      //  Thread.sleep(20)
        f.applyGrid(it)
        true
    }
}

