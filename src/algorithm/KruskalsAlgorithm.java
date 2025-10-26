package algorithm;

import model.Edge;
import model.Graph;
import result.AlgorithmResult;
import datastructure.UnionFind;
import exceptions.GraphException;
import java.util.*;


public class KruskalsAlgorithm {

    public static AlgorithmResult execute(Graph graph) throws GraphException {
        List<String> nodes = graph.getVertices();
        List<Edge> edges = new ArrayList<>(graph.getEdges());

        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;
        int operationsCount = 0;

        if (nodes.isEmpty()) {
            return new AlgorithmResult(mstEdges, totalCost, operationsCount, 0.0);
        }

        long startTime = System.nanoTime();

        // Sort edges by weight
        Collections.sort(edges);
        operationsCount += edges.size();

        // Initialize Union-Find
        UnionFind uf = new UnionFind(nodes);

        // Process edges
        for (Edge edge : edges) {
            operationsCount++;

            if (uf.union(edge.getFrom(), edge.getTo())) {
                mstEdges.add(edge);
                totalCost += edge.getWeight();
                operationsCount += 2;

                if (mstEdges.size() == nodes.size() - 1) {
                    break;
                }
            }

            operationsCount++;
        }

        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1_000_000.0;

        return new AlgorithmResult(mstEdges, totalCost, operationsCount, executionTime);
    }
}
