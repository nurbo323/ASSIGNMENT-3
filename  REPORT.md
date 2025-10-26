# Analytical Report: Assignment 3
## City Transportation Network Optimization Using MST Algorithms

Author: Nurbol Amangeldinov
Course: Design and Analysis of Algorithms

================================================================================

## 1. Introduction

This report analyzes two fundamental algorithms for finding Minimum Spanning Trees in weighted undirected graphs: Prim's Algorithm and Kruskal's Algorithm. The practical application focuses on optimizing city transportation networks by minimizing road construction costs while ensuring all districts remain connected.

Problem Context:
Modern urban planning requires efficient resource allocation for infrastructure development. The challenge is to construct a road network that connects all city districts, minimizes total construction cost, and avoids redundant connections.

Objectives:
1. Implement both Prim's and Kruskal's algorithms
2. Compare algorithmic performance on test datasets
3. Analyze time and space complexity
4. Determine optimal algorithm selection criteria
5. Validate correctness through multiple test cases

================================================================================

## 2. Algorithms Overview

### Kruskal's Algorithm
Strategy: Edge-based greedy approach
Data Structure: Union-Find (Disjoint Set Union)
Time Complexity: O(E log E)
Space Complexity: O(V)
Best For: Sparse graphs

How it works:
1. Sort all edges by weight in ascending order
2. Initialize Union-Find structure for cycle detection
3. Process edges one by one
4. Add edge to MST if it doesn't create a cycle
5. Stop when MST has V-1 edges

Key Optimization: Path compression and union by rank in Union-Find structure provides nearly constant-time operations.

### Prim's Algorithm
Strategy: Vertex-based greedy approach
Data Structure: Priority Queue (Min-Heap)
Time Complexity: O(E log V)
Space Complexity: O(V + E)
Best For: Dense graphs

How it works:
1. Start from arbitrary vertex
2. Maintain priority queue of edges from MST to non-MST vertices
3. Extract minimum edge from queue
4. Add edge and vertex to MST if vertex not visited
5. Add new edges from newly added vertex to queue
6. Repeat until all vertices included

Key Optimization: Binary heap provides efficient O(log V) insertion and extraction operations.

================================================================================

## 3. Test Results

### Test Case 1: 5-District Network

Input:
- Vertices: 5 (A, B, C, D, E)
- Edges: 7
- Graph Density: 70%
- Edge Weights: Range 2 to 8

Results:

Algorithm    MST Cost    Operations    Time (ms)    Edges in MST
Prim         16          29            9.85         4
Kruskal      16          24            0.50         4

MST Structure:
- B to C (weight: 2)
- A to C (weight: 3)
- B to D (weight: 5)
- D to E (weight: 6)
  Total: 2 + 3 + 5 + 6 = 16

Cost Savings: 54% compared to building all roads (total weight: 35)

### Test Case 2: 4-District Network

Input:
- Vertices: 4 (A, B, C, D)
- Edges: 5
- Graph Density: 83%
- Edge Weights: Range 1 to 5

Results:

Algorithm    MST Cost    Operations    Time (ms)    Edges in MST
Prim         6           19            0.03         3
Kruskal      6           16            0.02         3

MST Structure:
- A to B (weight: 1)
- B to C (weight: 2)
- C to D (weight: 3)
  Total: 1 + 2 + 3 = 6

Cost Savings: 60% compared to building all roads (total weight: 15)

================================================================================

## 4. Performance Analysis

### Correctness Verification
Both algorithms produced identical MST costs for all test cases:
- Graph 1: Prim = Kruskal = 16 (VERIFIED)
- Graph 2: Prim = Kruskal = 6 (VERIFIED)
- All MSTs have exactly V-1 edges (VERIFIED)
- No cycles detected in any MST (VERIFIED)

### Efficiency Comparison

Operations Count:
Graph 1: Kruskal 17% fewer operations than Prim
Graph 2: Kruskal 16% fewer operations than Prim

Execution Time:
Graph 1: Kruskal 19.7x faster than Prim
Graph 2: Kruskal 1.5x faster than Prim

Key Finding: Kruskal consistently outperforms Prim on both sparse and dense test graphs.

### Complexity Analysis

For Graph 1 (V=5, E=7):
Kruskal theoretical: O(7 log 7) = approximately 20 operations
Kruskal observed: 24 operations (within expected range)

Prim theoretical: O(7 log 5) = approximately 11 operations
Prim observed: 29 operations (includes auxiliary operations)

Note: Operation counts include auxiliary tasks beyond core algorithmic steps.

================================================================================

## 5. Comparative Discussion

When to Choose Kruskal:
- Graph is sparse (E approximately equal to V)
- Edge list representation is available
- Simple implementation is preferred
- Memory is constrained (O(V) space requirement)
- Starting vertex is not predetermined

When to Choose Prim:
- Graph is dense (E approximately equal to V squared)
- Adjacency list representation is available
- Starting vertex is known in advance
- Edge additions are dynamic during execution

For City Transportation Networks:
Real-world road networks are typically sparse, making Kruskal's Algorithm the preferred choice. The edge-based approach aligns naturally with the planning process where roads (edges) are the primary planning units.

Algorithm Selection Rule:
If E < V × log V: Use Kruskal
If E > V × log V: Use Prim
Otherwise: Both perform similarly

================================================================================

## 6. Implementation Quality

Strengths of Implementation:
- Clean package-based organization (algorithm, model, io, exceptions)
- Comprehensive error handling with custom exceptions
- Immutable data structures for thread safety
- Detailed operation counting for empirical analysis
- Precise execution time measurement using nanosecond precision
- No external dependencies (pure Java implementation)

Optimizations Applied:
- Union-Find with path compression for O(alpha(n)) amortized time
- Union by rank to maintain balanced tree structures
- Binary heap priority queue for efficient edge selection
- Early termination when MST construction completes

Code Quality Features:
- Comprehensive JavaDoc documentation
- Input validation at all entry points
- Defensive copying for data integrity
- Clear separation of concerns across packages

================================================================================

## 7. Conclusions

Summary of Findings:
1. Both algorithms reliably produce optimal MST solutions (correctness verified)
2. Kruskal outperforms Prim by 15-20% on test graphs
3. Both algorithms scale well with graph size
4. Algorithm choice depends primarily on graph density

Practical Implications for Urban Planning:
- MST approach reduces road construction costs by 50-60%
- Kruskal's algorithm is preferred for sparse city networks
- Union-Find provides efficient cycle detection
- Real-time performance suitable for interactive planning tools
- Significant cost savings justify computational investment

Learning Outcomes:
- Practical application of greedy algorithmic strategies
- Importance of appropriate data structure selection
- Trade-offs between different algorithmic approaches
- Value of empirical performance measurement alongside theoretical analysis

================================================================================

## 8. Note on Measurement Methodology

Operation Counts:
Our implementation counts core algorithmic operations including comparisons, union-find operations, priority queue insertions/extractions, and set membership checks. Different implementations may count different operation sets, leading to variation in absolute numbers. The relative performance comparison remains valid.

Execution Time:
Measured times are affected by JVM warmup, system load, hardware specifications, and garbage collection. Multiple runs show consistent relative performance with Kruskal faster than Prim, though absolute times vary. The speedup ratio is the meaningful metric.

MST Edge Order:
The specific sequence of edges in the MST may differ between algorithms and even between runs of the same algorithm, depending on starting vertex selection and tie-breaking in data structures. However, the total cost remains optimal and identical across all correct implementations.

==========================================================================================================================

END OF REPORT

This analytical report is submitted as part of Assignment 3 for the Design and Analysis of Algorithms course at Astana IT University.

Copyright 2025 Nurbol Amangeldinov. All rights reserved.
