import dynamicarray.DynamicArray

fun main() {
    println("Введите элементы массива (через пробел):")
    val dynamicArray = DynamicArray(readln().split(" ").map { it.toInt() }.toTypedArray())

    dynamicArray.print()

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