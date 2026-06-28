package graph

class AdjacencyMatrixGraph<T> (override val direction: GraphDirection = GraphDirection.UNDIRECTED) : Graph<T>{
    private val vertices =  linkedMapOf<T,Vertex<T>>()
    private val matrix = mutableListOf<MutableList<Int?>>()
    override fun addVertex(value: T): Vertex<T> {
     vertices[value]?.let { return it }
        val vertex:Vertex<T> = Vertex<T>(value)
        vertices[value] = vertex
        matrix.forEach { row -> row.add(null) }
        matrix.add(MutableList(vertices.size){null})
        return vertex
    }

    override fun addEdge(source: T, target: T, weight: Int) {
        val fromVertex = addVertex(source)
        val toVertex = addVertex(target)
        val fromindex = indexOf(fromVertex)
        val toindex = indexOf(toVertex)
        matrix[fromindex][toindex] = weight
        if (direction == GraphDirection.UNDIRECTED) {
            matrix[toindex][fromindex] = weight
        }
    }

    override fun getVertices(): List<Vertex<T>> = vertices.values.toList()
    override fun getEdges(): List<Edge<T>> {
        val vertices = getVertices()
        val result = mutableListOf<Edge<T>>()
        for (fromIndex in  matrix.indices) {
            for (toIndex in  matrix[fromIndex].indices) {
                val weight = matrix[fromIndex][toIndex] ?: continue
//                if (direction == GraphDirection.UNDIRECTED && toIndex < fromIndex) {
//                    continue
//                }
                result.add(Edge(vertices[fromIndex], vertices[toIndex],weight))
            }
        }
        return result
    }

    override fun getVertex(value: T): Vertex<T>? = vertices[value]

    override fun getNeighbours(vertex: Vertex<T>): List<Vertex<T>> = getEdgesFrom(vertex).map { edge -> edge.destination }

    override fun getEdgesFrom(vertex: Vertex<T>): List<Edge<T>> {
        val index = vertices.values.indexOf(vertex)
        if(index == -1) { return emptyList() }
        val allVertices = getVertices()
        return matrix[index].mapIndexedNotNull { toIndex, weight ->
            weight?.let { weight ->
                Edge(
                    source = vertex,
                    destination = allVertices[toIndex],
                    weight = weight
                )
            }
        }
    }
    private fun indexOf(vertex: Vertex<T>?): Int {
        val index = vertices.values.indexOf(vertex)
        require(index >= 0) { "Vertex does not exist in this graph." }
        return index
    }
}