package com.work.helpers;

import java.util.*;

public final class WeightedGraph<T> implements Iterable<T> {
    private final Map<T, Map<T, Double>> mGraph = new HashMap<>();

    public boolean addNode(T node) {
        if (this.mGraph.containsKey(node)) return false;
        this.mGraph.put(node, new HashMap<>());
        return true;
    }

    public boolean removeNode(T node) {
    if (!this.mGraph.containsKey(node)) return false;
    this.mGraph.remove(node);
    for (Map<T, Double> neighbors : this.mGraph.values()) {
        neighbors.remove(node);
    }
    return true;
}

    
    public void addEdge(T start, T dest, double length) {
        if (!this.mGraph.containsKey(start) || !this.mGraph.containsKey(dest))
            throw new NoSuchElementException("Both nodes must be in the graph.");
        this.mGraph.get(start).put(dest, length);
    }

    public void removeEdge(T start, T dest) {
        if (!this.mGraph.containsKey(start) || !this.mGraph.containsKey(dest))
            throw new NoSuchElementException("Both nodes must be in the graph.");
        this.mGraph.get(start).remove(dest);
    }

    public Map<T, Double> edgesFrom(T node) {
        Map<T, Double> arcs = this.mGraph.get(node);
        if (arcs == null)
            throw new NoSuchElementException("Source node does not exist.");
        return Collections.unmodifiableMap(arcs);
    }
    public int nodesCount() {
        return mGraph.keySet().size();
    }
    public Iterator<T> iterator() {
        return this.mGraph.keySet().iterator();
    }
}
