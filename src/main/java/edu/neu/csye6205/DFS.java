package edu.neu.csye6205;

import java.util.*;

public class DFS {
    // Performing DFS traversal starting from a given vertex
    public void dfsTraversal(MyGraph MyGraph) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the starting airport for DFS traversal: ");
        String startVertex = scanner.nextLine();

        // Checking if the MyGraph contains the start vertex
        if (!MyGraph.containsVertex(startVertex)) {
            System.out.println("Airport " + startVertex + " not found in the MyGraph.");
            return;
        }

        // Set to keep track of visited vertices
        Set<String> visited = new HashSet<>();

        // Stack for DFS traversal
        Stack<String> stack = new Stack<>();

        // Adding the start vertex to the stack and mark it as visited
        stack.push(startVertex);
        visited.add(startVertex);
        int totalVertex = 0;
        System.out.println("Depth-First Search Traversal:");
        while (!stack.isEmpty()) {
            // Pop a vertex from the stack
            String currentVertex = stack.pop();
            System.out.print(currentVertex);
            totalVertex++;
            // Get the neighbors of the current vertex
            Map<String, Integer> neighbors = MyGraph.getNeighbors(currentVertex);

            // Add unvisited neighbors to the stack and mark them as visited
            for (String neighbor : neighbors.keySet()) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                    visited.add(neighbor);
                }
            }
            if (!stack.isEmpty()) {
                System.out.print("->");
            }
        }
        System.out.println();
        System.out.println("Total number of Airports = " +totalVertex);
    }
}
