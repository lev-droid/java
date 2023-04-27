package com.work.visualization;

import com.work.helpers.WeightedGraph;
import com.work.algorithms.*;

import javax.swing.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
public class GraphVisualizer extends JFrame implements ActionListener {
    private final WeightedGraph<String> graph = new WeightedGraph<>();
    private final Map<String, Point> nodeLocations = new HashMap<>();
    private final Random random = new Random();
    private final int FRAME_WIDTH = 800;
    private final int FRAME_HEIGHT = 600;
    private String selectedAlgorithm = "Dijkstras";
    private String startNode;
    private String endNode;

    public GraphVisualizer() {
        setTitle("Graph Visualizer");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu graphMenu = new JMenu("Graph");
        menuBar.add(graphMenu);

        JMenuItem addNodeMenuItem = new JMenuItem("Add node");
        addNodeMenuItem.addActionListener(this);
        graphMenu.add(addNodeMenuItem);

        JMenuItem addEdgeMenuItem = new JMenuItem("Add edge");
        addEdgeMenuItem.addActionListener(this);
        graphMenu.add(addEdgeMenuItem);

        JMenuItem removeNodeMenuItem = new JMenuItem("Remove node");
        removeNodeMenuItem.addActionListener(this);
        graphMenu.add(removeNodeMenuItem);

        JMenuItem removeEdgeMenuItem = new JMenuItem("Remove edge");
        removeEdgeMenuItem.addActionListener(this);
        graphMenu.add(removeEdgeMenuItem);

        JMenu algorithmMenu = new JMenu("Algorithm");
        menuBar.add(algorithmMenu);

        JMenuItem selectAlgorithmMenuItem = new JMenuItem("Select algorithm");
        selectAlgorithmMenuItem.addActionListener(this);
        algorithmMenu.add(selectAlgorithmMenuItem);

        JMenuItem runAlgorithmMenuItem = new JMenuItem("Run algorithm");
        runAlgorithmMenuItem.addActionListener(this);
        algorithmMenu.add(runAlgorithmMenuItem);

        CustomPanel panel = new CustomPanel(graph, nodeLocations);
        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Add node":
                addNode();
                break;
            case "Add edge":
                addEdge();
                break;
            case "Remove node":
                removeNode();
                break;
            case "Remove edge":
                removeEdge();
                break;
            case "Select algorithm":
                selectAlgorithm();
                break;
            case "Run algorithm":
                runAlgorithm();
                break;
            default:
                throw new RuntimeException("Unknown command");
        }
    }

    private void addNode() {
        String nodeName = JOptionPane.showInputDialog("Enter node name:");
        graph.addNode(nodeName);
        nodeLocations.put(nodeName, new Point(random.nextInt(FRAME_WIDTH), random.nextInt(FRAME_HEIGHT)));
        repaint();
    }

    private void addEdge() {
        String start = JOptionPane.showInputDialog("Enter start node:");
        String end = JOptionPane.showInputDialog("Enter end node:");
        double weight = Double.parseDouble(JOptionPane.showInputDialog("Enter edge weight:"));

        graph.addEdge(start, end, weight);
        repaint();
    }

    private void removeNode() {
        String nodeName = JOptionPane.showInputDialog("Enter node name:");
        graph.removeNode(nodeName);
        nodeLocations.remove(nodeName);
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
        selectedAlgorithm = (String) JOptionPane.showInputDialog(
                this,
                "Select an algorithm:",
                "Algorithm Selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                algorithms,
                selectedAlgorithm
        );
    }

    private void runAlgorithm() {
        startNode = JOptionPane.showInputDialog("Enter start node:");
        endNode = JOptionPane.showInputDialog("Enter end node:");

        List<String> path;

        switch (selectedAlgorithm) {
            case "Dijkstras":
                Map<String, Double> distances = Dijkstras.fastPath(graph, startNode);
                path = getPathFromDistances(distances);
                break;
            case "AStar":
                path = AStar.aStar(graph, startNode, endNode, (from, to) -> 1.0);
                break;
            case "BellmanFord":
                Map<String, Double> bellmanFordDistances = BellmanFord.computeShortestPaths(graph, startNode);
                path = getPathFromDistances(bellmanFordDistances);
                break;
            default:
                throw new RuntimeException("Unknown algorithm");
        }

        if (path.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No path found");
        } else {
            JOptionPane.showMessageDialog(this, "Path: " + String.join(" -> ", path));
        }
    }

    private List<String> getPathFromDistances(Map<String, Double> distances) {
        if (!distances.containsKey(endNode)) {
            return List.of();
        }

        // Reconstruct the path from the distances map
        List<String> path = new ArrayList<>();
        String currentNode = endNode;
        path.add(currentNode);

        while (!currentNode.equals(startNode)) {
            double minDistance = Double.POSITIVE_INFINITY;
            String minNode = null;

            for (String neighbor : graph.edgesFrom(currentNode).keySet()) {
                double neighborDistance = distances.getOrDefault(neighbor, Double.POSITIVE_INFINITY);
                if (neighborDistance < minDistance) {
                    minDistance = neighborDistance;
                    minNode = neighbor;
                }
            }

            if (minNode == null) {
                return List.of();
            }

            currentNode = minNode;
            path.add(currentNode);
        }

        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GraphVisualizer visualizer = new GraphVisualizer();
            visualizer.setVisible(true);
        });
    }
}
