fun main() {
    val char = readLine()!![0]
    println(char in 'A'..'Z' || char in '1'..'9')
}