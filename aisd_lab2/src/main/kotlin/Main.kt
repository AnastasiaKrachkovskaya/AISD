import dynamicarray.DynamicArray
import timsort.timSort

fun main() {
    println("Лабораторная работа №2")
    println("Пример массива:")
    val dynamicArray = DynamicArray(arrayOf(1, 7, 8, 2, 5, 3))
    dynamicArray.print()
    println("Сортировка:")
    timSort(dynamicArray).print()
    println("Введите элементы массива (через пробел):")
    val array = DynamicArray(readln().split(" ").toTypedArray())
    array.print()
    println("Сортировка:")
    timSort(array).print()
}