package stack

import doubleconnectedList.DoubleConnectedList

class Stack<T> {
    private var doubleConnectedList = DoubleConnectedList<T>()
    val size
        get() = doubleConnectedList.size


    fun push(element: T) {
        doubleConnectedList.add(element)
    }

    fun pop(): T {
        return doubleConnectedList.popLast()
    }

    //возвращает верхнее значение, ничего не удаляя
    fun peek(): T {
        return doubleConnectedList.lastElement()
    }

    fun print() {
        doubleConnectedList.print()
    }

    fun popAll(): DoubleConnectedList<T> {
        val allValues = doubleConnectedList
        doubleConnectedList = DoubleConnectedList()
        return allValues
    }
}