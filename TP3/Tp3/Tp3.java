package Tp3;

import java.nio.file.SecureDirectoryStream;
import java.util.*;
import java.util.stream.IntStream;

public class Tp3 {
    public static void main(String[] args) {
        args = new String[2];
        args[0] = "C:\\Users\\Samir\\Documents\\GitHub\\IFT-2015\\TP3\\Tp3\\testsTP3E19\\carte1.txt";
        args[1] = "C:\\Users\\Samir\\Documents\\GitHub\\IFT-2015\\TP3\\Tp3\\output.txt";
        Reader.read(args[0]);
        Map<String, String> vertices = Reader.getVertices();
        Map<String, String[]> edges = Reader.getEdges();
        List<String> names = Reader.getNames();
        List<String[]> list = new ArrayList<>(edges.values());
        int numberOfVertices = vertices.size();
        Graph graph = new Graph(numberOfVertices);

        IntStream.range(0, edges.size()).forEach(i -> {
            String startValue = list.get(i)[0];
            String endValue = list.get(i)[1];
            graph.addEdge(new Vertex(Integer.parseInt(vertices.get(startValue)),startValue), new Vertex(Integer.parseInt(vertices.get(endValue)),
                    endValue), Integer.parseInt(list.get(i)[2]), names.get(i));
        });

        IntStream.range(0, edges.size()).forEach(i -> {
            String startValue = list.get(i)[0];
            String endValue = list.get(i)[1];
            graph.addReversedEdges(new Vertex(Integer.parseInt(vertices.get(startValue)), startValue), new Vertex(Integer.parseInt(vertices.get(endValue)),
                    endValue), Integer.parseInt(list.get(i)[2]), names.get(i));
        });

        List<Edge> result = orderEdges(primJarnik(graph));
        int mstWeight = totalWeight(result);
        Writer.write(args[1], result, vertices, mstWeight);
    }

    // Complexity : O((|V| + |E|) log |E|)
    public static List<Edge> primJarnik(Graph graph) {
        int numVertices = graph.getNumVertices();
        boolean[] visited = new boolean[numVertices];
        List<Edge> mst = new ArrayList<>();
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();

        int initialVertex = 0;
        visited[initialVertex] = true;
        priorityQueue.addAll(graph.getAdjacentEdges(initialVertex)); //O(|E|)

        while (!priorityQueue.isEmpty()) {
            Edge currentEdge = priorityQueue.poll(); //O(|E| log |E|)
            int destination = currentEdge.getDestination().getKey();

            if (visited[destination]) {
                continue;
            }

            visited[destination] = true;
            mst.add(currentEdge);

            graph.getAdjacentEdges(destination).forEach(edge -> { //O(|V|)
                System.out.println(edge.toString());
                if (!visited[edge.getDestination().getKey()]) {
                    priorityQueue.add(edge); //O(|E| log |E|)
                }
            });
        }

        return mst;
    }

    public static List<Edge> orderEdges(List<Edge> edgeList){
        Map<String, Map<String, Edge>> map = new TreeMap<>();
        List<Edge> orderedEdges = new ArrayList<Edge>();

        edgeList.forEach(edge -> {
            String key = edge.getSource().getValue();
            if (map.containsKey(key)){
                map.get(key).put(edge.getDestination().getValue(), edge);
            } else {
                Map<String, Edge> tmpMap = new TreeMap<>();
                tmpMap.put(edge.getDestination().getValue(), edge);
                map.put(edge.getSource().getValue(), tmpMap);
            }
        });

        List<Map<String, Edge>> list = new ArrayList<>(map.values());
        list.forEach(elem -> orderedEdges.addAll(elem.values()));

        return orderedEdges;
    }

    public static int totalWeight(List<Edge> result){
        return result.stream()
                .mapToInt(value -> value.getWeight())
                .sum();
    }

}
