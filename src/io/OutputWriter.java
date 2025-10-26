package io;

import model.Edge;
import result.AlgorithmResult;
import java.io.*;
import java.util.List;
import java.util.Locale;



public class OutputWriter {

    public static class ResultData {
        public int graphId;
        public int vertices;
        public int edges;
        public AlgorithmResult primResult;
        public AlgorithmResult kruskalResult;

        public ResultData(int graphId, int vertices, int edges,
                          AlgorithmResult primResult, AlgorithmResult kruskalResult) {
            this.graphId = graphId;
            this.vertices = vertices;
            this.edges = edges;
            this.primResult = primResult;
            this.kruskalResult = kruskalResult;
        }
    }

    public static void writeResults(String filename, List<ResultData> results)
            throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(filename));

        writer.println("{");
        writer.println("  \"results\": [");

        for (int i = 0; i < results.size(); i++) {
            ResultData result = results.get(i);
            writer.print(formatResult(result));

            if (i < results.size() - 1) {
                writer.println(",");
            } else {
                writer.println();
            }
        }

        writer.println("  ]");
        writer.println("}");
        writer.close();
    }

    private static String formatResult(ResultData result) {
        StringBuilder sb = new StringBuilder();
        sb.append("    {\n");
        sb.append(String.format("      \"graph_id\": %d,\n", result.graphId));
        sb.append("      \"input_stats\": {\n");
        sb.append(String.format("        \"vertices\": %d,\n", result.vertices));
        sb.append(String.format("        \"edges\": %d\n", result.edges));
        sb.append("      },\n");

        sb.append("      \"prim\": {\n");
        sb.append("        \"mst_edges\": [\n");
        formatEdges(sb, result.primResult.getMstEdges());
        sb.append("        ],\n");
        sb.append(String.format("        \"total_cost\": %d,\n", result.primResult.getTotalCost()));
        sb.append(String.format("        \"operations_count\": %d,\n", result.primResult.getOperationsCount()));
        sb.append(String.format(Locale.US, "        \"execution_time_ms\": %.2f\n", result.primResult.getExecutionTimeMs()));
        sb.append("      },\n");

        sb.append("      \"kruskal\": {\n");
        sb.append("        \"mst_edges\": [\n");
        formatEdges(sb, result.kruskalResult.getMstEdges());
        sb.append("        ],\n");
        sb.append(String.format("        \"total_cost\": %d,\n", result.kruskalResult.getTotalCost()));
        sb.append(String.format("        \"operations_count\": %d,\n", result.kruskalResult.getOperationsCount()));
        sb.append(String.format(Locale.US, "        \"execution_time_ms\": %.2f\n", result.kruskalResult.getExecutionTimeMs()));
        sb.append("      }\n");
        sb.append("    }");

        return sb.toString();
    }


    private static void formatEdges(StringBuilder sb, List<Edge> edges) {
        for (int i = 0; i < edges.size(); i++) {
            sb.append("          ").append(edges.get(i));
            if (i < edges.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
    }
}
