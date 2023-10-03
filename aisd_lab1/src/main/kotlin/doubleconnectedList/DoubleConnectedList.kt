package doubleconnectedList

class DoubleConnectedList<T> {

    private var head: ListNode<T>? = null
    private var tail: ListNode<T>? = null

    private var _size = 0
    val size
        get() = _size

    operator fun get(index: Int): T {
        return findNodeByIndex(index = index).value

    }

    fun getLast(): T {
        return requireNotNull(tail).value
    }

    fun isEmpty(): Boolean {
        return _size == 0
    }

    fun clear() {
        head = null
        tail = null
        _size = 0
    }

    /**
     * Возвращает значение последнего элемента в списке и удаляет его из списка
     * сложность О(1) так как внутри [remove] используется [findNodeByIndex] с оптимизацией
     * на последний элемент
     */
    fun popLast(): T {
        val lastValue = requireNotNull(tail).value
        remove(index = _size - 1)
        return lastValue
    }

    fun lastElement(): T {
        val lastValue = requireNotNull(tail).value
        return lastValue
    }

    fun add(element: T) {
        tail?.let { tail ->
            val newNode = ListNode(element)
            newNode.previousNode = tail
            tail.nextNode = newNode
            this.tail = newNode
        } ?: run {
            // Если список пустой, то надо инициализировать head
            head = ListNode(element)
            tail = head
        }
        _size++
    }

    fun remove(index: Int) {
        val targetNodeToRemove = findNodeByIndex(index = index)
        when {
            // когда список из одного элемента и его надо удалить
            targetNodeToRemove === head && targetNodeToRemove === tail -> {
                head = null
                tail = null
            }

            // список не из одного элемента и надо удалить первый элемент
            targetNodeToRemove === head -> {
                head?.nextNode?.previousNode = null
                head = head?.nextNode
            }

            // список не из одного элемента и надо удалить последний элемент
            targetNodeToRemove === tail -> {
                tail?.previousNode?.nextNode = null
                tail = tail?.previousNode
            }

            // список не из одного элемента и надо удалить не первый и не последний элемент
            else -> {
                targetNodeToRemove.previousNode?.nextNode = targetNodeToRemove.nextNode
                targetNodeToRemove.nextNode?.previousNode = targetNodeToRemove.previousNode
            }
        }
        _size--
    }
    operator fun iterator(): Iterator<T> {
        return object : Iterator<T> {
            var index: Int = 0

            override fun hasNext(): Boolean {
                return index < _size - 1
            }

            override fun next(): T {
                val value = get(index)
                index++
                return value
            }

        }
    }

    fun set(index: Int, newValue: T){
        checkBoundsForIndex(index = index)
        findNodeByIndex(index = index).value = newValue
    }

    fun insert(index: Int, value: T) {
        when {
            index == 0 && head == null -> {
                add(value)
            }

            else -> {
                val nodeOnIndex = findNodeByIndex(index = index)
                val newNode = ListNode(value)

                nodeOnIndex.previousNode?.nextNode = newNode
                newNode.previousNode = nodeOnIndex.previousNode
                nodeOnIndex.previousNode = newNode
                newNode.nextNode = nodeOnIndex

                // если происходит insert на место головного элемента, головной элемент становится тем, что только что
                // добавили
                if (nodeOnIndex === head) {
                    head = newNode
                }
                // если insert на хвостовой элемент, ничего не надо менять, так как хвостовой элемент так и останется
                // последним
            }
        }
        _size++
    }

    fun print() {
        var currentNode = head
        print("[")
        while (currentNode != null) {
            print("${currentNode.value}")
            if (currentNode != tail) {
                print(", ")
            }
            currentNode = currentNode.nextNode
        }
        print("]\n")
    }

    private fun findNodeByIndex(index: Int): ListNode<T> {
        if (index == _size - 1) {
            return requireNotNull(tail)
        }
        var currentListNode = head
        var counter = 0
        while (counter != index) {
            currentListNode = currentListNode?.nextNode
            counter++
        }
        if (currentListNode == null) throw IndexOutOfBoundsException("Нет элемента по индексу $index")

        return currentListNode
    }

    private fun checkBoundsForIndex(index: Int) {
        if (index !in 0..<_size)
            throw IndexOutOfBoundsException("Нет элемента по индексу $index")
    }
}