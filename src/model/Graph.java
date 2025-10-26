package model;

import exceptions.GraphException;
import java.util.*;

public final class Graph {
    private final int id;
    private final List<String> vertices;
    private final List<Edge> edges;
    private final Map<String, List<Edge>> adjacencyList;
    private final GraphMetrics metrics;


    public Graph(int id, List<String> vertices, List<Edge> edges)
            throws GraphException {
        validateInput(vertices, edges);

        this.id = id;
        this.vertices = Collections.unmodifiableList(new ArrayList<>(vertices));
        this.edges = Collections.unmodifiableList(new ArrayList<>(edges));
        this.adjacencyList = buildAdjacencyList();
        this.metrics = calculateMetrics();
    }

    private void validateInput(List<String> vertices, List<Edge> edges)
            throws GraphException {
        if (vertices == null || vertices.isEmpty()) {
            throw new GraphException(GraphException.ErrorCode.EMPTY_GRAPH);
        }

        if (edges == null) {
            throw new GraphException(
                    GraphException.ErrorCode.INVALID_EDGE,
                    "edges list is null"
            );
        }

        // Check for duplicate vertices
        Set<String> vertexSet = new HashSet<>(vertices);
        if (vertexSet.size() != vertices.size()) {
            throw new GraphException(
                    GraphException.ErrorCode.INVALID_EDGE,
                    "duplicate vertices found"
            );
        }

        // Validate all edges reference existing vertices
        for (Edge edge : edges) {
            if (!vertexSet.contains(edge.getFrom()) ||
                    !vertexSet.contains(edge.getTo())) {
                throw new GraphException(
                        GraphException.ErrorCode.INVALID_EDGE,
                        "edge references non-existent vertex: " + edge
                );
            }
        }
    }

    private Map<String, List<Edge>> buildAdjacencyList() {
        Map<String, List<Edge>> adjList = new HashMap<>();

        // Initialize empty lists
        for (String vertex : vertices) {
            adjList.put(vertex, new ArrayList<>());
        }

        // Add edges (both directions for undirected graph)
        for (Edge edge : edges) {
            adjList.get(edge.getFrom()).add(edge);
            try {
                // Create reverse edge
                Edge reverseEdge = new Edge(edge.getTo(), edge.getFrom(),
                        edge.getWeight());
                adjList.get(edge.getTo()).add(reverseEdge);
            } catch (GraphException e) {
                // Should never happen as validation already done
                throw new RuntimeException("Unexpected error creating reverse edge", e);
            }
        }

        // Make lists unmodifiable
        Map<String, List<Edge>> unmodifiableMap = new HashMap<>();
        for (Map.Entry<String, List<Edge>> entry : adjList.entrySet()) {
            unmodifiableMap.put(entry.getKey(),
                    Collections.unmodifiableList(entry.getValue()));
        }

        return Collections.unmodifiableMap(unmodifiableMap);
    }

    private GraphMetrics calculateMetrics() {
        if (edges.isEmpty()) {
            return new GraphMetrics(vertices.size(), 0, 0, 0, 0.0);
        }

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        long sum = 0;

        for (Edge edge : edges) {
            int weight = edge.getWeight();
            min = Math.min(min, weight);
            max = Math.max(max, weight);
            sum += weight;
        }

        double avg = (double) sum / edges.size();

        return new GraphMetrics(vertices.size(), edges.size(), min, max, avg);
    }

    public int getId() {
        return id;
    }

    public List<String> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Map<String, List<Edge>> getAdjacencyList() {
        return adjacencyList;
    }

    public GraphMetrics getMetrics() {
        return metrics;
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getEdgeCount() {
        return edges.size();
    }

    /**
     * Checks if graph is connected using BFS
     */
    public boolean isConnected() {
        if (vertices.isEmpty()) return true;

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.offer(vertices.get(0));
        visited.add(vertices.get(0));

        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (Edge edge : adjacencyList.get(current)) {
                if (!visited.contains(edge.getTo())) {
                    visited.add(edge.getTo());
                    queue.offer(edge.getTo());
                }
            }
        }

        return visited.size() == vertices.size();
    }

    @Override
    public String toString() {
        return String.format("Graph{id=%d, %s}", id, metrics);
    }
}
