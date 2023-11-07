package timsort

import dynamicarray.DynamicArray
import kotlin.math.pow

fun <T : Comparable<T>> timSort(arr: DynamicArray<T>): DynamicArray<T> {
    val n = arr.size
    for (i in 0 until n step getMinrun(n)) {
        insertionSort(arr, i, minOf(i + getMinrun(n), n))
    }

    var run = getMinrun(n)
    while (run < n) {
        for (i in 0 until n step 2 * run) {
            val left = i
            val mid = minOf(i + run, n)
            val right = minOf(i + 2 * run, n)

            if (mid < right) {
                merge(arr, left, mid, right)
            }
        }
        run *= 2
    }
    return arr
}

fun getMinrun(n: Int): Int {
    var n = n
    var r = 0
    while (n >= 64) {
        r = r or (n and 1)
        n = n shr 1
    }
    return n + r
}

fun <T : Comparable<T>> insertionSort(arr: DynamicArray<T>, left: Int, right: Int): DynamicArray<T> {
    for (i in left + 1 until right) {
        val key = arr[i]
        var j = i - 1
        while (j >= left && arr[j] > key) {
            arr[j + 1] = arr[j]
            j--
        }
        arr[j + 1] = key
    }
    return arr
}

fun <T : Comparable<T>> merge(arr: DynamicArray<T>, left: Int, mid: Int, right: Int) {
    val len1 = mid - left
    val len2 = right - mid
    val leftArray = arr.slice(left until mid)
    val rightArray = arr.slice(mid until right)
    var i = 0
    var j = 0
    var k = left

    var gallopL = 0
    var gallopR = 0

    while (i < len1 && j < len2) {
        if (gallopL == GALLOP_MAX_COUNTER) {
            val index = gallopMode(arr = leftArray, left = i, comparableValue = rightArray[j])
            gallopL = 0
            while (i <= index) {
                arr[k] = leftArray[i]
                i++
                k++
            }
            continue
        }
        if (gallopR == GALLOP_MAX_COUNTER) {
            val index = gallopMode(arr = rightArray, left = j, comparableValue = leftArray[i])
            gallopR = 0
            while (j <= index) {
                arr[k] = leftArray[i]
                j++
                k++
            }
            continue
        }

        if (leftArray[i] <= rightArray[j]) {
            arr[k] = leftArray[i]
            i++
            gallopL++
            gallopR = 0
        } else {
            arr[k] = rightArray[j]
            j++
            gallopR++
            gallopL = 0
        }
        k++
    }
    while (i < len1) {
        arr[k] = leftArray[i]
        i++
        k++
    }
    while (j < len2) {
        arr[k] = rightArray[j]
        j++
        k++
    }
}


private const val GALLOP_MAX_COUNTER = 7
private fun <T : Comparable<T>> gallopMode(arr: DynamicArray<T>, left: Int, comparableValue: T): Int {
    var powerOf2 = 1
    fun getCheckableIndex(): Int {
        return (left + 2f.pow(powerOf2)).toInt()
    }

    var checkableIndex = getCheckableIndex()
    while (checkableIndex < arr.size) {
        if (arr[checkableIndex] > comparableValue) {
            val indexOfFirstGreater =
                timSortBinarySearch(
                    arr = arr,
                    left = left,
                    right = arr.size - 1,
                    comparableValue = comparableValue,
                )
            return indexOfFirstGreater
        }
        powerOf2++
        checkableIndex = getCheckableIndex()
    }

    return arr.size - 1
}

private fun <T : Comparable<T>> timSortBinarySearch(
    arr: DynamicArray<T>,
    left: Int,
    right: Int,
    comparableValue: T
): Int {
    var start = left
    while (start <= right - 1) {
        val mid = (start + right) / 2

        if (comparableValue <= arr[mid]) {
            return mid
        } else {
            start = mid + 1
        }
    }
    return right
}