# Assignment 3: City Transportation Network Optimization

## Overview
Implementation of Prim's and Kruskal's algorithms for Minimum Spanning Tree to optimize city road construction with minimum cost.

## Algorithms

### Kruskal's Algorithm
- Time: O(E log E)
- Space: O(V)
- Best for: Sparse graphs

### Prim's Algorithm
- Time: O(E log V)
- Space: O(V + E)
- Best for: Dense graphs

## Project Structure
ASSIGNMENT-3/
├── src/
│ ├── algorithm/
│ ├── model/
│ ├── exceptions/
│ └── Main.java
├── README.md
└── REPORT.md

text

## How to Run
javac -d bin src/**/.java src/.java
java -cp bin Main

text

## Results
| Graph | MST Cost | Algorithm |
|-------|----------|-----------|
| 1     | 16       | Optimal   |
| 2     | 6        | Optimal   |

## Author
Nurbol Amangeldinov  