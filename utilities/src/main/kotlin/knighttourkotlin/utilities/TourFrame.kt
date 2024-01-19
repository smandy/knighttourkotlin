package knighttourkotlin.utilities

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.util.concurrent.atomic.AtomicReference
import javax.swing.JFrame
import javax.swing.JPanel

class TourFrame {
    val grid = AtomicReference<Grid1>(null)

    fun doImmediateRepaint() {
        panel?.paintImmediately(0, 0, frame.width, frame.height)
    }

    fun applyGrid(g : Grid1) {
        grid.set(g)
        doImmediateRepaint()
    }

    private val panel = object : JPanel() {
        override fun paintComponent(graphics: Graphics?) {
            super.paintComponent(graphics)
            if (graphics is Graphics2D) {
                val squareSize = 40
                graphics.paint = Color.BLACK
                graphics.fillRect(
                    0, 0, squareSize * BOARDSIZE,
                    squareSize * BOARDSIZE
                )
                graphics.paint = Color.WHITE

                for (x in 0 until BOARDSIZE) {
                    for (y in 0 until BOARDSIZE) {
                        //println("x + y ${ (x + y) % 2 }")
                        if (((x + y) % 2) == 0) {
                            val x0 = x * squareSize
                            val y0 = y * squareSize
                            graphics.fillRect(
                                x0,
                                y0,
                                squareSize,
                                squareSize
                            )
                        }
                    }
                }

                val gridInstance = grid.get()
                if (gridInstance != null) {
                    graphics.paint = Color.RED

                    val coords = gridInstance
                        .visited
                        .map {
                            //println("Value is $it")
                            PInt(
                                squareSize / 2 + it.first * squareSize,
                                squareSize / 2 + it.second * squareSize
                            )
                        }
                    //println("Moves made ${gridInstance.movesMade}")
                    //println("Coords made $coords")

                    graphics.paint = Color.RED
                    graphics.stroke = BasicStroke(10.0f)
                    if (true) {
                        coords.zip(coords.drop(1)).map { (a, b) ->
                            graphics.drawLine(a.first, a.second, b.first, b.second)
                        }
                    }
                }

            }
        }
    }


    val frame : JFrame = JFrame("Tour").apply {
        setSize(320,320)
        add(panel)
        isVisible = true
        //doImmediateRepaint()
    }
}

fun main() {
    val tf = TourFrame()
    val grid = Grid1().run {
        listOf(PInt(2, 1), PInt(1, 2), PInt(-1, 2), PInt(2, -1)).fold( Grid1() ) { g, p -> g + p}
    }
    println("Grid is $grid")
    tf.applyGrid(grid)

}
