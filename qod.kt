package cinema

fun main() {

    println("Enter the number of rows:")
    val row = readln().toInt()
    println("Enter the number of seats in each row:")
    val seat = readln().toInt()

    val list = MutableList(row) { MutableList(seat) { "S" } }

    var quest = ""
    while (quest != "0") {
        println(
            """
1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit"""
        )
        quest = readln()
        when(quest) {
            "1" -> show(list)
            "2" -> buy(list)
            "3" -> stat(list)
            "0" -> return
            else -> println("Incorrect input.")
        }
    }

}

fun show(list: MutableList<MutableList<String>>) {

    println("\nCinema:")
    var sh = " "
    var i = 1
    for (ind in list[0].indices) sh += " ${ind + 1}"
    println(sh)
    for (l in list) {
        println("${i++} " + l.joinToString(" "))
    }

}

fun buy(list: MutableList<MutableList<String>>): MutableList<MutableList<String>> {

    println("\nEnter a row number:")
    val mrow = readln().toInt()
    println("Enter a seat number in that row:")
    val mseat = readln().toInt()
    try {
        if (list[mrow - 1][mseat - 1] != "B") {
            println(
                "\nTicket price: $" + when(list.size * list[0].size) {
                    in 1..60 -> 10
                    else -> if (mrow <= list.size / 2) 10 else 8
                }
            )
            list[mrow - 1][mseat - 1] = "B"
        } else {
            println("\nThat ticket has already been purchased!\nPlease choose another place.")
            buy(list)
        }
    } catch (e: IndexOutOfBoundsException) {
        println("\nWrong input!\nPlease choose another place.")
        buy(list)
    }
    return list

}

fun stat(list: MutableList<MutableList<String>>) {

    var tikets = 0
    var allprice = 0
    for (i in list.indices) {
        for (j in list[i].indices) {
            if (list[i][j] == "B") {
                tikets++
                if (list.size * list[0].size <= 60) allprice += 10 else if (i + 1 <= list.size / 2) allprice += 10 else allprice += 8
            }
        }
    }
    println("\nNumber of purchased tickets: $tikets")
    val percentage = tikets.toFloat() * 100 / (list.size * list[0].size)
    println("Percentage: " + "%.2f".format(percentage) + "%")
    println("Current income: \$$allprice")
    println(
        "Total income: $" + when(list.size * list[0].size) {
            in 1..60 -> list.size * list[0].size * 10
            else -> list.size / 2 * list[0].size * 10 + (list.size - list.size / 2) * list[0].size * 8
        }
    )

}
