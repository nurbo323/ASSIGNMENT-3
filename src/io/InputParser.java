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
            // DON'T remove braces - needed for edge parsing!

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
            if (c == '{') {
                if (braceCount == 0) {
                    start = i;
                }
                braceCount++;
            } else if (c == '}') {
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
        if (end == -1) end = json.length();

        String numStr = json.substring(start, end).trim();
        // Remove any trailing }
        if (numStr.endsWith("}")) {
            numStr = numStr.substring(0, numStr.length() - 1).trim();
        }

        return Integer.parseInt(numStr);
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

        // Find "edges" key
        int edgesKeyPos = json.indexOf("\"edges\":");
        if (edgesKeyPos == -1) {
            return edges;
        }

        // Find array start
        int arrayStart = json.indexOf("[", edgesKeyPos) + 1;
        int arrayEnd = findMatchingBracket(json, arrayStart - 1);

        if (arrayEnd == -1) {
            return edges;
        }

        String edgesContent = json.substring(arrayStart, arrayEnd);

        // Extract each edge object
        int braceCount = 0;
        int start = -1;

        for (int i = 0; i < edgesContent.length(); i++) {
            char c = edgesContent.charAt(i);

            if (c == '{') {
                if (braceCount == 0) {
                    start = i;
                }
                braceCount++;
            } else if (c == '}') {
                braceCount--;
                if (braceCount == 0 && start != -1) {
                    String edgeStr = edgesContent.substring(start + 1, i);

                    try {
                        String from = extractStringValue(edgeStr, "\"from\":");
                        String to = extractStringValue(edgeStr, "\"to\":");
                        int weight = extractIntValue(edgeStr, "\"weight\":");

                        edges.add(new Edge(from, to, weight));
                    } catch (Exception e) {
                        System.err.println("Warning: Failed to parse edge: " + edgeStr);
                    }

                    start = -1;
                }
            }
        }

        return edges;
    }

    private static int findMatchingBracket(String str, int openPos) {
        int count = 1;
        for (int i = openPos + 1; i < str.length(); i++) {
            if (str.charAt(i) == '[') count++;
            else if (str.charAt(i) == ']') {
                count--;
                if (count == 0) return i;
            }
        }
        return -1;
    }

    private static String extractStringValue(String json, String key) {
        int keyPos = json.indexOf(key);
        if (keyPos == -1) return "";

        int start = json.indexOf("\"", keyPos + key.length()) + 1;
        int end = json.indexOf("\"", start);

        return json.substring(start, end);
    }

    private static int extractIntValue(String json, String key) {
        int keyPos = json.indexOf(key);
        if (keyPos == -1) return 0;

        int start = keyPos + key.length();

        // Skip whitespace and colon
        while (start < json.length() &&
                (Character.isWhitespace(json.charAt(start)) || json.charAt(start) == ':')) {
            start++;
        }

        // Skip whitespace after colon
        while (start < json.length() && Character.isWhitespace(json.charAt(start))) {
            start++;
        }

        int end = start;
        while (end < json.length() &&
                (Character.isDigit(json.charAt(end)) || json.charAt(end) == '-')) {
            end++;
        }

        return Integer.parseInt(json.substring(start, end));
    }
}
