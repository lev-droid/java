/**
 * @param <G> generic type, used as substitute for node type
 * .
 */
package com.work.djkstras;
import java.util.*; // For HashMap

import com.work.helpers.*;
public final class Dijkstras {

    public static <G> Map<G, Double> fastPath(WeightedGraph<G> graph, G source) {
        Map<G, FibonacciHeap.Entry<G>> entries = new HashMap<>();
        Map<G, Double> distance = new HashMap<>();
        FibonacciHeap<G> queue = new FibonacciHeap<>();

        for (G node : graph)
            entries.put(node, queue.enqueue(node, Double.POSITIVE_INFINITY));
        queue.decreaseKey(entries.get(source), 0.0);
        while (!queue.isEmpty()) {
            FibonacciHeap.Entry<G> currentNode = queue.dequeueMin();
            distance.put(currentNode.getValue(), currentNode.getPriority());
            for (Map.Entry<G, Double> nextNode : graph.edgesFrom(currentNode.getValue()).entrySet()) {
                G destination = nextNode.getKey();
                double pathCost = currentNode.getPriority() + nextNode.getValue();
                if (!distance.containsKey(destination)) {
                    FibonacciHeap.Entry<G> dest = entries.get(destination);
                    if (pathCost < dest.getPriority()) {
                        queue.decreaseKey(dest, pathCost);
                    }
                }
            }
        }
        return distance;
    }
}

