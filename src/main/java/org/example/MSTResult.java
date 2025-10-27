package org.example;

import java.util.*;

public class MSTResult {
    public List<Edge> mstEdges = new ArrayList<>();
    public long totalCost;
    public long operationsCount;
    public double executionTimeMs;

    public MSTResult() {}

    public String toString() {
        return String.format("Cost=%d, Ops=%d, Time=%.3fms", totalCost, operationsCount, executionTimeMs);
    }
}
