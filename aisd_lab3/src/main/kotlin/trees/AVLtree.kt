package trees

import doubleconnectedList.DoubleConnectedList
import stack.Stack


class AVLTree(var root: AvlTreeNode? = null) {

    class AvlTreeNode(var value: Int) {
        var left: AvlTreeNode? = null
        var right: AvlTreeNode? = null
        var height: Int = 1

        fun height(node: AvlTreeNode?): Int {
            return node?.height ?: 0
        }

        fun getBalance(node: AvlTreeNode?): Int {
            return height(node?.left) - height(node?.right)
        }

        fun updateHeight() {
            height = 1 + maxOf(height(left), height(right))
        }
    }

    fun rotateLeft(z: AvlTreeNode?): AvlTreeNode? {
        val y = z?.right
        val T2 = y?.left

        y?.left = z
        z?.right = T2

        z?.updateHeight()
        y?.updateHeight()

        return y
    }

    fun rotateRight(y: AvlTreeNode?): AvlTreeNode? {
        val x = y?.left
        val T2 = x?.right

        x?.right = y
        y?.left = T2

        y?.updateHeight()
        x?.updateHeight()

        return x
    }

    fun insertNode(startNode: AvlTreeNode? = root, value: Int): AvlTreeNode? {
        if (root == null) {
            root = AvlTreeNode(value)
            return root
        }

        if (startNode == null) return AvlTreeNode(value)

        if (value < startNode.value) {
            startNode.left = insertNode(startNode.left, value)
        } else if (value > startNode.value) {
            startNode.right = insertNode(startNode.right, value)
        } else {
            return startNode
        }

        startNode.updateHeight()
        return rebalance(startNode = startNode, value)
    }

    fun rebalance(startNode: AvlTreeNode?, value: Int): AvlTreeNode? {
        if (startNode == null) return null
        val balance = startNode.getBalance(startNode)

        startNode.left?.value?.let {
            if (balance > 1 && value < it) {
                return rotateRight(startNode).also {
                    if (startNode === root) {
                        root = it
                    }
                }
            }
        }

        startNode.right?.value?.let {
            if (balance < -1 && value > it) {
                return rotateLeft(startNode).also {
                    if (startNode === root) {
                        root = it
                    }
                }
            }
        }

        startNode.left?.value?.let {
            if (balance > 1 && value > it) {
                startNode.left = rotateLeft(startNode.left)
                return rotateRight(startNode).also {
                    if (startNode === root) {
                        root = it
                    }
                }
            }
        }

        startNode.right?.value?.let {
            if (balance < -1 && value < it) {
                startNode.right = rotateRight(startNode.right)
                return rotateLeft(startNode).also {
                    if (startNode === root) {
                        root = it
                    }
                }
            }
        }
        return startNode
    }

    fun printTree(node: AvlTreeNode? = root, prefix: String = "", isTail: Boolean = true) {
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

    companion object {
        fun createFromValues(values: List<Int>): AVLTree {
            val avlTree = AVLTree(root = null)
            for (value in values) {
                avlTree.insertNode(value = value)
            }

            return avlTree
        }
    }
    fun find(key: Int): AvlTreeNode? {
        var current = root
        while (current != null) {
            if (current.value == key) {
                break
            }
            current = if (current.value < key) current.right else current.left
        }
        return current
    }

    fun insert(value: Int) {
        root = insertNode(root, value)
    }

    fun delete(value: Int) {
        root = delete(root, value)
    }

    private fun delete(node: AvlTreeNode?, key: Int): AvlTreeNode? {
        var node = node
        if (node == null) {
            return node
        } else if (node.value > key) {
            node.left = delete(node.left, key)
        } else if (node.value < key) {
            node.right = delete(node.right, key)
        } else {
            if (node.left == null || node.right == null) {
                node = if (node.left == null) node.right else node.left
            } else {
                val mostLeftChild = mostLeftChild(node.right)
                node.value = mostLeftChild!!.value
                node.right = delete(node.right, node.value)
            }
        }
        if (node != null) {
            node = rebalance(node, key)
        }
        return node
    }

    private fun mostLeftChild(node: AvlTreeNode?): AvlTreeNode? {
        var current = node
        /* loop down to find the leftmost leaf */while (current!!.left != null) {
            current = current.left
        }
        return current
    }


    fun preOrder(): DoubleConnectedList<Int>? {
        return preOrder(root, DoubleConnectedList())
    }

    fun preOrder(node: AvlTreeNode?, list: DoubleConnectedList<Int>): DoubleConnectedList<Int>? {
        if (node == null) return null
        val stack: Stack<AvlTreeNode> = Stack()
        stack.push(node)
        while (stack.size != 0) {
            val n: AvlTreeNode = stack.pop()
            val left = n.left
            val right = n.right
            list.add(n.value)
            if (right != null) stack.push(right)
            if (left != null) stack.push(left)
        }
        return list
    }

    fun inOrder(): DoubleConnectedList<Int> {
        return inOrder(root, DoubleConnectedList())
    }

    fun inOrder(node: AvlTreeNode?, list: DoubleConnectedList<Int>): DoubleConnectedList<Int> {
        var node: AvlTreeNode? = node
        val stack: Stack<AvlTreeNode> = Stack()
        while (node != null || stack.size != 0) {
            while (node != null) {
                stack.push(node)
                node = node.left
            }
            node = stack.pop()
            list.add(node.value)
            node = node.right
        }
        return list
    }

    fun postOrder(): DoubleConnectedList<Int>? {
        return postOrder(root, DoubleConnectedList())
    }

    fun postOrder(node: AvlTreeNode?, list: DoubleConnectedList<Int>): DoubleConnectedList<Int>? {
        if (node == null) return null
        val stack: Stack<AvlTreeNode> = Stack()
        val resultStack: Stack<AvlTreeNode> = Stack()
        stack.push(node)
        while (stack.size != 0) {
            val current: AvlTreeNode = stack.pop()
            val left = current.left
            val right = current.right
            resultStack.push(current)
            if (left != null) {
                stack.push(left)
            }
            if (right != null) {
                stack.push(right)
            }
        }
        while (resultStack.size != 0) {
            list.add(resultStack.pop().value)
        }
        return list
    }

    fun levelOrder(): DoubleConnectedList<Int>? {
        return levelOrder(root)
    }

    private fun levelOrder(node: AvlTreeNode?): DoubleConnectedList<Int>? {
        val values: DoubleConnectedList<Int> = DoubleConnectedList()
        val queue: DoubleConnectedList<AvlTreeNode> = DoubleConnectedList()
        if (node == null) return null
        queue.add(node)
        while (!queue.isEmpty()) {
            val current: AvlTreeNode = queue.get(0)
            val left = current.left
            val right = current.right
            queue.remove(0)
            values.add(current.value)
            if (left != null) queue.add(left)
            if (right != null) queue.add(right)
        }
        return values
    }
}