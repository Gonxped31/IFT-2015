package Tp3;

import javax.lang.model.element.NestingKind;
import java.security.interfaces.EdECKey;
import java.util.*;
import java.util.stream.IntStream;

public class Tp3 {
    public static void main(String[] args) {

        List<Map<String, String[]>> reading = Reader.read("C:\\Users\\Samir\\Documents\\GitHub\\IFT-2015\\TP3\\Tp3\\testsTP3E19\\carte10.txt");
        Map<String, String[]> vertices = reading.get(0);
        Map<String, String[]> edges = reading.get(1);
        List<String> names = Reader.getNames();
        List<String[]> list = new ArrayList<>(edges.values());
        int numberOfVertices = vertices.size();
        Graph graph = new Graph(numberOfVertices);

        for (int i = 0; i < edges.size(); i++) {
            String startValue = list.get(i)[0];
            int startKey = Integer.parseInt(vertices.get(startValue)[0]);

            String endValue = list.get(i)[1];
            int endKey = Integer.parseInt(vertices.get(endValue)[0]);

            int weight = Integer.parseInt(list.get(i)[2]);
            graph.addEdge(new Vertex(startKey, startValue), new Vertex(endKey, endValue), weight, names.get(i));
        }
        List<Edge> result = primJarnik(graph);
        int mstWeight = totalWeight(result);
        Writer.write("C:\\Users\\Samir\\Documents\\GitHub\\IFT-2015\\TP3\\Tp3\\output.txt", result, vertices, mstWeight);
    }


    public static List<Edge> primJarnik(Graph graph) {
        int numVertices = graph.getNumVertices();
        boolean[] visited = new boolean[numVertices];
        List<Edge> minimumSpanningTree = new ArrayList<>();
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();

        int initialVertex = 0;
        visited[initialVertex] = true;
        priorityQueue.addAll(graph.getAdjacentEdges(initialVertex));

        while (!priorityQueue.isEmpty()) {
            Edge currentEdge = priorityQueue.poll();
            int destination = currentEdge.getDestination().getKey();

            if (visited[destination]) {
                continue;
            }

            visited[destination] = true;
            minimumSpanningTree.add(currentEdge);

            for (Edge edge : graph.getAdjacentEdges(destination)) {
                if (!visited[edge.getDestination().getKey()]) {
                    priorityQueue.add(edge);
                }
            }
        }

        return minimumSpanningTree;
    }

    public static int totalWeight(List<Edge> result){
        return result.stream()
                .mapToInt(value -> value.getWeight())
                .sum();
    }

}
