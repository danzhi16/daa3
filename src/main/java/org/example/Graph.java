package org.example;

import java.util.*;

public class Graph {
    public List<String> nodes = new ArrayList<>();
    public List<Edge> edges = new ArrayList<>();
    public Map<String, Integer> indexMap = new HashMap<>();

    public void addNode(String name) {
        if (!indexMap.containsKey(name)) {
            indexMap.put(name, nodes.size());
            nodes.add(name);
        }
    }

    public void addEdge(String from, String to, int weight) {
        addNode(from);
        addNode(to);
        int u = indexMap.get(from);
        int v = indexMap.get(to);
        edges.add(new Edge(u, v, weight));
    }

    public int V() {
        return nodes.size();
    }

    public int E() {
        return edges.size();
    }
}
