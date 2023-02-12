package com.work.djkstras;
import  com.work.helpers.*;
import org.w3c.dom.Node;

import java.io.*;
import java.util.*;

public final class dijkstras{
    static Map expanded;
    static Map<Node,  FibonacciHeap.Entry<Node>> entries;
    static FibonacciHeap<Node> vertexQueue;
    public static void setup(WeightedGraph.Node source){
        vertexQueue = new FibonacciHeap<>();
        entries = new HashMap<Node, FibonacciHeap.Entry<Node>>();
        expanded = new HashMap<Node, Double>();


    }
    private void djikstras(WeightedGraph graph, int source) {
        for (WeightedGraph.Node node: graph.adj_list.get(0) )
        {
            entries.put((Node) node, vertexQueue.enqueue((Node) node, Double.POSITIVE_INFINITY));
        }
        vertexQueue.decreaseKey(entries.get(source), 0.0);

    }

}
