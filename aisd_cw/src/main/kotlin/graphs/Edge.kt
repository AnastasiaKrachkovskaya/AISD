package graphs

data class Edge(
    val vertex1: String,
    val vertex2: String,
    val weight: Int,
) : Comparable<Edge> {
    override fun compareTo(other: Edge): Int {
        return when {
            this.weight == other.weight -> 0
            this.weight < other.weight -> -1
            else -> 1
        }
    }
}