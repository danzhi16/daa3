# MST Assignment

Minimal Spanning Tree implementations using Prim’s and Kruskal’s algorithms on Java \(Maven\).

## Build and Run

- Build: `mvn clean package`
- Run: `java -jar target/mst-assignment-1.0-SNAPSHOT-shaded.jar input.json output.json`
- Input: `input.json`
- Output: `output.json`

Main entry: `org.example.Main`. See `pom.xml` for build configuration.

## Input and Output

- Input format \(per graph\): nodes and weighted edges in `input.json`.
- Output: For each graph, `output.json` includes MST edges, total cost, operation count, and execution time for both algorithms.

## 1. Summary of Input Data and Algorithm Results

Numbers below are taken from the current `output.json`.

| Graph | V | E  | Prim cost | Prim ops | Prim time \(ms\) | Kruskal cost | Kruskal ops | Kruskal time \(ms\) |
|------:|--:|---:|----------:|---------:|-----------------:|-------------:|------------:|--------------------:|
| 1     | 5 |  5 | 11        | 10       | 0.595875         | 11           | 28          | 0.110709            |
| 2     | 4 |  5 | 9         | 8        | 0.010292         | 9            | 19          | 0.007292            |
| 3     | 6 |  6 | 15        | 11       | 0.010541         | 15           | 28          | 0.006083            |
| 4     |10 | 12 | 31        | 23       | 0.014042         | 31           | 70          | 0.010041            |
| 5     |12 | 12 | 52        | 23       | 0.018291         | 52           | 63          | 0.009541            |
| 6     |20 | 24 | 76        | 44       | 0.046416         | 76           | 127         | 0.017667            |

Observations:
- Both algorithms produce identical MST total costs for all cases.
- Kruskal shows lower measured execution time in these runs but higher operation counts due to DSU `find/union` work and edge sorting.
- Prim maintains consistently lower operation counts here.

## 2. Prim vs. Kruskal: Efficiency and Performance

Asymptotics \(with this implementation\):
- Prim \(binary heap / adjacency list\): `O(E log V)`
- Kruskal \(sort edges + DSU\): `O(E log E)` \(≈ `O(E log V)`\)

Practical considerations:
- Prim processes via adjacency and a priority queue; good locality on sparse graphs.
- Kruskal sorts the whole edge list once and uses DSU; sensitive to `E` and sorting cost.
- Memory:
    - Prim uses adjacency lists and a PQ of candidate edges.
    - Kruskal stores the edge list and DSU arrays; no adjacency needed.

Empirical \(from `output.json`\):
- Kruskal is faster in wall-clock time on these datasets but performs more logical operations.
- Prim performs less logical work and scales smoothly with increasing `V`, `E`.

## 3. Conclusions: When to Prefer Each

- Prefer Prim when:
    - Graphs are sparse \(`E` close to `V`\) and represented as adjacency lists.
    - You repeatedly expand from a start vertex or need incremental MST growth.
    - Minimizing per-step operations in a streaming or online setting.

- Prefer Kruskal when:
    - You already have an edge list and sorting is cheap \(or reused\).
    - Graphs are disconnected and you want a minimum spanning forest.
    - Parallel or external sorting can be leveraged for very large `E`.

- Dense graphs:
    - Theoretical variants of Prim with advanced heaps can outperform, but this code uses a binary heap. In practice here, both are `O(E log V)` and close; choice depends on representation and constants.

## 4. References

- T. Cormen, C. Leiserson, R. Rivest, C. Stein. Introduction to Algorithms \(CLRS\), MST chapters.
- R. Prim. Shortest connection networks and some generalizations.
- J. B. Kruskal. On the shortest spanning subtree of a graph.
- Wikipedia: “Minimum spanning tree,” “Prim’s algorithm,” “Kruskal’s algorithm.”
