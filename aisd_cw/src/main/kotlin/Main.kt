import dynamicarray.DynamicArray
import graphs.Edge
import timsort.timSort
import java.io.File

fun main() {
    val file = File("aisd_cw/cw.txt")
    val inputLines = file.readLines()
    val vertexKeys = inputLines.first().split(" ")
    val adjacencyMatrixLines = inputLines.drop(1)

    // построение списка ребер
    val edgesArray = DynamicArray<Edge>()
    adjacencyMatrixLines.forEachIndexed { columnIndex, matrixLine ->
        matrixLine.split(" ").forEachIndexed WeightsForEach@{ rowIndex, weightValueStr ->
            val weight = weightValueStr.toInt()
            // так как граф неориентированный должен быть
            if (rowIndex < columnIndex) return@WeightsForEach
            // 0 значит, что ребра нет
            if (weight == 0) return@WeightsForEach
            edgesArray.add(
                value = Edge(
                    vertex1 = vertexKeys[columnIndex],
                    vertex2 = vertexKeys[rowIndex],
                    weight = weight,
                )
            )
        }
    }

    // сортировка списка ребер по неубыванию
    val sortedEdges = timSort(edgesArray)
    val minSpanningTreeVertexesKeys = DynamicArray<String>()
    val minSpanningTreeEdges = DynamicArray<Edge>()

    for (index in 0 until  sortedEdges.size) {
        val currentEdge = sortedEdges[index]

        var shouldAddCurrentEdge = false
        if (!minSpanningTreeVertexesKeys.contains(currentEdge.vertex1)) {
            minSpanningTreeVertexesKeys.add(currentEdge.vertex1)
            shouldAddCurrentEdge = true
        }
        if (!minSpanningTreeVertexesKeys.contains(currentEdge.vertex2)) {
            minSpanningTreeVertexesKeys.add(currentEdge.vertex2)
            shouldAddCurrentEdge = true
        }

        if (shouldAddCurrentEdge) {
            minSpanningTreeEdges.add(currentEdge)
        }

        if (minSpanningTreeVertexesKeys.containsAll(vertexKeys)) {
            break
        }
    }

    println("\n\nРебра минимального остовного дерева:\n")
    var summaryWeight = 0
    for (edge in minSpanningTreeEdges) {
        println("${edge.vertex1} ${edge.vertex2}")
        summaryWeight += edge.weight
    }

    println("\nСуммарный вес минимального остовного дерева: $summaryWeight")
}