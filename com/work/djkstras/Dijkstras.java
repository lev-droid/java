/**
 * @param <G> generic type, used as substitute for node type
 * @author Lev petersen
 * Fast Fibonacci Heap implementation
 * */

package com.work.djkstras;
import java.util.*; // For HashMap

import com.work.helpers.*;
public final class Dijkstras {

    public static <G> Map<G, Double> fastPath(WeightedGraph<G> graph, G source) {
        //nodes
        Map<G, FibonacciHeap.Entry<G>> entries = new HashMap<>();
        // costs
        Map<G, Double> distance = new HashMap<>();
        //prio q
        FibonacciHeap<G> queue = new FibonacciHeap<>();
        // queue each item with postivity infinity
        for (G node : graph)
            entries.put(node, queue.enqueue(node, Double.POSITIVE_INFINITY));
        // give the initial node a cost of 0
        queue.decreaseKey(entries.get(source), 0.0);
        while (!queue.isEmpty()) {
            //while nodes not solved

            // find the first element added to the que
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

