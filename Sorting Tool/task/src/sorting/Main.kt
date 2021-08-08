package sorting

import java.io.File
import java.lang.Exception
import java.util.*

class SortingSystem {
    var inputFile = ""
    var outputFile = ""
    val cTypeLong = "long"
    val cTypeLine = "line"
    val cTypeWord = "word"
    var typeSort = cTypeLong

    val cSortNatural = "natural"
    val cSortByCount = "byCount"
    var sortingType = cSortNatural

    private val mutableMap = mutableMapOf<String, Int>()

    fun sort() {
        var out = ""
        var size = 0
        var type = "numbers"
        var scanner = Scanner(System.`in`)
        if (inputFile != "") scanner = Scanner(File(inputFile))

        when (typeSort) {
            cTypeLong -> {
                val list = mutableListOf<Long>()
                while (scanner.hasNextLong())
                    list.add(scanner.nextLong())
                size = list.size
                list.sort()
                if (sortingType == cSortNatural)
                    out = list.joinToString(" ")
                else if (sortingType == cSortByCount) {
                    var curr = Long.MIN_VALUE
                    for (l in list)
                        if (curr != l) {
                            curr = l
                            val count = list.count { it == curr }
                            val percent = ((count.toDouble() / size.toDouble()) * 100).toInt()
                            mutableMap ["$curr: $count time(s), $percent%."] = count
                        }
                }
            }
            cTypeLine, cTypeWord -> {
                val list = mutableListOf<String>()
                if (typeSort == cTypeLine) {
                    type = "lines"
                    while (scanner.hasNextLine())
                        list.add(scanner.nextLine())
                } else {
                    type = "words"
                    while (scanner.hasNext()) {
                        list.add(scanner.next())
                    }
                }
                size = list.size
                list.sort()
                if (sortingType == cSortNatural)
                    if (typeSort == cTypeLine)
                        for (l in list) out += l + "\n"
                    else
                        out = list.joinToString(" ")
                else if (sortingType == cSortByCount) {
                    var curr = ""
                    for (l in list)
                        if (curr != l) {
                            curr = l
                            val count = list.count { it == curr }
                            val percent = ((count.toDouble() / size.toDouble()) * 100).toInt()
                            mutableMap ["$curr: $count time(s), $percent%."] = count
                        }
                }
            }
        }
        if (inputFile != "") scanner.close()
        println("Total $type: $size.")
        if (sortingType == cSortNatural) {
            if (outputFile != "") File(outputFile).writeText("Sorted data:$out")
            else println("Sorted data:$out")
        } else if (sortingType == cSortByCount) {
            sortMap(mutableMap).forEach {
                if (outputFile != "") File(outputFile).appendText(it.key)
                else println(it.key)
            }
        }
    }

    private fun sortMap(hashMap: MutableMap<String, Int>): MutableMap<String, Int> {
        val sortMap = mutableMapOf<String, Int>()
        val indSort = hashMap.toList()
            .sortedBy { (_, value) -> value }
            .toMap()
        indSort.forEach { (_, value) ->
            hashMap.forEach { (t, u) ->
                if (value == u) sortMap[t] = u
            }
        }
        return sortMap
    }
}

fun main(args: Array<String>) {
    val ss = SortingSystem()
    val errData = "No data type defined!"
    val errSort = "No sorting type defined!"
    if (args.isNotEmpty()) {
        args.forEachIndexed { index, s ->
            if (s.contains("-")) {
                when (s) {
                    "-dataType" ->
                        try {
                            if (args[index + 1] in listOf(ss.cTypeLong, ss.cTypeLine, ss.cTypeWord))
                                ss.typeSort = args[index + 1]
                            else println(errData)
                        } catch (e: Exception) {
                            println(errData)
                        }
                    "-sortingType" ->
                        try {
                            if (args[index + 1] in listOf(ss.cSortNatural, ss.cSortByCount))
                                ss.sortingType = args[index + 1]
                            else println(errSort)
                        } catch (e: Exception) {
                            println(errSort)
                        }
                    "-inputFile" -> ss.inputFile = args[index + 1]
                    "-outputFile" -> ss.outputFile = args[index + 1]
                    else -> println("\"$s\" is not a valid parameter. It will be skipped.")
                }
            }
        }
    }
    ss.sort()
}