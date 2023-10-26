package aisd_lab1

import algorithm.Algorithm
import doubleconnectedList.DoubleConnectedList
import dynamicarray.DynamicArray
import stack.Stack


fun main() {
    while(true){
        println("Выберите, что хотите протестировать:")
        println("1 -> Динамический массив")
        println("2 -> Двусвязный список")
        println("3 -> Стек")
        println("4 -> Алгоритм сортировочной станции")

        val choose: Int = readln().toInt()
        when (choose) {
            1 -> processDynamicArrayCase()
            2 -> processDoubleConnectedListCase()
            3 -> processStackCase()
            4 -> processAlgorithm()
        }
    }
}

fun processDynamicArrayCase() {
    println("Введите элементы массива (через пробел):")
    val dynamicArray = DynamicArray<String>(readln().split(" ").toTypedArray())

    dynamicArray.print()

    println("Введите индекс для получения его значения:")
    val indexToGet = readln().toInt()
    dynamicArray[indexToGet]
    println(dynamicArray[indexToGet])

    println("Введите элемент для добавления:")
    val toAdd = readln()
    dynamicArray.add(toAdd)
    dynamicArray.print()

    println("Введите индекс для удаления:")
    val indexToRemove = readln().toInt()
    dynamicArray.removeAt(indexToRemove)
    dynamicArray.print()

    println("Введите индекс и элемент для замены:")
    val (indexToReplace, newValue) = readln().split(" ")
    dynamicArray.set(indexToReplace.toInt(), newValue)
    dynamicArray.print()

    println("Введите индекс и элемент для вставки:")
    val (indexToInsert, newNum) = readln().split(" ")
    dynamicArray.insert(indexToInsert.toInt(), newNum)
    dynamicArray.print()
}

fun processDoubleConnectedListCase() {
    println("Введите элементы двусвязного списка (через пробел):")
    val inputString = readln()
    val doubleConnectedList = inputString.splitIntoTokensDoubleConnectedList()
    doubleConnectedList.print()

    println("Введите индекс для получения его значения:")
    val indexToGet = readln().toInt()
    println(doubleConnectedList[indexToGet])

    println("Введите элемент для добавления:")
    val toAdd = readln()
    doubleConnectedList.add(toAdd)
    doubleConnectedList.print()

    println("Введите индекс для удаления:")
    val indexToRemove = readln().toInt()
    doubleConnectedList.remove(indexToRemove)
    doubleConnectedList.print()

    println("Введите индекс и элемент для замены:")
    val (indexToReplace, newValue) = readln().split(" ")
    doubleConnectedList.set(indexToReplace.toInt(), newValue)
    doubleConnectedList.print()

    println("Введите индекс и элемент для вставки:")
    val (indexToInsert, newNum) = readln().split(" ")
    doubleConnectedList.insert(indexToInsert.toInt(), newNum)
    doubleConnectedList.print()

    println("А сейчас список очистится, чтобы Вы посмотрели работу функции clear")
    doubleConnectedList.clear()
    doubleConnectedList.print()
}

fun processStackCase() {
    println("Введите элементы стека (через пробел):")
    val inputString = readln()
    val stack = inputString.splitIntoTokensStack()
    stack.print()

    println("Введите элемент для добавления:")
    val toAdd = readln()
    stack.push(toAdd)
    stack.print()

    println("Сейчас удалится и выведется последний элемент:")
    println("Сейчас удалился элемент: ${stack.pop()}")
    stack.print()

    println("Значение последнего элемента: ${stack.peek()}")
}

fun processAlgorithm() {
    println("Введите выражение (каждый символ разделяйте пробелом):")
    val inputString = readln()
    val str = Algorithm(inputString).toPostfix()
    println("Выражение в постфиксной записи: ${str}")
    val result = Algorithm(inputString).calculator()
    println("Результат: ${result}")
//    Можете вбивать эти примеры и сразу проверите правильность расчетов:
//    12 * 5 - 36 / 3 //48
//    ( cos ( 5 ) - 3 ) * 4 //-10.86535

}
fun String.splitIntoTokensDoubleConnectedList(): DoubleConnectedList<String> {
    val doubleConnectedList = DoubleConnectedList<String>()

    var currentToken = ""
    for (symbol in this) {
        if (symbol == ' ') {
            doubleConnectedList.add(currentToken)
            currentToken = ""
        } else {
            currentToken += symbol
        }
    }
    if (currentToken.isNotEmpty()) doubleConnectedList.add(currentToken)

    return doubleConnectedList
}
fun String.splitIntoTokensStack(): Stack<String> {
    val stack = Stack<String>()

    var currentToken = ""
    for (symbol in this) {
        if (symbol == ' ') {
            stack.push(currentToken)
            currentToken = ""
        } else {
            currentToken += symbol
        }
    }
    if (currentToken.isNotEmpty()) stack.push(currentToken)

    return stack
}
