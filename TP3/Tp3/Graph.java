package Tp3;

import java.util.ArrayList;
import java.util.List;

class Graph {
    private int numVertices;
    private List<List<Edge>> adjacencyList;

    public Graph(int numVertices) {
        this.numVertices = numVertices;
        adjacencyList = new ArrayList<>();

        for (int i = 0; i < numVertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(Vertex source, Vertex destination, int weight, String name) {
        Edge edge = new Edge(name, source, destination, weight);
        adjacencyList.get(source.getKey()).add(edge);

        // For a non-oriented graph, add the edge in the opposite direction as well
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