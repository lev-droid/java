package com.work.algorithms;
import com.work.helpers.*;
import java.util.*;

public class AStar {
    private AStar() {} // Private constructor to prevent instantiation

    public static <T> List<T> aStar(WeightedGraph<T> graph, T start, T goal, HeuristicFunction<T> heuristic) {
        Map<T, Double> gScore = new HashMap<>();
        Map<T, Double> fScore = new HashMap<>();
        Map<T, T> cameFrom = new HashMap<>();


        Set<T> openSet = new HashSet<>();
        Set<T> closedSet = new HashSet<>();

        openSet.add(start);
        gScore.put(start, 0.0);
        fScore.put(start, heuristic.apply(start, goal));

        while (!openSet.isEmpty()) {
            T current = Collections.min(openSet, Comparator.comparing(fScore::get));

            if (current.equals(goal)) {
                return reconstructPath(cameFrom, goal);
            }

            openSet.remove(current);
            closedSet.add(current);

            for (T neighbor : graph.edgesFrom(current).keySet()) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                double tentativeGScore = gScore.get(current) + graph.edgesFrom(current).get(neighbor);
                if (!openSet.contains(neighbor)) {
                    openSet.add(neighbor);
                } else if (tentativeGScore >= gScore.get(neighbor)) {
                    continue;
                }

                cameFrom.put(neighbor, current);
                gScore.put(neighbor, tentativeGScore);
                fScore.put(neighbor, gScore.get(neighbor) + heuristic.apply(neighbor, goal));
            }
        }

        return Collections.emptyList();
    }

    private static <T> List<T> reconstructPath(Map<T, T> cameFrom, T current) {
        List<T> path = new ArrayList<>();
        path.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(current);
        }
        Collections.reverse(path);
        return path;
    }

    @FunctionalInterface
    public interface HeuristicFunction<T> {
        double apply(T from, T to);
    }
}
