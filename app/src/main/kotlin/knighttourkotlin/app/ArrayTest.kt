package knighttourkotlin.app

fun main() {
    val xs = BooleanArray(10) { false }

    println(xs.joinToString(",", "[", "]"))
    xs[2] = true
    println(xs.joinToString(",", "[", "]"))
    val ys = xs.copyOf().filter { it }
    println(ys.joinToString(",", "[", "]"))
}