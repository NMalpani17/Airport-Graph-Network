package edu.neu.csye6205;

import java.util.*;

public class BFS {
    // Performing BFS traversal starting from a given vertex
    public void bfsTraversal(MyGraph MyGraph) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the starting Airport for BFS traversal: ");
        String startVertex = scanner.nextLine();

        // Checking if the MyGraph contains the start vertex
        if (!MyGraph.containsVertex(startVertex)) {
            System.out.println("Airport " + startVertex + " not found in the MyGraph.");
            return;
        }

        // Setting to keep track of visited vertices
        Set<String> visited = new HashSet<>();

        // Queue for BFS traversal
        Queue<String> queue = new LinkedList<>();

        // Add the start vertex to the queue and mark it as visited
        queue.offer(startVertex);
        visited.add(startVertex);
        int totalVertex = 0;
        System.out.println("Breadth-First Search Traversal:");
        while (!queue.isEmpty()) {
            // Dequeue a vertex from the queue
            String currentVertex = queue.poll();
            System.out.print(currentVertex);
            totalVertex++;

            // Get the neighbors of the current vertex
            Map<String, Integer> neighbors = MyGraph.getNeighbors(currentVertex);

            // Add unvisited neighbors to the queue and mark them as visited
            for (String neighbor : neighbors.keySet()) {
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                }
            }
            if(!queue.isEmpty()){
                System.out.print("->");
            }
        }
        System.out.println();
        System.out.println("Total number of Airports = " +totalVertex);
    }
}
