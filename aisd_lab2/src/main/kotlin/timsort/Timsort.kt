package timsort

import dynamicarray.DynamicArray

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

fun <T> Comparable<T>.isGreaterThan(other: T): Boolean {
    return this > other
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
    var gallop = 0
    while (i < len1 && j < len2) {
        if (gallop == 7) {
            if (leftArray[i] <= rightArray[j]) {
                arr[k++] = leftArray[i++];
                if (i < len1) arr[k++] = leftArray[i++];
            } else {
                i -= 2;
                k -= 2;
                gallop = 0;
            }
        } else if (leftArray[i] <= rightArray[j]) {
            arr[k++] = leftArray[i++];
            gallop++;
        } else {
            arr[k++] = rightArray[j++];
            gallop = 0;
        }
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