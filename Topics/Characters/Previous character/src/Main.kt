fun main() {
    val arrChar = Array(4) { readLine()!! }.joinToString("")
    arrChar.forEach { println((it.toInt() - 1).toChar()) }
}
