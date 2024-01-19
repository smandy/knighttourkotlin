package knighttourkotlin.app

import knighttourkotlin.utilities.*

fun main() {
    val f = TourFrame()

    Tour().run(true) {
      //  Thread.sleep(20)
        f.applyGrid(it)
        true
    }
}

