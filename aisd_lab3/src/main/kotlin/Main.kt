import extenstions.removeWhitespaces
import trees.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

fun main() {
    println("Прямой обход обычного двоичного дерева:")
    createTree()
    println(" ")
    println("Пример парсинга выражения с файла:")
    val file = File("aisd_lab3/lab3.txt")
    val input = file.readLines().first()
    println(input)
    val tree = parse(input) ?: return
    val values = tree.traversePreOrder().trim().split(" ").map { it.toInt() }
    val avlTree: AVLTree = AVLTree.createFromValues(values = values)
    println("АВЛ дерево:")
    avlTree.printTree()
    println("Вставка элемента в АВЛ дерево:")
    avlTree.insert(5)
    avlTree.printTree()
    println("Удаление элемента из АВЛ дерева:")
    avlTree.delete(2)
    avlTree.printTree()
    println("Поиск элемента АВЛ дерева:")
    avlTree.find(1)?.let { println(it.value) }
    println("Прямой (префиксный) обход дерева:")
    avlTree.preOrder()?.print()
    println("Обратный (инфиксный) обход дерева:")
    avlTree.inOrder().print()
    println("Концевой (постфиксный) обход дерева:")
    avlTree.postOrder()?.print()
    println("Горизонтальный обход дерева:")
    avlTree.levelOrder()?.print()
}


fun createTree() {
    val root2 = BinaryTreeNode(1)
    val node2 = BinaryTreeNode(2)
    val node3 = BinaryTreeNode(3)
    val node4 = BinaryTreeNode(4)
    val node5 = BinaryTreeNode(5)
    val node6 = BinaryTreeNode(6)
    val node7 = BinaryTreeNode(7)
    root2.left = node2
    root2.right = node3

    node2.left = node4
    node2.right = node5

    node3.left = node6
    node3.right = node7
    val tree = BinaryTree(root2)
    println(tree.traversePreOrder())
}

fun parse(input: String) : BinaryTree? {
    val result = validateTreeString(treeString = input)

    result.fold(
        onSuccess = {
            val binaryTree = BinaryTree.parseFromString(input.removeWhitespaces())
            binaryTree?.printTree()
            return binaryTree
        },
        onFailure = {
            println("Произошла ошибка: ${it.message}")
        }
    )
    return null
}