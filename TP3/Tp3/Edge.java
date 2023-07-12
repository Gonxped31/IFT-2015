package Tp3;

class Edge implements Comparable<Edge> {
    private Vertex source;
    private Vertex destination;
    private int weight;
    private String name;

    public Edge(String name, Vertex source, Vertex destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.name = name;
    }

    public Vertex getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    public String toString() {
        return name + "\t" + source.getValue() + "\t" + destination.getValue() + "\t" + weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(weight, other.weight);
    }
}