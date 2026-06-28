package graph

interface Graph<T> {
    val direction: GraphDirection
    fun addVertex(value:T):Vertex<T>
    fun addEdge(source:T, target:T, weight: Int = 1)
    fun getVertices():List<Vertex<T>>
    fun getEdges():List<Edge<T>>
    fun getVertex(value:T):Vertex<T>?
    fun getNeighbours(vertex: Vertex<T>):List<Vertex<T>>
    fun getEdgesFrom(vertex: Vertex<T>):List<Edge<T>>
}