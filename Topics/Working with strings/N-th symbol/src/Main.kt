fun main() {
    val str = readLine()!!
    val n = readLine()!!.toInt()
    println("Symbol # $n of the string \"$str\" is '${str[n - 1]}'")
}
