package dynamicarray

class DynamicArray<T>(
    initialCapacity: Int = DEFAULT_INITIAL_CAPACITY
) {
    private companion object {
        const val CAPACITY_INCREASING_COEFFICIENT = 2
        const val DEFAULT_INITIAL_CAPACITY = 16

    }

    private var array: Array<Any?> = arrayOfNulls(size = initialCapacity)

    private var capacity: Int = initialCapacity

    var realSize: Int = 0
    val size: Int
        get() = realSize

    private val lastIndex: Int
        get() = realSize - 1

    constructor(
        array: Array<out T>
    ) : this(initialCapacity = array.size) {
        array.copyInto(this.array)
        this.realSize = array.size
    }

    private constructor(
        anyArrayInnerConstructorParam: AnyArrayInnerConstructorParam,
    ) : this(initialCapacity = anyArrayInnerConstructorParam.array.size) {
        anyArrayInnerConstructorParam.array.copyInto(this.array)
        this.realSize = anyArrayInnerConstructorParam.array.size
    }

    private class AnyArrayInnerConstructorParam(
        val array: Array<Any?>
    )

    @Suppress("UNCHECKED_CAST")
    operator fun get(index: Int): T {
        checkBoundsForIndex(index = index)
        return array[index] as T
    }

    fun slice(
        sliceRange: IntRange
    ): DynamicArray<T> {
        val slicedArray = Array<Any?>(size = sliceRange.count()) {
            get(it + sliceRange.first)
        }

        return DynamicArray(anyArrayInnerConstructorParam = AnyArrayInnerConstructorParam(slicedArray))
    }

    operator fun set(index: Int, value: T) {
        checkBoundsForIndex(index = index)
        array[index] = value
    }

    fun add(value: T) {
        if (realSize == capacity) {
            val newCapacity = capacity * CAPACITY_INCREASING_COEFFICIENT + 2
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

    fun removeLast() {
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

    @Suppress("UNCHECKED_CAST")
    fun containsAll(elements: Collection<T>): Boolean {
        return elements.map { element ->
            array.any {
                it as T == element
            }
        }.all { true }
    }

    @Suppress("UNCHECKED_CAST")
    fun contains(element: T): Boolean {
        return array.any { it as T == element }
    }

    fun isEmpty(): Boolean {
        return realSize == 0
    }

    operator fun iterator(): Iterator<T> {
        return object : Iterator<T> {
            var index: Int = 0

            override fun hasNext(): Boolean {
                return index < realSize - 1
            }

            override fun next(): T {
                val value = get(index)
                index++
                return value
            }

        }
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