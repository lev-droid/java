package com.work.djkstras;
import com.work.helpers.WeightedGraph;
import java.util.*;

public class BellmanFord<T> {

    public static <T> Map<T, Double> computeShortestPaths(WeightedGraph<T> graph, T source) {
        // Initialize maps to hold distances and previous nodes for each node in the graph
        Map<T, Double> distances = new HashMap<>();
        Map<T, T> previousNodes = new HashMap<>();

        // Set the distance to all nodes to be infinite, except for the source node which is set to 0
        for (T node : graph) {
            distances.put(node, Double.POSITIVE_INFINITY);
            previousNodes.put(node, null);
        }
        distances.put(source, 0.0);

        // Compute the shortest path to all nodes in the graph
        for (int i = 0; i < graph.nodesCount() - 1; i++) {
            // Iterate through all nodes in the graph
            for (T node : graph) {
                // Iterate through all edges from the current node to its neighbors
                for (Map.Entry<T, Double> neighborEntry : graph.edgesFrom(node).entrySet()) {
                    T neighbor = neighborEntry.getKey();
                    double edgeWeight = neighborEntry.getValue();
                    double newDistance = distances.get(node) + edgeWeight;

                    // Update the distance and previous node if a shorter path is found
                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        previousNodes.put(neighbor, node);
                    }
                }
            }
        }

        // Check for negative cycles in the graph
        for (T node : graph) {
            for (Map.Entry<T, Double> neighborEntry : graph.edgesFrom(node).entrySet()) {
                T neighbor = neighborEntry.getKey();
                double edgeWeight = neighborEntry.getValue();
                double newDistance = distances.get(node) + edgeWeight;

                // If a shorter path to a node is found after the algorithm has finished, there is a negative cycle
                if (newDistance < distances.get(neighbor)) {
                    throw new RuntimeException("Negative cycle detected");
                }
            }
        }

        // Return the map of distances to each node
        return distances;
    }
}
