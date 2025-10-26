package io;

import model.Edge;
import model.Graph;
import exceptions.GraphException;
import exceptions.InvalidInputException;
import java.io.*;
import java.util.*;

public class InputParser {

    public static List<Graph> readGraphs(String filename)
            throws IOException, InvalidInputException, GraphException {

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        StringBuilder jsonContent = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            jsonContent.append(line.trim());
        }
        reader.close();

        String content = jsonContent.toString();

        int graphsStart = content.indexOf("\"graphs\":[") + 10;
        int graphsEnd = content.lastIndexOf("]");
        String graphsContent = content.substring(graphsStart, graphsEnd);

        String[] graphStrings = splitGraphs(graphsContent);

        List<Graph> graphs = new ArrayList<>();
        for (String graphStr : graphStrings) {
            graphStr = graphStr.replace("{", "").replace("}", "");

            int id = extractInt(graphStr, "\"id\":");
            List<String> nodes = extractStringArray(graphStr, "\"nodes\":");
            List<Edge> edges = extractEdges(graphStr);

            graphs.add(new Graph(id, nodes, edges));
        }

        return graphs;
    }

    private static String[] splitGraphs(String content) {
        List<String> graphs = new ArrayList<>();
        int braceCount = 0;
        int start = 0;

        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c == '{') braceCount++;
            else if (c == '}') {
                braceCount--;
                if (braceCount == 0) {
                    graphs.add(content.substring(start, i + 1));
                    start = i + 2;
                }
            }
        }

        return graphs.toArray(new String[0]);
    }

    private static int extractInt(String json, String key) {
        int start = json.indexOf(key) + key.length();
        int end = json.indexOf(",", start);
        if (end == -1) end = json.indexOf("}", start);
        return Integer.parseInt(json.substring(start, end).trim());
    }

    private static List<String> extractStringArray(String json, String key) {
        List<String> result = new ArrayList<>();
        int start = json.indexOf(key) + key.length();
        int arrayStart = json.indexOf("[", start) + 1;
        int arrayEnd = json.indexOf("]", arrayStart);
        String arrayContent = json.substring(arrayStart, arrayEnd);

        String[] items = arrayContent.split(",");
        for (String item : items) {
            result.add(item.replace("\"", "").trim());
        }

        return result;
    }

    private static List<Edge> extractEdges(String json) throws GraphException {
        List<Edge> edges = new ArrayList<>();
        int edgesStart = json.indexOf("\"edges\":[") + 9;
        int edgesEnd = json.indexOf("]", edgesStart);
        String edgesContent = json.substring(edgesStart, edgesEnd);

        int braceCount = 0;
        int start = 0;
        List<String> edgeStrings = new ArrayList<>();

        for (int i = 0; i < edgesContent.length(); i++) {
            char c = edgesContent.charAt(i);
            if (c == '{') braceCount++;
            else if (c == '}') {
                braceCount--;
                if (braceCount == 0) {
                    edgeStrings.add(edgesContent.substring(start, i + 1));
                    start = i + 2;
                }
            }
        }

        for (String edgeStr : edgeStrings) {
            edgeStr = edgeStr.replace("{", "").replace("}", "");

            String from = extractString(edgeStr, "\"from\":");
            String to = extractString(edgeStr, "\"to\":");
            int weight = extractInt(edgeStr, "\"weight\":");

            edges.add(new Edge(from, to, weight));
        }

        return edges;
    }

    private static String extractString(String json, String key) {
        int start = json.indexOf(key) + key.length();
        int valueStart = json.indexOf("\"", start) + 1;
        int valueEnd = json.indexOf("\"", valueStart);
        return json.substring(valueStart, valueEnd);
    }
}
