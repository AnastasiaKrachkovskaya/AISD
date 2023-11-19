package trees

class BinaryTree(private var root: BinaryTreeNode?) {

    fun printTree(node: BinaryTreeNode? = root, prefix: String = "", isTail: Boolean = true) {
        if (node == null) return
        println("$prefix${if (isTail) "└── " else "├── "}${node.value}")
        node.left?.let {
            printTree(
                it, "$prefix${if (isTail) "    " else "│   "}",
                node.right == null
            )
        }

        node.right?.let {
            printTree(
                it, "$prefix${if (isTail) "    " else "│   "}",
                true
            )
        }
    }

    fun traversePreOrder(node: BinaryTreeNode? = root): String {
        var str: String = ""
        if (node == null) return str
        str += "${node.value} "
        str += traversePreOrder(node = node.left)
        str += traversePreOrder(node = node.right)
        return str
    }

    companion object {
        fun parseFromString(input: String): BinaryTree? {
            return BinaryTree(
                root = parseTreeFromString(input = input)
            )
        }
    }
}