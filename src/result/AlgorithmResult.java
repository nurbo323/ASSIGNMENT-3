package result;

import model.Edge;
import java.util.*;

public class AlgorithmResult {
    private final List<Edge> mstEdges;
    private final int totalCost;
    private final int operationsCount;
    private final double executionTimeMs;

    public AlgorithmResult(List<Edge> mstEdges, int totalCost,
                           int operationsCount, double executionTimeMs) {
        this.mstEdges = Collections.unmodifiableList(new ArrayList<>(mstEdges));
        this.totalCost = totalCost;
        this.operationsCount = operationsCount;
        this.executionTimeMs = executionTimeMs;
    }

    public List<Edge> getMstEdges() { return mstEdges; }
    public int getTotalCost() { return totalCost; }
    public int getOperationsCount() { return operationsCount; }
    public double getExecutionTimeMs() { return executionTimeMs; }
}
