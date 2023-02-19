package com.work.djkstras;
import com.work.helpers.WeightedGraph;
import org.tinylog.Logger;

import java.util.Map;

public class GUI {
    public static void main(String[] args) {
        WeightedGraph<String> graph = setup();
        Map<String, Double> distance = Dijkstras.fastPath(graph, "A");
        Map<String, Double> distances2 = BellmanFord.computeShortestPaths(graph, "A");
        for (Map.Entry<String, Double> entry : distances2.entrySet())
            Logger.info("Node (Bellman) " + entry.getKey() +
                    ", Distance = " + entry.getValue());
        for (Map.Entry<String, Double> entry : distance.entrySet())
            Logger.info("Node (Djikstras)" + entry.getKey() +
                    ", Distance = " + entry.getValue());
    }


    public static WeightedGraph<String> setup() {
        WeightedGraph<String> graph = new WeightedGraph<>();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");
        graph.addNode("H");
        graph.addNode("I");
        graph.addNode("J");
        graph.addNode("K");
        graph.addNode("L");
        graph.addNode("M");
        graph.addNode("N");
        graph.addNode("O");
        graph.addNode("P");
        graph.addNode("Q");
        graph.addNode("R");
        graph.addNode("S");
        graph.addNode("T");
        graph.addNode("U");
        graph.addNode("V");
        graph.addNode("W");
        graph.addNode("X");
        graph.addNode("Y");
        graph.addNode("Z");
        graph.addEdge("A", "B", 4);
        graph.addEdge("A", "C", 2);
        graph.addEdge("C", "B", 1);
        graph.addEdge("B", "D", 5);
        graph.addEdge("C", "D", 8);
        graph.addEdge("C", "E", 10);
        graph.addEdge("D", "E", 2);
        graph.addEdge("D", "F", 6);
        graph.addEdge("E", "D", 1);
        graph.addEdge("E", "F", 2);
        graph.addEdge("E", "G", 1);
        graph.addEdge("F", "G", 9);
        graph.addEdge("G", "F", 9);
        graph.addEdge("G", "H", 2);
        graph.addEdge("G", "I", 4);
        graph.addEdge("H", "I", 7);
        graph.addEdge("I", "H", 7);
        graph.addEdge("I", "J", 6);
        graph.addEdge("I", "K", 7);
        graph.addEdge("J", "K", 8);
        graph.addEdge("K", "J", 8);
        graph.addEdge("K", "L", 5);
        graph.addEdge("K", "M", 9);
        graph.addEdge("L", "M", 3);
        graph.addEdge("M", "L", 3);
        graph.addEdge("M", "N", 4);
        return graph;
    }

}
