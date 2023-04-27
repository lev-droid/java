package com.work.visualization;

import com.work.helpers.WeightedGraph;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class CustomPanel extends JPanel {
    private final WeightedGraph<String> graph;
    private final Map<String, Point> nodeLocations;
    private static final int NODE_RADIUS = 20;

    public CustomPanel(WeightedGraph<String> graph, Map<String, Point> nodeLocations) {
        this.graph = graph;
        this.nodeLocations = nodeLocations;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGraph(g);
    }

    private void drawGraph(Graphics g) {
        // Draw edges
        for (String node : graph) {
            Map<String, Double> edges = graph.edgesFrom(node);
            for (String neighbor : edges.keySet()) {
                drawEdge(g, node, neighbor);
            }
        }

        // Draw nodes
        for (String node : graph) {
            drawNode(g, node);
        }
    }

    private void drawNode(Graphics g, String node) {
        Point location = nodeLocations.get(node);
        g.setColor(Color.BLUE);
        g.fillOval(location.x - NODE_RADIUS, location.y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        g.setColor(Color.WHITE);
        g.drawString(node, location.x, location.y);
    }

    private void drawEdge(Graphics g, String start, String end) {
        Point startLocation = nodeLocations.get(start);
        Point endLocation = nodeLocations.get(end);
        g.setColor(Color.BLACK);
        g.drawLine(startLocation.x, startLocation.y, endLocation.x, endLocation.y);
    }
}
