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
        if (checkStickOccupany(sticks, indexPhilosopher, countPhilosopher)) {

        }
        timeMap.remove(indexPhilosopher)
    }
}

fun checkStickOccupany(
    timeSticks: ArrayList<Stick>,
    indexPhilosopher: Int,
    countPhilosopher: Int
): Boolean {
    val indexStickLeft = indexPhilosopher
    val indexStickRight = if (indexPhilosopher - 1 == -1){
        countPhilosopher - 1
    } else{
        indexPhilosopher
    }


    return true
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