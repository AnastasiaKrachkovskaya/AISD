package graphs

class AdjacencyMatrix(
    private val vertexAmount: Int,
    edges: Array<Pair<Int, Int>> = emptyArray(),
) {
    private val _matrix = Array(vertexAmount) { IntArray(vertexAmount) }

    init {
        edges.forEach {
            _matrix[it.first][it.second] = 1
            _matrix[it.second][it.first] = 1
        }
    }

    fun createEdgeBetween(v1: Int, v2: Int) {
        _matrix[v1][v2] = 1
        _matrix[v2][v1] = 1
    }

    fun isEdgeBetween(v1: Int, v2: Int): Boolean {
        return _matrix[v1][v2] == 1
    }
}