import dynamicarray.DynamicArray
import graphs.AdjacencyMatrix
import graphs.Edge
import graphs.bfs
import graphs.dfs
import timsort.timSort

fun main() {
    val adjacencyMatrix = AdjacencyMatrix.createFromFile("aisd_cw/cw.txt")

    val edgesDynamicArray = adjacencyMatrix.toEdgesDynamicArray()
    println("\nПредставление в виде списка ребер:")
    for (edge in edgesDynamicArray) {
        println("${edge.vertex1} ${edge.vertex2} ${edge.weight}")
    }

    val adjacencyList = adjacencyMatrix.toAdjacencyList()
    println("\nПредставление в виде списка смежности:")
    for (vertexAdjacency in adjacencyList) {
        print("\n${vertexAdjacency.first} |")
        for (connectedVertex in vertexAdjacency.second) {
            print("(${connectedVertex.first},${connectedVertex.second}) ")
        }
    }

    println("\n\nОбход графа в глубину с вершины A:")
    adjacencyMatrix.dfs(startVertex = "A")

    println("\n\nОбход графа в ширину с вершины A:")
    adjacencyMatrix.bfs(startVertex = "A")

    // сортировка списка ребер по неубыванию
    val sortedEdges = timSort(edgesDynamicArray)
    val minSpanningTreeVertexesKeys = DynamicArray<String>()
    val minSpanningTreeEdges = DynamicArray<Edge>()

    for (index in 0 until sortedEdges.size) {
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
    }

    println("\n\nРебра минимального остовного дерева:\n")
    var summaryWeight = 0
    for (edge in minSpanningTreeEdges) {
        println("${edge.vertex1} ${edge.vertex2}")
        summaryWeight += edge.weight
    }

    println("\nСуммарный вес минимального остовного дерева: $summaryWeight")
}