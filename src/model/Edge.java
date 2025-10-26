package model;

import exceptions.GraphException;
import java.util.Objects;


public final class Edge implements Comparable<Edge> {
    private final String from;
    private final String to;
    private final int weight;


    public Edge(String from, String to, int weight) throws GraphException {
        validateVertex(from, "source");
        validateVertex(to, "destination");
        validateWeight(weight);

        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    private void validateVertex(String vertex, String position) throws GraphException {
        if (vertex == null) {
            throw new GraphException(
                    GraphException.ErrorCode.NULL_VERTEX,
                    position + " vertex is null"
            );
        }
        if (vertex.trim().isEmpty()) {
            throw new GraphException(
                    GraphException.ErrorCode.NULL_VERTEX,
                    position + " vertex is empty"
            );
        }
    }

    private void validateWeight(int weight) throws GraphException {
        if (weight < 0) {
            throw new GraphException(
                    GraphException.ErrorCode.NEGATIVE_WEIGHT,
                    "weight = " + weight
            );
        }
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Edge other) {
        int weightComparison = Integer.compare(this.weight, other.weight);
        if (weightComparison != 0) {
            return weightComparison;
        }
        // Break ties by vertex names for deterministic sorting
        int fromComparison = this.from.compareTo(other.from);
        if (fromComparison != 0) {
            return fromComparison;
        }
        return this.to.compareTo(other.to);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Edge)) return false;
        Edge other = (Edge) obj;
        // Undirected: A-B equals B-A
        return weight == other.weight &&
                ((from.equals(other.from) && to.equals(other.to)) ||
                        (from.equals(other.to) && to.equals(other.from)));
    }

    @Override
    public int hashCode() {
        // Symmetric hash for undirected edges
        return Objects.hash(Math.min(from.hashCode(), to.hashCode()),
                Math.max(from.hashCode(), to.hashCode()),
                weight);
    }

    @Override
    public String toString() {
        return String.format("{\"from\":\"%s\",\"to\":\"%s\",\"weight\":%d}",
                from, to, weight);
    }


    public String toDisplayString() {
        return String.format("%s -- %d -- %s", from, weight, to);
    }
}
