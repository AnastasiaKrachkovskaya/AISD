package trees

import stack.Stack

fun parseTreeFromString(input:String): BinaryTreeNode? {
    val stack = Stack<BinaryTreeNode>()
    var current: BinaryTreeNode? = null
    var i = 0
    while (i < input.length) {
        when (val char = input[i]) {
            '(' -> {
                if (current != null) {
                    var newNode = BinaryTreeNode(Character.getNumericValue(input[i + 1]))
                    if (current.left != null && current.right != null) {
                        println("Это не двоичное дерево. Потомков в дереве не может быть больше двух.")
                        return null
                    }
                    while (true) {
                        if (input[i + 2] in '0'..'9') {
                            val number = newNode.value.toString() + input[i + 2]
                            newNode = BinaryTreeNode(number.toInt())
                            i++
                        } else break
                    }
                    if (current.left == null) {
                        current.left = newNode
                    } else if (current.right == null) {
                        current.right = newNode
                    }
                    stack.push(current)
                    current = newNode
                    i += 2

                } else {
                    current = BinaryTreeNode(Character.getNumericValue(input[i + 1]))
                    i += 2
                }
            }

            ')' -> {
                if (stack.size > 0) {
                    current = stack.pop()
                }
                i++
            }

            in '0'..'9' -> {
                var number = current?.value.toString() + char
                current = BinaryTreeNode(number.toInt())
                i++
            }
        }
    }
    return current
}