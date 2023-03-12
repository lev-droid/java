/**
 * @param <G> generic type, used as substitute for node type
 * @author Lev petersen
 * Fast Fibonacci Heap implementation
 * */

package com.work.algorithms;
import java.util.*; // For HashMap

import com.work.helpers.*;
public final class Dijkstras {

    public static <G> Map<G, Double> fastPath(WeightedGraph<G> graph, G source) {
        //Create a Hash Map to hold entries.
        Map<G, FibonacciHeap.Entry<G>> entries = new HashMap<>();
        // Create a HashMap to hold node distances.
        Map<G, Double> distance = new HashMap<>();
        //Create a Fibonacci Heap to hold nodes.
        FibonacciHeap<G> queue = new FibonacciHeap<>();
        // Initialize the heap by adding all nodes to the HashMap with an entry in the heap with a priority of infinity.
        for (G node : graph)
            entries.put(node, queue.enqueue(node, Double.POSITIVE_INFINITY));
        // Add the source node to the heap with a priority of 0.
        queue.decreaseKey(entries.get(source), 0.0);
        //Loop through the heap while it is not empty.
        while (!queue.isEmpty()) {
            // Find the node with the minimum cost and remove it from the heap.
            FibonacciHeap.Entry<G> currentNode = queue.dequeueMin();
            //Update the distance HashMap with the cost of the removed node.
                    distance.put(currentNode.getValue(), currentNode.getPriority());
            //For each neighbor of the removed node, calculate the cost of the path from the source node through the removed node to the neighbor.
            for (Map.Entry<G, Double> nextNode : graph.edgesFrom(currentNode.getValue()).entrySet()) {
                G destination = nextNode.getKey();
                double pathCost = currentNode.getPriority() + nextNode.getValue();
                //If the new cost is lower than the current cost of the neighbor, update the neighbor's entry in the heap with the new cost.
                if (!distance.containsKey(destination)) {
                    FibonacciHeap.Entry<G> dest = entries.get(destination);
                    if (pathCost < dest.getPriority()) {
                        queue.decreaseKey(dest, pathCost);
                    }
                }
            }
        }
        //Return the distance HashMap.
        return distance;
    }
}

