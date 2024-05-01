package edu.neu.csye6205;

import org.graphstream.ui.view.Viewer;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filePath = "data/Airports.csv";
        CSVReader csvReader = new CSVReader(filePath);
        MyGraph MyGraph;
        // Reading data from CSV file and handling Error
        try {
            List<String[]> data = csvReader.readCSV();
            MyGraph = Utils.buildMyGraphFromCSV(data);
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            return;
        }
        Scanner scanner = new Scanner(System.in);
        int option=-1;

        // Loop for selecting various operations
        do {
            System.out.println("Select an option:");
            System.out.println("1. Minimum Distance Airport Connectivity (Kruskal's Algorithm)");
            System.out.println("2. Minimum distance between 2 Airports (Dijkstra's Algorithm)");
            System.out.println("3. Breadth-First Search (BFS)");
            System.out.println("4. Depth-First Search (DFS)");
            System.out.println("5. Print Graph");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            // Checking if the input is an integer
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                switch (option) {
                    case 1:
                        //Implementing Kruskal's algorithm
                        Kruskal kruskal=new Kruskal();
                        kruskal.kruskalMST(MyGraph);
                        break;
                    case 2:
                        //Implementing Dijkstra's Algorithm
                        Dijkstra dijkstra = new Dijkstra();
                        dijkstra.dijkstraShortestPath(MyGraph);
                        break;
                    case 3:
                        // Call method to run BFS
                        BFS bfs = new BFS();
                        bfs.bfsTraversal(MyGraph);
                        break;
                    case 4:
                        // Call method to run DFS
                        DFS dfs = new DFS();
                        dfs.dfsTraversal(MyGraph);
                        break;
                    case 5:
                        // Print and visualize graph
                        MyGraph.printAndVisualizeMyGraph();
                        MyGraph.printMyGraph();
                        break;
                    case 0:
                        System.out.println("Exiting program...!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } else {
                // Clearing the scanner buffer
                scanner.next();
                System.out.println("Invalid input. Please enter a valid integer option.");
            }
        } while (option != 0);

    }
}