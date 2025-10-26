package algorithm;

import model.Edge;
import model.Graph;
import result.AlgorithmResult;
import exceptions.GraphException;
import java.util.*;


public class PrimsAlgorithm {

    private static class PQEdge implements Comparable<PQEdge> {
        Edge edge;

        PQEdge(Edge edge) {
            this.edge = edge;
        }

        @Override
        public int compareTo(PQEdge other) {
            return Integer.compare(this.edge.getWeight(), other.edge.getWeight());
        }
    }

    public static AlgorithmResult execute(Graph graph) throws GraphException {
        List<String> nodes = graph.getVertices();
        Map<String, List<Edge>> adjacencyList = graph.getAdjacencyList();

        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;
        int operationsCount = 0;

        if (nodes.isEmpty()) {
            return new AlgorithmResult(mstEdges, totalCost, operationsCount, 0.0);
        }

        long startTime = System.nanoTime();

        Set<String> visited = new HashSet<>();
        PriorityQueue<PQEdge> pq = new PriorityQueue<>();

        String startNode = nodes.get(0);
        visited.add(startNode);

        for (Edge edge : adjacencyList.get(startNode)) {
            pq.offer(new PQEdge(edge));
            operationsCount++;
        }

        while (!pq.isEmpty() && visited.size() < nodes.size()) {
            PQEdge pqEdge = pq.poll();
            Edge currentEdge = pqEdge.edge;
            operationsCount++;

            String toNode = currentEdge.getTo();

            if (visited.contains(toNode)) {
                operationsCount++;
                continue;
            }

            visited.add(toNode);
            mstEdges.add(currentEdge);
            totalCost += currentEdge.getWeight();
            operationsCount++;

            for (Edge edge : adjacencyList.get(toNode)) {
                if (!visited.contains(edge.getTo())) {
                    pq.offer(new PQEdge(edge));
                    operationsCount++;
                }
                operationsCount++;
            }
        }

        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1_000_000.0;

        return new AlgorithmResult(mstEdges, totalCost, operationsCount, executionTime);
    }
}
