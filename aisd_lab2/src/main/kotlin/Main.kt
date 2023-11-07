import dynamicarray.DynamicArray
import timsort.timSort

fun main() {
    println("Лабораторная работа №2")
    println("Пример массива:")
    val dynamicArray = DynamicArray(arrayOf(17, 78, 8, 20, 5, 3, 56, 23, 7, 54, 12, 16, 45, 56, 17, 79, 89, 21, 58, 30))
    dynamicArray.print()
    println("Сортировка:")
    timSort(dynamicArray).print()
    println("Введите элементы массива (через пробел):")
    val array = DynamicArray(readln().split(" ").map { it.toInt() }.toTypedArray())
    array.print()
    val sorted = timSort(array)
    println("Сортировка:")
    sorted.print()
}