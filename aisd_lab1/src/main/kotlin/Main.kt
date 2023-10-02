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
    val dynamicArray = DynamicArray(readln().split(" ").map { it.toInt() }.toTypedArray())

    dynamicArray.print()

    println(dynamicArray.contains(2))

    println("Введите индекс для получения его значения:")
    val indexToGet = readln().toInt()
    dynamicArray[indexToGet]
    println(dynamicArray[indexToGet])

    println("Введите элемент для добавления:")
    val toAdd = readln().toInt()
    dynamicArray.add(toAdd)
    dynamicArray.print()

    println("Введите индекс для удаления:")
    val indexToRemove = readln().toInt()
    dynamicArray.removeAt(indexToRemove)
    dynamicArray.print()

    println("Введите индекс и элемент для замены:")
    val (indexToReplace, newValue) = readln().split(" ").map { it.toInt() }
    dynamicArray.set(indexToReplace, newValue)
    dynamicArray.print()

    println("Введите индекс и элемент для вставки:")
    val (indexToInsert, newNum) = readln().split(" ").map { it.toInt() }
    dynamicArray.insert(indexToInsert, newNum)
    dynamicArray.print()
}

fun processDoubleConnectedListCase() {
    val doubleConnectedList = DoubleConnectedList<Int>()

    doubleConnectedList.add(25)
    doubleConnectedList.add(30)
    doubleConnectedList.add(100)

    doubleConnectedList.print()

    doubleConnectedList.popLast()
    doubleConnectedList.print()

    val stack = Stack<Int>()

    stack.print()

    doubleConnectedList.remove(1)
    doubleConnectedList.print()
    doubleConnectedList.remove(0)
    doubleConnectedList.print()

    doubleConnectedList.insert(0, 10)
    doubleConnectedList.print()

    doubleConnectedList.insert(0, 20)
    doubleConnectedList.print()

    doubleConnectedList.insert(0, 30)
    doubleConnectedList.print()

    doubleConnectedList.insert(1, 25)
    doubleConnectedList.print()

    doubleConnectedList.insert(3, 5)
    doubleConnectedList.print()
}

fun processStackCase() {
    val stack = Stack<Int>()
    stack.push(24)
    stack.push(11)
    stack.push(67)
    stack.print()
    println(stack.peek())
    stack.pop()
}

fun processAlgorithm() {

    val inputString = readln()
    val str = Algorithm(inputString).toPostfix()
    println(str)
    val result = Algorithm(inputString).calc()
    println(result)

//    ("   12*5   - 36 / 3") // 48
//
//    ("12 + 50 / 5 - 3  ") // 19
//

}
