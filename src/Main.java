import model.*;
import algorithm.*;
import io.*;
import result.*;
import exceptions.*;
import java.io.IOException;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        System.out.println("=".repeat(70));
        System.out.println("ASSIGNMENT 3: CITY TRANSPORTATION NETWORK OPTIMIZATION");
        System.out.println("Minimum Spanning Tree Analysis");
        System.out.println("=".repeat(70));

        try {
            List<Graph> graphs = InputParser.readGraphs("ass_3_input.json");
            System.out.println("\n✓ Loaded " + graphs.size() + " graphs\n");

            List<OutputWriter.ResultData> results = new ArrayList<>();

            for (Graph graph : graphs) {
                OutputWriter.ResultData result = processGraph(graph);
                results.add(result);
            }

            OutputWriter.writeResults("ass_3_output_generated.json", results);

            System.out.println("\n" + "=".repeat(70));
            System.out.println("✓ PROCESSING COMPLETE");
            System.out.println("=".repeat(70));
            System.out.println("Results saved to: ass_3_output_generated.json\n");

            printSummary(results);

        } catch (IOException e) {
            System.err.println("✗ ERROR: File not found - " + e.getMessage());
        } catch (GraphException e) {
            System.err.println("✗ ERROR: Graph error - " + e.getMessage());
        } catch (InvalidInputException e) {
            System.err.println("✗ ERROR: Invalid input - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("✗ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static OutputWriter.ResultData processGraph(Graph graph) throws GraphException {
        System.out.println("=".repeat(70));
        System.out.println("Graph " + graph.getId());
        System.out.println("=".repeat(70));
        System.out.println("Vertices: " + graph.getVertexCount() + ", Edges: " + graph.getEdgeCount());

        System.out.println("\n► Prim's Algorithm...");
        AlgorithmResult primResult = PrimsAlgorithm.execute(graph);
        System.out.println("  ✓ MST Cost: " + primResult.getTotalCost());
        System.out.println("  ✓ Operations: " + primResult.getOperationsCount());
        System.out.printf("  ✓ Time: %.4f ms\n", primResult.getExecutionTimeMs());

        System.out.println("\n► Kruskal's Algorithm...");
        AlgorithmResult kruskalResult = KruskalsAlgorithm.execute(graph);
        System.out.println("  ✓ MST Cost: " + kruskalResult.getTotalCost());
        System.out.println("  ✓ Operations: " + kruskalResult.getOperationsCount());
        System.out.printf("  ✓ Time: %.4f ms\n", kruskalResult.getExecutionTimeMs());

        if (primResult.getTotalCost() == kruskalResult.getTotalCost()) {
            System.out.println("\n✓ VERIFICATION PASSED: Costs match\n");
        } else {
            System.out.println("\n✗ WARNING: Costs don't match!\n");
        }

        return new OutputWriter.ResultData(
                graph.getId(),
                graph.getVertexCount(),
                graph.getEdgeCount(),
                primResult,
                kruskalResult
        );
    }

    private static void printSummary(List<OutputWriter.ResultData> results) {
        System.out.println("SUMMARY");
        System.out.println("=".repeat(70));
        System.out.printf("%-8s%-10s%-10s%-12s%-15s%n",
                "Graph", "Vertices", "Edges", "MST Cost", "Faster");
        System.out.println("-".repeat(70));

        for (OutputWriter.ResultData r : results) {
            String faster = r.kruskalResult.getExecutionTimeMs() < r.primResult.getExecutionTimeMs()
                    ? "Kruskal" : "Prim";
            System.out.printf("%-8d%-10d%-10d%-12d%-15s%n",
                    r.graphId, r.vertices, r.edges, r.primResult.getTotalCost(), faster);
        }

        System.out.println("=".repeat(70));
    }
}
