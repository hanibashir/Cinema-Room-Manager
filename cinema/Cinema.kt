package cinema


fun main() {
    println("Enter the number of rows: ")
    val rows = readLine()!!.toInt()
    println("Enter the number of seats in each row: ")
    val seatsInRow = readLine()!!.toInt()
    val cinemaArray = Array(rows) { Array(seatsInRow) { 'S' } }
    var purchasedTickets = 0
    var currentIncome = 0
    val totalIncome = totalIncome(seatsInRow, rows)

    var menuInput = printMenu()

    while (menuInput != 0) {
        when (menuInput) {
            1 -> showSeats(cinemaArray)
            2 -> {
                val ticket = buyTicket(rows, seatsInRow, cinemaArray)
                if (ticket != 0) {
                    currentIncome += ticket
                    purchasedTickets++
                }
            }
            3 -> statics(purchasedTickets, currentIncome, totalIncome, rows, seatsInRow)
        }
        // show menu again and wait for user input
        menuInput = printMenu()
    }
}


fun printMenu(): Int {
    println(
        "\n1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit"
    )

    // get the user input
    return readLine()!!.toInt()
}

fun showSeats(cinemaArray: Array<Array<Char>>) {

    print("\nCinema:\n ")
    for (i in 0..cinemaArray[0].lastIndex) {
        print(" ${i + 1}")
    }
    println()
    for (row in 0..cinemaArray.lastIndex) {
        print(row + 1)
        for (seat in cinemaArray[row]) {
            print(" $seat")
        }
        println()
    }
}

fun buyTicket(rows: Int, seatsInRow: Int, cinemaArray: Array<Array<Char>>): Int {
    println("Enter a row number: ")
    val rowNo = readLine()!!.toInt()
    println("Enter a seat number in that row: ")
    val seatNo = readLine()!!.toInt()

    val ticketPrice: Int
    return try {
        if (cinemaArray[rowNo - 1][seatNo - 1] == 'B') {
            println("\nThat ticket has already been purchased!")
            buyTicket(rows, seatsInRow, cinemaArray)
        } else {
            val seats = rows * seatsInRow
            val halfRows = rows / 2

            ticketPrice = if (seats <= 60 || rowNo <= halfRows) 10 else 8
            println("\nTicket price: $$ticketPrice")

            cinemaArray[rowNo - 1][seatNo - 1] = 'B'
            ticketPrice
        }
    } catch (e: IndexOutOfBoundsException) {
        println("\nWrong input!")
        buyTicket(rows, seatsInRow, cinemaArray)
    }
}

fun totalIncome(seatsInRow: Int, rows: Int): Int {
    val halfRows = rows / 2
    return if ((rows * seatsInRow) > 60)
        seatsInRow * halfRows * 10 + seatsInRow * (rows - halfRows) * 8 else seatsInRow * rows * 10
}

fun statics(
    purchasedTickets: Int,
    currentIncome: Int, // $
    totalIncome: Int,
    rows: Int,
    seatsInRow: Int
) {
    val percentage = "%.2f".format((purchasedTickets.toDouble() / (rows * seatsInRow)) * 100)
    println(
        "\nNumber of purchased tickets: $purchasedTickets\n" +
                "Percentage: $percentage%\n" +
                "Current income: $$currentIncome\n" +
                "Total income: $$totalIncome"
    )
}
