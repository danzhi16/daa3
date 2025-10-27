package org.example;

import java.util.*;

public class Kruskal {
    static class DSU {
        int[] parent, rank;
        long ops = 0;

        DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int find(int x) {
            ops++;
            if (parent[x] != x)
                parent[x] = find(parent[x]);
            return parent[x];
        }

        boolean union(int a, int b) {
            int ra = find(a);
            int rb = find(b);
            if (ra == rb) return false;
            if (rank[ra] < rank[rb]) parent[ra] = rb;
            else if (rank[ra] > rank[rb]) parent[rb] = ra;
            else {
                parent[rb] = ra;
                rank[ra]++;
            }
            ops++;
            return true;
        }
    }

    public static MSTResult run(Graph g) {
        long start = System.nanoTime();
        long ops = 0;
        DSU dsu = new DSU(g.V());

        List<Edge> sorted = new ArrayList<>(g.edges);
        Collections.sort(sorted);
        ops += sorted.size(); // comparisons estimate

        List<Edge> mst = new ArrayList<>();
        for (Edge e : sorted) {
            if (dsu.union(e.u, e.v)) {
                mst.add(e);
                ops++;
                if (mst.size() == g.V() - 1) break;
            } else ops++;
        }

        long end = System.nanoTime();
        MSTResult res = new MSTResult();
        res.mstEdges = mst;
        res.totalCost = mst.stream().mapToLong(ed -> ed.weight).sum();
        res.operationsCount = ops + dsu.ops;
        res.executionTimeMs = (end - start) / 1e6;
        return res;
    }
}
