package graph

class AdjacencyListGraph<T>(
    override val direction: GraphDirection = GraphDirection.UNDIRECTED
) : Graph<T> {
    private val vertices = linkedMapOf<T, Vertex<T>>()
    private val adjacencyList = linkedMapOf<Vertex<T>, MutableList<Edge<T>>>()

    override fun addVertex(value: T): Vertex<T> {
        vertices[value]?.let { return it }

        val vertex = Vertex(value)
        vertices[value] = vertex
        adjacencyList[vertex] = mutableListOf()
        return vertex
    }

    override fun addEdge(source: T, target: T, weight: Int) {
        val sourceVertex = addVertex(source)
        val targetVertex = addVertex(target)

        adjacencyList[sourceVertex]?.add(Edge(sourceVertex, targetVertex, weight))

        if (direction == GraphDirection.UNDIRECTED) {
            adjacencyList[targetVertex]?.add(Edge(targetVertex, sourceVertex, weight))
        }
    }

    override fun getVertices(): List<Vertex<T>> = vertices.values.toList()

    override fun getEdges(): List<Edge<T>> = adjacencyList.values.flatten()

    override fun getVertex(value: T): Vertex<T>? = vertices[value]

    override fun getNeighbours(vertex: Vertex<T>): List<Vertex<T>> =
        getEdgesFrom(vertex).map { edge -> edge.destination }

    override fun getEdgesFrom(vertex: Vertex<T>): List<Edge<T>> =
        adjacencyList[vertex]?.toList() ?: emptyList()
}
