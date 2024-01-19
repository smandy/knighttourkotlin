package knighttourkotlin.app

import knighttourkotlin.utilities.*

fun main() {
    val f = TourFrame()

    Tour().run {
        f.applyGrid(it);
        Thread.sleep(1000);
        true
    }
}

