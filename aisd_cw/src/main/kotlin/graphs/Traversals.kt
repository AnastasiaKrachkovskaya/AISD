package graphs

import doubleconnectedList.DoubleConnectedList
import dynamicarray.DynamicArray

fun AdjacencyMatrix.dfs(startVertex: String) {
    val vertexKeys = this.getAllVertexKeys()
    val visited = DynamicArray(BooleanArray(size = vertexKeys.size).toTypedArray())

    fun recursiveDfs(vertex: String) {
        print("$vertex ")
        val vertexIndex = vertex.toIndex()
        visited[vertexIndex] = true
        _matrix[vertexIndex].forEachIndexed { columnIndex, weight ->
            if (weight == 0) return@forEachIndexed
            if (!visited[columnIndex]) {
                recursiveDfs(vertex = vertexKeys[columnIndex])
            }
        }
    }

    recursiveDfs(vertex = startVertex)
}

fun AdjacencyMatrix.bfs(startVertex: String) {
    val vertexKeys = this.getAllVertexKeys()
    val visited = DynamicArray(BooleanArray(size = vertexKeys.size).toTypedArray()).apply {
        this[startVertex.toIndex()] = true
    }
    val queue = DoubleConnectedList<String>().apply {
        add(startVertex)
    }

    while (!queue.isEmpty()) {
        val current = queue.popFirst()
        print("$current ")
        val vertexIndex = current.toIndex()
        _matrix[vertexIndex].forEachIndexed { columnIndex, weight ->
            if (weight == 0) return@forEachIndexed
            if (!visited[columnIndex]) {
                queue.add(vertexKeys[columnIndex])
                visited[columnIndex] = true
            }
        }
    }
}
