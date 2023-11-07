package trees

import doubleconnectedList.DoubleConnectedList
import stack.Stack


class TreeNode(var value: Int) {
    val children = DoubleConnectedList<TreeNode>()

    fun addChild(node: TreeNode) {
        if (children.size < 2)
            children.add(node)
    }

    fun traversePreOrder() {
        print(" " + value)
        for (child in children) {
            child.traversePreOrder()
        }
    }
}

fun parseTree(input: String): TreeNode? {
    val stack = Stack<TreeNode>()
    var current: TreeNode? = null
    var i = 0
    while (i < input.length) {
        when (val char = input[i]) {
            '(' -> {
                if (current != null) {
                    var newNode = TreeNode(Character.getNumericValue(input[i + 1]))
                    if (current.children.size == 2) {
                        println("Это не двоичное дерево. Потомков в дереве не может быть больше двух.")
                        return null
                    }
                    while (true) {
                        if (input[i + 2] in '0'..'9') {
                            val number = newNode.value.toString() + input[i + 2]
                            newNode = TreeNode(number.toInt())
                            i++
                        } else break
                    }
                    current.children.add(newNode)
                    stack.push(current)
                    current = newNode
                    i += 2

                } else {
                    current = TreeNode(Character.getNumericValue(input[i + 1]))
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
                current = TreeNode(number.toInt())
                i++
            }
        }
    }

    return current
}

fun printTree(node: TreeNode, prefix: String = "", isTail: Boolean = true) {

    val children = node.children

    println("$prefix${if (isTail) "└── " else "├── "}${node.value}")
    for (i in 0..<children.size) {
        val child = children[i]
        printTree(child, "$prefix${if (isTail) "    " else "│   "}", i == children.size - 1)
    }


}
