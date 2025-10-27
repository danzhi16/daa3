package org.example;

public class Edge implements Comparable<Edge> {
    public int u, v;
    public int weight;

    public Edge(int u, int v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge o) {
        return Integer.compare(this.weight, o.weight);
    }

    @Override
    public String toString() {
        return "(" + u + " - " + v + ", w=" + weight + ")";
    }
}
