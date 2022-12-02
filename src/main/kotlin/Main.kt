fun main() {
    println("Укажите числом количество философов за круглым столом")
    val countPhilosopher = enteringCountNum()

    val philosophers = ArrayList<Philisopher>()
    val sticks = ArrayList<Stick>()

    for (i in 0 until countPhilosopher) {
        print("Имя философа номер ${i + 1} - ")
        var namePhilosopher = readln()
        if (namePhilosopher.trim() == "") {
            namePhilosopher = "name_${i + 1}"
        }
        philosophers.add(Philisopher(namePhilosopher))
        sticks.add(Stick("stick$i"))
    }

    val timeMap: MutableMap<Int, String> =
        philosophers.associateBy(keySelector = { philosophers.indexOf(it) },
            valueTransform = { it.name }
        ) as MutableMap<Int, String>

    while (timeMap.isNotEmpty()) {
        val indexPhilosopher = selectPhilosopherRandom(timeMap)
        val numStick = checkStickOccupancy(sticks, indexPhilosopher, countPhilosopher)
        if (numStick != -1) {
            philosophers[indexPhilosopher].state = "takes Food"
            if (numStick == indexPhilosopher) {
                println("${philosophers[indexPhilosopher].name} взял вилку слева и обедает")
            } else {
                println("${philosophers[indexPhilosopher].name} взял вилку справа и обедает")
            }
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
): Int {
    val indexStickLeft = indexPhilosopher
    val indexStickRight = if (indexPhilosopher - 1 == -1) {
        countPhilosopher - 1
    } else {
        indexPhilosopher - 1
    }
    val timeIndexStick = mutableListOf(indexStickLeft, indexStickRight)
    val rndStick = timeIndexStick.random()
    return if (timeSticks[rndStick].state == "busy") {
        timeIndexStick.remove(rndStick)
        if (timeSticks[timeIndexStick[0]].state == "busy") {
            -1
        } else {
            timeSticks[timeIndexStick[0]].state = "busy"
            timeIndexStick[0]
        }
    } else {
        timeSticks[rndStick].state = "busy"
        rndStick
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
    return if (s.isEmpty()) false
    else regex.matches(s)
}