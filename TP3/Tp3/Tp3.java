import java.util.*;
import java.util.stream.IntStream;

public class Tp3 {
    public static void main(String[] args) {
        List<Map<String, String[]>> reading = Reader.read(args[0]);
        Map<String, String[]> vertices = reading.get(0);
        Map<String, String[]> edges = reading.get(1);
        List<String> names = Reader.getNames();
        List<String[]> list = new ArrayList<>(edges.values());
        int numberOfVertices = vertices.size();
        Graph graph = new Graph(numberOfVertices);

        IntStream.range(0, edges.size()).forEach(i -> {
            String startValue = list.get(i)[0];
            int startKey = Integer.parseInt(vertices.get(startValue)[0]);

            String endValue = list.get(i)[1];
            int endKey = Integer.parseInt(vertices.get(endValue)[0]);

            int weight = Integer.parseInt(list.get(i)[2]);
            graph.addEdge(new Vertex(startKey, startValue), new Vertex(endKey, endValue), weight, names.get(i));
        });

        List<Edge> result = primJarnik(graph);
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
                if (!visited[edge.getDestination().getKey()]) {
                    priorityQueue.add(edge); //O(|E| log |E|)
                }
            });
        }

        return mst;
    }

    public static int totalWeight(List<Edge> result){
        return result.stream()
                .mapToInt(value -> value.getWeight())
                .sum();
    }

}
