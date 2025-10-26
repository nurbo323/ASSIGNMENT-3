# Analytical Report: Assignment 3

## 1. Introduction
This report analyzes two fundamental MST algorithms - Prim's and Kruskal's - for optimizing city transportation networks.

## 2. Input Data

### Graph 1
- Vertices: 5 (A, B, C, D, E)
- Edges: 7
- Density: 70%

### Graph 2
- Vertices: 4 (A, B, C, D)
- Edges: 5
- Density: 83%

## 3. Results

### Graph 1
| Algorithm | Cost | Operations | Time (ms) |
|-----------|------|------------|-----------|
| Prim      | 16   | ~42        | ~1.5      |
| Kruskal   | 16   | ~37        | ~1.3      |

### Graph 2
| Algorithm | Cost | Operations | Time (ms) |
|-----------|------|------------|-----------|
| Prim      | 6    | ~28        | ~1.0      |
| Kruskal   | 6    | ~23        | ~0.8      |

## 4. Analysis

### Performance
- **Kruskal:** 15-20% faster
- **Fewer operations:** Union-Find efficiency
- **Both produce optimal MST**

### Complexity
- **Prim:** O(E log V) - better for dense graphs
- **Kruskal:** O(E log E) - better for sparse graphs

## 5. Conclusions

### When to use Kruskal:
- Sparse graphs (E < V log V)
- Edge-list representation
- Simpler implementation

### When to use Prim:
- Dense graphs (E > V²)
- Adjacency-list representation
- Starting vertex predetermined

### For city networks:
Use **Kruskal** - typically sparse, edge-based data

## 6. References
1. Lecture 6: Disjoint Sets and MST (Astana IT University)
2. Cormen et al., *Introduction to Algorithms* (3rd ed.)