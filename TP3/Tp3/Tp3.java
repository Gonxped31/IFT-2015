package Tp3;

import java.security.interfaces.EdECKey;
import java.util.*;
import java.util.stream.IntStream;

public class Tp3 {
    public static void main(String[] args) {

        List<Map<String, String[]>> reading = Reader.read("C:\\Users\\Samir\\Documents\\GitHub\\IFT-2015\\TP3\\Tp3\\testsTP3E19\\carte10.txt");
        Map<String, String[]> vertices = reading.get(0);
        Map<String, String[]> edges = reading.get(1);
        System.out.println(vertices);
        System.out.println(edges);
        List<String[]> list = new ArrayList<>(edges.values());
        int numberOfVertices = vertices.size();
        Graph graph = new Graph(numberOfVertices);

        for (int i = 0; i < edges.size(); i++) {
            int start = Integer.parseInt(vertices.get(list.get(i)[0])[0]);
            int end = Integer.parseInt(vertices.get(list.get(i)[1])[0]);
            int weight = Integer.parseInt(list.get(i)[2]);
            graph.addEdge(start, end, weight);
        }

        primJarnik(graph);

        /*List<Vertex> vertices = new ArrayList<>();
        vertices.add(new Vertex(0, "SFO"));
        vertices.add(new Vertex(1, "LAX"));
        vertices.add(new Vertex(2, "DFW"));
        vertices.add(new Vertex(3, "ORD"));
        vertices.add(new Vertex(4, "BOS"));
        vertices.add(new Vertex(5, "PVD"));
        vertices.add(new Vertex(6, "JFK"));
        vertices.add(new Vertex(7, "BWI"));
        vertices.add(new Vertex(8, "MIA"));
        Graph graph = new Graph(vertices.size());
        graph.addEdge(0, 4, 2704);
        graph.addEdge(0, 3, 1846);
        graph.addEdge(0, 2, 1464);
        graph.addEdge(0, 1, 337);

        graph.addEdge(4, 3, 867);
        graph.addEdge(4, 6, 187);
        graph.addEdge(4, 8, 1258);

        graph.addEdge(3, 5, 849);
        graph.addEdge(3, 6, 740);
        graph.addEdge(3, 7, 621);
        graph.addEdge(3, 2, 802);

        graph.addEdge(2, 6, 1391);
        graph.addEdge(2, 1, 1235);
        graph.addEdge(2, 8, 1121);

        graph.addEdge(1, 8, 2342);

        graph.addEdge(6, 7, 184);
        graph.addEdge(6, 8, 1090);
        graph.addEdge(6, 5, 144);

        graph.addEdge(8, 7, 946);

        List<Edge> list = primJarnik(graph);
        System.out.println("Minimum Spanning Tree:");
        for (Edge edge : list) {
            System.out.println(edge.getSource() + " - " + edge.getDestination() + ", weight: " + edge.getWeight());*/
    }


    public static List<Edge> primJarnik(Graph graph) {
        int numVertices = graph.getNumVertices();
        boolean[] visited = new boolean[numVertices];
        List<Edge> minimumSpanningTree = new ArrayList<>();
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();
        int totalWeight = 0;

        // Start with vertex 0 as the initial vertex
        int initialVertex = 0;
        visited[initialVertex] = true;
        priorityQueue.addAll(graph.getAdjacentEdges(initialVertex));

        while (!priorityQueue.isEmpty()) {
            Edge currentEdge = priorityQueue.poll();
            int destination = currentEdge.getDestination();

            if (visited[destination]) {
                continue;
            }

            visited[destination] = true;
            minimumSpanningTree.add(currentEdge);
            totalWeight += currentEdge.getWeight();

            for (Edge edge : graph.getAdjacentEdges(destination)) {
                if (!visited[edge.getDestination()]) {
                    priorityQueue.add(edge);
                }
            }
        }

        System.out.println(totalWeight);
        return minimumSpanningTree;
    }

}
