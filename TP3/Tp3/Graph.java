import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

class Graph {
    private int numVertices;
    private List<List<Edge>> adjacencyList;

    public Graph(int numVertices) {
        this.numVertices = numVertices;
        adjacencyList = new ArrayList<>();
        IntStream.range(0, numVertices).forEach(i -> adjacencyList.add(new ArrayList<>()));
    }

    public void addEdge(Vertex source, Vertex destination, int weight, String name) {
        Edge edge = new Edge(name, source, destination, weight);
        adjacencyList.get(source.getKey()).add(edge);

        // Adding the reversed edge because the graph is not oriented.
        Edge reverseEdge = new Edge(name, destination, source, weight);
        adjacencyList.get(destination.getKey()).add(reverseEdge);
    }

    public List<Edge> getAdjacentEdges(int vertex) {
        return adjacencyList.get(vertex);
    }

    public int getNumVertices() {
        return numVertices;
    }
}