package org.example;

import java.util.*;

public class Prim {
    public static MSTResult run(Graph g) {
        long start = System.nanoTime();
        long ops = 0;

        int n = g.V();
        boolean[] inMST = new boolean[n];
        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (Edge e : g.edges) {
            adj.get(e.u).add(e);
            adj.get(e.v).add(new Edge(e.v, e.u, e.weight));
        }

        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        inMST[0] = true;
        pq.addAll(adj.get(0));
        ops += adj.get(0).size();

        List<Edge> mst = new ArrayList<>();
        while (!pq.isEmpty() && mst.size() < n - 1) {
            Edge e = pq.poll();
            ops++;
            if (inMST[e.v]) continue;
            inMST[e.v] = true;
            mst.add(e);
            for (Edge ne : adj.get(e.v)) {
                if (!inMST[ne.v]) {
                    pq.offer(ne);
                    ops++;
                }
            }
        }

        long end = System.nanoTime();
        MSTResult res = new MSTResult();
        res.mstEdges = mst;
        res.totalCost = mst.stream().mapToLong(x -> x.weight).sum();
        res.operationsCount = ops;
        res.executionTimeMs = (end - start) / 1e6;
        return res;
    }
}
