package knighttourkotlin.app

import knighttourkotlin.utilities.*
import knighttourkotlin.utilities.grids.Grid1
import knighttourkotlin.utilities.grids.Grid2
import knighttourkotlin.utilities.grids.Grid3
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong

fun main() {
    val f = TourFrame()

    var lastSolutions = 0L
    var lastTime = 0L
    val solutions = AtomicLong()
    val executor = Executors.newSingleThreadScheduledExecutor()

    executor.scheduleAtFixedRate({
        solutions.get().let { sols ->
            System.currentTimeMillis().let { newTime ->
                val millis = newTime - lastTime
                val numSolutions = sols - lastSolutions
                println("sols=$numSolutions millis=$millis rate=${numSolutions * 1000 / millis}/sec")
                println("doImmadate=${f.doImmediateThreads} paintThreads=${f.paintThreads}")
                lastTime = newTime
                lastSolutions = sols
            }
        }
    },0, 1, TimeUnit.SECONDS)

    Tour().run(Grid2(), eager = true) {
        //Thread.sleep(20)
        f.applyGrid(it)
        solutions.incrementAndGet()
        true
    }
}

