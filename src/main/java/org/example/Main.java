package org.example;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import com.fasterxml.jackson.databind.*;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java -jar mst-assignment.jar <input.json> <output.json>");
            return;
        }

        String inputPath = args[0];
        String outputPath = args[1];

        ObjectMapper mapper = new ObjectMapper();
        Map<?, ?> root = mapper.readValue(new File(inputPath), Map.class);
        List<Map<String, Object>> graphs = (List<Map<String, Object>>) root.get("graphs");

        List<Map<String, Object>> results = new ArrayList<>();

        for (Map<String, Object> gObj : graphs) {
            Graph g = new Graph();
            List<String> nodes = (List<String>) gObj.get("nodes");
            List<Map<String, Object>> edges = (List<Map<String, Object>>) gObj.get("edges");
            for (String node : nodes) g.addNode(node);
            for (Map<String, Object> e : edges)
                g.addEdge((String) e.get("from"), (String) e.get("to"),
                        ((Number) e.get("weight")).intValue());

            MSTResult prim = Prim.run(g);
            MSTResult kruskal = Kruskal.run(g);

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("graph_id", gObj.get("id"));
            result.put("input_stats", Map.of("vertices", g.V(), "edges", g.E()));
            result.put("prim", toMap(prim, g));
            result.put("kruskal", toMap(kruskal, g));
            results.add(result);
        }

        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(outputPath), Map.of("results", results));

        System.out.println("✅ Output written to " + outputPath);
    }

    private static Map<String, Object> toMap(MSTResult r, Graph g) {
        List<Map<String, Object>> edges = new ArrayList<>();
        for (Edge e : r.mstEdges) {
            edges.add(Map.of(
                    "from", g.nodes.get(e.u),
                    "to", g.nodes.get(e.v),
                    "weight", e.weight
            ));
        }
        return Map.of(
                "mst_edges", edges,
                "total_cost", r.totalCost,
                "operations_count", r.operationsCount,
                "execution_time_ms", r.executionTimeMs
        );
    }
}
