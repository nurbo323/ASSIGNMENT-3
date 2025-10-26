# Assignment 3: City Transportation Network Optimization

## Overview
Implementation of Prim's and Kruskal's algorithms for Minimum Spanning Tree to optimize road construction costs while ensuring all city districts are connected.

## Algorithms

### Kruskal's Algorithm
- **Time:** O(E log E)
- **Space:** O(V)
- **Best for:** Sparse graphs
- **Uses:** Union-Find (path compression + union by rank)

### Prim's Algorithm
- **Time:** O(E log V)
- **Space:** O(V + E)
- **Best for:** Dense graphs
- **Uses:** Priority Queue (binary heap)

## Project Structure
ASSIGNMENT-3/
├── src/
│ ├── exceptions/
│ │ ├── GraphException.java
│ │ └── InvalidInputException.java
│ ├── model/
│ │ ├── Edge.java
│ │ ├── Graph.java
│ │ └── GraphMetrics.java
│ ├── algorithm/
│ │ ├── KruskalsAlgorithm.java
│ │ └── PrimsAlgorithm.java
│ ├── datastructure/
│ │ └── UnionFind.java
│ ├── io/
│ │ ├── InputParser.java
│ │ └── OutputWriter.java
│ ├── result/
│ │ └── AlgorithmResult.java
│ └── Main.java
├── ass_3_input.json
├── README.md
└── REPORT.md

text

## How to Run

### Compile
javac -d bin src/**/.java src/.java

text

### Run
java -cp bin Main

text

Or simply run `Main.java` in IntelliJ.

## Input Format
{
"graphs": [
{
"id": 1,
"nodes": ["A", "B", "C"],
"edges": [
{"from": "A", "to": "B", "weight": 4}
]
}
]
}

text

## Output
- Console: Detailed execution results
- File: `ass_3_output_generated.json`

## Test Results
| Graph | Vertices | Edges | MST Cost | Faster Algorithm |
|-------|----------|-------|----------|------------------|
| 1     | 5        | 7     | 16       | Kruskal          |
| 2     | 4        | 5     | 6        | Kruskal          |

## Author
**Nurbol Amangeldinov**
- Course: Design and Analysis of Algorithms
- University: Astana IT University
- Date: October 26, 2025
- GitHub: https://github.com/nurbo323/ASSIGNMENT-3

## License
Academic coursework - Astana IT University