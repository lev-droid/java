package com.work.visualization;

import com.work.helpers.WeightedGraph;
import com.work.algorithms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GraphVisualizer extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int NODE_RADIUS = 20;

    private WeightedGraph<String> graph;
    private Map<String, Point> nodeLocations;
    private String selectedAlgorithm;
    private String startNode;
    private String endNode;

    public GraphVisualizer() {
        setTitle("Graph Algorithm Visualizer");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize graph, node locations, and the selected algorithm
        graph = new WeightedGraph<>();
        nodeLocations = new HashMap<>();
        selectedAlgorithm = "Dijkstras";

        // Add mouse and keyboard listeners for user interaction
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });

        // Set focusable for key events
        setFocusable(true);
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

    private void handleMouseClick(MouseEvent e) {
        String nodeName = JOptionPane.showInputDialog("Enter node name:");
        if (nodeName != null && !nodeName.isEmpty()) {
            graph.addNode(nodeName);
            nodeLocations.put(nodeName, e.getPoint());
            repaint();
        }
    }

    private void handleKeyPress(KeyEvent e) {
        // Handle key presses for adding/removing edges and running algorithms
        if (e.getKeyChar() == 'e') {
            addEdge();
        } else if (e.getKeyChar() == 'r') {
            removeEdge();
        } else if (e.getKeyChar() == 's') {
            selectAlgorithm();
        } else if (e.getKeyChar() == 'g') {
            runAlgorithm();
        }
    }

    private void addEdge() {
        String start = JOptionPane.showInputDialog("Enter start node:");
        String end = JOptionPane.showInputDialog("Enter end node:");
        double weight = Double.parseDouble(JOptionPane.showInputDialog("Enter edge weight:"));
        graph.addEdge(start, end, weight);
        repaint();
    }

    private void removeEdge() {
        String start = JOptionPane.showInputDialog("Enter start node:");
        String end = JOptionPane.showInputDialog("Enter end node:");
        graph.removeEdge(start, end);
        repaint();
    }

    private void selectAlgorithm() {
        String[] algorithms = {"Dijkstras", "AStar", "BellmanFord"};
        String algorithm = (String) JOptionPane.showInputDialog(
                this,
                "Select an algorithm:",
                "Algorithm Selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                algorithms,
                selectedAlgorithm);

        if (algorithm != null) {
            selectedAlgorithm = algorithm;
        }
    }

    private void runAlgorithm() {
        startNode = JOptionPane.showInputDialog("Enter start node:");
        endNode = JOptionPane.showInputDialog("Enter end node:");

        List<String> path;
        switch (selectedAlgorithm) {
            case "Dijkstras":
                Map<String, Double> distances = Dijkstras.fastPath(graph, startNode);
                path = getPathFromDistances(distances, endNode);
                break;
            case "AStar":
                path = AStar.aStar(graph, startNode, endNode, (a, b) -> 0); // Assuming 0 heuristic for simplicity
                break;
            case "BellmanFord":
                Map<String, Double> shortestPaths = BellmanFord.computeShortestPaths(graph, startNode);
                path = getPathFromDistances(shortestPaths, endNode);
                break;
            default:
                throw new RuntimeException("Unknown algorithm");
        }

        displayPath(path);
    }

    private List<String> getPathFromDistances(Map<String, Double> distances, String endNode) {
        List<String> path = new ArrayList<>();
        if (!distances.containsKey(endNode)) {
            return path;
        }

        String currentNode = endNode;
        path.add(currentNode);

        while (distances.get(currentNode) != 0.0) {
            Map<String, Double> neighbors = graph.edgesFrom(currentNode);
            for (String neighbor : neighbors.keySet()) {
                if (distances.get(currentNode) == distances.get(neighbor) + neighbors.get(neighbor)) {
                    currentNode = neighbor;
                    path.add(0, currentNode);
                    break;
                }
            }
        }

        return path;
    }

    private void displayPath(List<String> path) {
        if (path.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No path found");
        } else {
            JOptionPane.showMessageDialog(this, "Path: " + String.join(" -> ", path));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraphVisualizer visualizer = new GraphVisualizer();
            visualizer.setVisible(true);
        });
    }
}
