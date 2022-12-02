fun main(args: Array<String>) {
    println("Укажите числом количество философов за круглым столом")
    val countPhilosopher = enteringCountNum()

    val philosophers = ArrayList<Philisopher>()
    val sticks = ArrayList<Stick>()

    for (i in 0 until countPhilosopher) {
        print("Имя философа номер $i - ")
        val namePhilosopher = readln()
        philosophers.add(Philisopher(namePhilosopher))
        sticks.add(Stick("stick$i"))
    }

    val timeMap: MutableMap<Int, String> =
        philosophers.associateBy(keySelector = { philosophers.indexOf(it) },
            valueTransform = { it.name }
        ) as MutableMap<Int, String>

    while (timeMap.isNotEmpty()) {
        val indexPhilosopher = selectPhilosopherRandom(timeMap)
        if (checkStickOccupancy(sticks, indexPhilosopher, countPhilosopher)) {
            philosophers[indexPhilosopher].state = "takes Food"
            println("${philosophers[indexPhilosopher].name} обедает")
        } else {
            println("${philosophers[indexPhilosopher].name} размышляет")
        }
        timeMap.remove(indexPhilosopher)
    }
}

fun checkStickOccupancy(
    timeSticks: ArrayList<Stick>,
    indexPhilosopher: Int,
    countPhilosopher: Int
): Boolean {
    val indexStickLeft = indexPhilosopher
    val indexStickRight = if (indexPhilosopher - 1 == -1) {
        countPhilosopher - 1
    } else {
        indexPhilosopher - 1
    }
    val timeIndexStick = mutableListOf(indexStickLeft, indexStickRight)
    val rndStick = timeIndexStick.random()
    if (timeSticks[rndStick].state == "busy") {
        timeIndexStick.remove(rndStick)
        if (timeSticks[timeIndexStick[0]].state == "busy") {
            return false
        } else {
            timeSticks[0].state = "busy"
            println("${timeSticks[0].name} занята")
            return true
        }
    } else {
        timeSticks[rndStick].state = "busy"
        println("${timeSticks[rndStick].name} занята")
        return true
    }
}

fun selectPhilosopherRandom(mapPhilosopher: Map<Int, String>): Int {
    return ArrayList(mapPhilosopher.keys).random()
}

fun enteringCountNum(): Int {
    var intCountBool = true
    var userCount = ""
    while (intCountBool) {
        print("Введите количество указав целое положительное число - ")
        userCount = readln()
        if (isPosOrNegNumber(userCount)) {
            intCountBool = false
        }
    }
    return userCount.toInt()
}

fun isPosOrNegNumber(s: String): Boolean {
    val regex = """^[0-9]+$""".toRegex()
    return if (s.isNullOrEmpty()) false
    else regex.matches(s)
}