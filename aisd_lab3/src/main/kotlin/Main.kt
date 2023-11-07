import extenstions.removeWhitespaces
import trees.TreeNode
import trees.parseTree
import trees.printTree
import trees.validateTreeString

fun main() {
    println("Прямой обход обычного двоичного дерева:")
    createTree()
    println(" ")
    println("Пример парсинга выражения $input:")

    val result = validateTreeString(treeString = input)

    result.fold(
        onSuccess = {
            val root = parseTree(input.removeWhitespaces())
            if (root != null) {
                printTree(root)
            }
            //println("Дерево верное")
        },
        onFailure = {
            println("Произошла ошибка: ${it.message}")
        }
    )

}


fun createTree() {
    val root2 = TreeNode(1)
    val node2 = TreeNode(2)
    val node3 = TreeNode(3)
    val node4 = TreeNode(4)
    val node5 = TreeNode(5)
    val node6 = TreeNode(6)
    val node7 = TreeNode(7)

    root2.addChild(node2)
    root2.addChild(node3)

    root2.addChild(node3)

    node2.addChild(node4)
    node2.addChild(node5)

    node3.addChild(node6)
    node3.addChild(node7)
    root2.traversePreOrder()
}

val input = "(12(1) (2)"
