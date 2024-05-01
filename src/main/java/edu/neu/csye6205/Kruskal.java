package edu.neu.csye6205;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;


public class Kruskal {


    public void kruskalMST(MyGraph myGraph) {
        if (myGraph == null || myGraph.getAllEdges() == null || myGraph.getVertices() == null) {
            System.err.println("Error: Invalid input graph.");
            return;
        }

        // Initializing the result list to store the MST edges
        List<MyGraph.Edge> mstEdges = new ArrayList<>();

        // Sorting all the edges of the MyGraph in ascending order of their weights
        List<MyGraph.Edge> edges = myGraph.getAllEdges();
        edges.sort(Comparator.comparingInt(MyGraph.Edge::getWeight));

        // Creating a disjoint set to track the connected components
        DisjointSet disjointSet = new DisjointSet();
        Set<String> vertices = myGraph.getVertices();
        for (String vertex : vertices) {
            disjointSet.makeSet(vertex);
        }

        // Initializing variables to track the total distance and airports visited
        int totalDistance = 0;
        int airportsVisited = 0;

        // Iterating through the sorted edges and add them to the MST if they don't form a cycle
        for (MyGraph.Edge edge : edges) {
            String source = edge.getSource();
            String destination = edge.getDestination();

            // Checking if including this edge forms a cycle
            if (!disjointSet.find(source).equals(disjointSet.find(destination))) {
                mstEdges.add(edge);
                disjointSet.union(source, destination);
                totalDistance += edge.getWeight();
                airportsVisited++;
            }

            // If all airports are visited, breaking the loop
            if (airportsVisited == vertices.size() - 1) {
                break;
            }
        }

        // Printing the airports connected by each edge in the MST
        System.out.print("Minimum Spanning Tree (MST): ");
        for (int i = 0; i < mstEdges.size(); i++) {
            MyGraph.Edge edge = mstEdges.get(i);
            System.out.print(edge.getSource() + " -> " + edge.getDestination() + " (" + edge.getWeight() + ")");
            if (i != mstEdges.size() - 1) {
                System.out.print(", ");
            }
        }

        // Printing the minimum distance to cover all airports
        System.out.println("\nMinimum distance to cover all " + vertices.size() + " airports: " + totalDistance + " miles");

        visualizeMST(myGraph, mstEdges);

    }


    public void visualizeMST(MyGraph myGraph, List<MyGraph.Edge> mstEdges) {
        // Setting the UI package for graph visualization and converting into GraphStream graph
        System.setProperty("org.graphstream.ui", "swing");
        SingleGraph mstGraph = new SingleGraph("Minimum Spanning Tree");

        // Adding vertices to the graph
        Set<String> vertices = myGraph.getVertices();
        for (String vertex : vertices) {
            Node node = mstGraph.addNode(vertex);
            node.setAttribute("ui.label", vertex);
        }

        // Adding MST edges to the graph
        for (MyGraph.Edge edge : mstEdges) {
            mstGraph.addEdge(edge.getSource() + "_" + edge.getDestination(), edge.getSource(), edge.getDestination(), true)
                    .setAttribute("ui.label", String.valueOf(edge.getWeight()));
            mstGraph.addEdge(edge.getDestination() + "_" + edge.getSource(), edge.getDestination(), edge.getSource(), true)
                    .setAttribute("ui.label", String.valueOf(edge.getWeight()));

        }

        // Setting style attributes for visualization
        mstGraph.setAttribute("ui.stylesheet", "node { size: 30px; fill-color: yellow; text-size: 30px; }");

        // Anti-aliasing and smoothness
        mstGraph.setAttribute("ui.quality", true);
        mstGraph.setAttribute("ui.antialias", true);


        // Displaying the MST
        Viewer viewer = mstGraph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.EXIT);
    }
}
