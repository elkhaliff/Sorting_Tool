fun main() {
    val(name, lastName, year) = readLine()!!.split(" ")
    println("${name[0]}. $lastName, $year years old")
}