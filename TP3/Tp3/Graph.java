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

    public void addEdge(int source, int destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        adjacencyList.get(source).add(edge);

        // For a non-oriented graph, add the edge in the opposite direction as well
        Edge reverseEdge = new Edge(destination, source, weight);
        adjacencyList.get(destination).add(reverseEdge);
    }

    public List<Edge> getAdjacentEdges(int vertex) {
        return adjacencyList.get(vertex);
    }

    public int getNumVertices() {
        return numVertices;
    }
}