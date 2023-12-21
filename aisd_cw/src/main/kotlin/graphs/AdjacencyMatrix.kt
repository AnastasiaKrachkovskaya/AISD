package graphs

import dynamicarray.DynamicArray
import java.io.File

class AdjacencyMatrix(
    private val vertexes: DynamicArray<String>,
    edges: Array<Triple<String, String, Int>> = emptyArray(),
) {

    val _matrix: Array<IntArray>

    init {
        val vertexesAmount = vertexes.size
        _matrix = Array(vertexesAmount) { IntArray(vertexesAmount) { 0 } }

        edges.forEach { (v1, v2, weight) ->
            _matrix[v1.toIndex()][v2.toIndex()] = weight
        }
    }

    fun String.toIndex(): Int {
        return vertexes.indexOf(this)
    }

    fun createEdgeBetween(v1: String, v2: String, weight: Int = 1) {
        _matrix[v1.toIndex()][v2.toIndex()] = weight
    }

    fun isEdgeBetween(v1: String, v2: String): Boolean {
        return _matrix[v1.toIndex()][v2.toIndex()] != 0
    }

    /**
     * @return список ребер
     */
    fun toEdgesDynamicArray(): DynamicArray<Edge> {
        val edgesDynamicArray = DynamicArray<Edge>()
        _matrix.forEachIndexed { rowIndex, intArray ->
            intArray.forEachIndexed WeightsForEach@{ columnIndex, weight ->
                if (weight == 0) return@WeightsForEach
                edgesDynamicArray.add(
                    Edge(
                        vertex1 = vertexes[rowIndex],
                        vertex2 = vertexes[columnIndex],
                        weight = weight,
                    )
                )
            }
        }
        return edgesDynamicArray
    }

    /**
     * @return список смежности
     */
    fun toAdjacencyList(): DynamicArray<Pair<String, DynamicArray<Pair<String, Int>>>> {
        val adjacencyList = DynamicArray<Pair<String, DynamicArray<Pair<String, Int>>>>()
        _matrix.forEachIndexed { rowIndex, intArray ->
            intArray.forEachIndexed WeightsForEach@{ columnIndex, weight ->
                if (weight == 0) return@WeightsForEach
                try {
                    adjacencyList[rowIndex].second.add(vertexes[columnIndex] to weight)
                } catch (_: Exception) {
                    adjacencyList.add(vertexes[rowIndex] to DynamicArray())
                    adjacencyList[rowIndex].second.add(vertexes[columnIndex] to weight)
                }
            }
        }
        return adjacencyList
    }

    fun getAllVertexKeys() = vertexes

    companion object {
        fun createFromFile(filePath: String): AdjacencyMatrix {
            val file = File(filePath)
            val inputLines = file.readLines()
            val vertexKeys = DynamicArray(inputLines.first().split(" ").toTypedArray())
            val adjacencyMatrixLines = inputLines.drop(1)

            val adjacencyMatrix = AdjacencyMatrix(vertexes = vertexKeys)

            adjacencyMatrixLines.forEachIndexed { rowIndex, matrixLine ->
                matrixLine.split(" ").forEachIndexed WeightsForEach@{ columnIndex, weightValueStr ->
                    val weight = weightValueStr.toInt()
                    // 0 значит, что ребра нет
                    if (weight == 0) return@WeightsForEach
                    val v1 = vertexKeys[rowIndex]
                    val v2 = vertexKeys[columnIndex]
                    adjacencyMatrix.createEdgeBetween(v1, v2, weight)
                }
            }

            return adjacencyMatrix
        }
    }
}