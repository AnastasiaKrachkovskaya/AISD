package dynamicarray

class DynamicArray<T>(
    initialCapacity: Int,
) {
    private companion object {
        const val CAPACITY_INCREASING_COEFFICIENT = 2
    }

    private var array: Array<Any?> = arrayOfNulls(size = initialCapacity)

    private var capacity: Int = initialCapacity

    private var realSize: Int = 0

    private val lastIndex: Int
        get() = realSize - 1

    constructor(
        array: Array<T>
    ) : this(initialCapacity = array.size) {
        array.copyInto(this.array)
        this.realSize = array.size
    }

    @Suppress("UNCHECKED_CAST")
    operator fun get(index: Int): T {
        checkBoundsForIndex(index = index)
        return array[index] as T
    }

    fun set(index: Int, value: T) {
        checkBoundsForIndex(index = index)
        array[index] = value
    }
    fun add(value: T) {
        if (realSize == capacity) {
            val newCapacity = capacity * CAPACITY_INCREASING_COEFFICIENT
            val newArray = arrayOfNulls<Any?>(size = newCapacity)
            array.copyInto(newArray)
            capacity = newCapacity
            array = newArray
        }

        realSize++
        array[lastIndex] = value
    }
    fun removeAt(index: Int) {
        checkBoundsForIndex(index = index)
        var prevValue = array[lastIndex]
        for (shiftIndex in lastIndex downTo index) {
            val currentValue = array[shiftIndex]
            array[shiftIndex] = prevValue
            prevValue = currentValue
        }
        array[lastIndex] = null
        realSize--
    }

    fun insert(index: Int, value: T) {
        checkBoundsForIndex(index = index)
        if (realSize == capacity) {
            val newCapacity = capacity * CAPACITY_INCREASING_COEFFICIENT
            val newArray = arrayOfNulls<Any?>(size = newCapacity)
            array.copyInto(newArray)
            capacity = newCapacity
            array = newArray
        }
        realSize++
        var postValue = array[lastIndex]
        for (shiftIndex in index..lastIndex) {
            val currentValue = array[shiftIndex]
            array[shiftIndex] = postValue
            postValue = currentValue
        }
        array[index] = value
    }

    fun print() {
        print("[")
        array.forEachIndexed { index, value ->
            if (value != null) {
                print("$value")
                if (index != lastIndex) {
                    print(", ")
                }
            }
        }
        print("]\n")
    }
    private fun checkBoundsForIndex(index: Int) {
        if (index !in 0..realSize)
            throw IndexOutOfBoundsException("Нет элемента по индексу $index")
    }
}