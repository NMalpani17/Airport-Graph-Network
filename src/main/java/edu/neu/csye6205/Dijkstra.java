package edu.neu.csye6205;

import java.util.*;
public class Dijkstra {

    public static class Pair<K, V> {
        private final K key;
        private final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    // Method to find the shortest path using Dijkstra's Algorithm
    public void dijkstraShortestPath(MyGraph MyGraph) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the source airport: ");
        String source = scanner.nextLine();
        // Checking if source vertex exists in the MyGraph
        if (!MyGraph.containsVertex(source)) {
            System.out.println("Source Airport " + source + " not found in the MyGraph.");
            return;
        }

        System.out.print("Enter the destination airport: ");
        String destination = scanner.nextLine();
        // Checking if destination vertex exists in the MyGraph
        if (!MyGraph.containsVertex(destination)) {
            System.out.println("Destination Airport " + destination + " not found in the MyGraph.");
            return;
        }

        // Initializing a priority queue to store vertices based on their distance from the source
        PriorityQueue<Pair<String, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Pair::getValue));
        // Initializing a map to store the distance of each vertex from the source
        Map<String, Integer> distanceMap = new HashMap<>();
        // Initializing a map to store the parent vertex of each vertex in the shortest path tree
        Map<String, String> parentMap = new HashMap<>();
        // Initializing a set to keep track of visited vertices
        Set<String> visited = new HashSet<>();

        Map<String, MyGraph.neighborMap> sourceMap = MyGraph.getSourceMap();

        // Initializing distances of all vertices to infinity and add them to the priority queue
        for (String vertex : sourceMap.keySet()) {
            distanceMap.put(vertex, Integer.MAX_VALUE);
            parentMap.put(vertex, null);
        }
        // Setting distance of source vertex to 0
        distanceMap.put(source, 0);
        pq.offer(new Pair<>(source, 0));

        // Running Dijkstra's Algorithm
        while (!pq.isEmpty()) {
            String current = pq.poll().getKey();
            // If the destination vertex is reached, break the loop
            if (current.equals(destination)) {
                break;
            }
            // Skipping vertex if it has already been visited
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            // Updating distances of neighboring vertices
            Map<String, Integer> neighbors = MyGraph.getNeighbors(current);
            for (Map.Entry<String, Integer> entry : neighbors.entrySet()) {
                String neighbor = entry.getKey();
                int weight = entry.getValue();
                if (!visited.contains(neighbor)) {
                    int newDistance = distanceMap.get(current) + weight;
                    if (newDistance < distanceMap.get(neighbor)) {
                        distanceMap.put(neighbor, newDistance);
                        parentMap.put(neighbor, current);
                        pq.offer(new Pair<>(neighbor, newDistance));
                    }
                }
            }
        }

        StringBuilder shortestPath = new StringBuilder();
        String current = destination;
        int totalDistance = 0;
        while (current != null) {
            shortestPath.insert(0, current); // Inserting current vertex at the beginning
            String parent = parentMap.get(current);
            if (parent != null) {
                int distance = MyGraph.getWeight(parent, current); // Getting distance
                totalDistance += distance;
                shortestPath.insert(0, " -> " + distance + " miles -> "); // Printing path
            }
            current = parent;
        }


        // Append total distance to the end of the string
        shortestPath.append("\nTotal Distance: ").append(totalDistance).append(" miles");

        System.out.println(shortestPath.toString());

    }

}
