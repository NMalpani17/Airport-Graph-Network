package edu.neu.csye6205;


import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import java.util.*;

public class MyGraph {
    // Adjacency list to store the MyGraph
    private final Map<String, neighborMap> sourceMap;
    private final List<Edge> edges;

    public static class Edge {
        private final String source;
        private final String destination;
        private final int weight;

        Edge(String source, String destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        // Getters for source, destination, and weight
        public String getSource() {
            return source;
        }

        public String getDestination() {
            return destination;
        }

        public int getWeight() {
            return weight;
        }
    }

    public Map<String, neighborMap> getSourceMap() {
        return sourceMap;
    }

    public MyGraph() {
        sourceMap = new HashMap<>();
        edges = new ArrayList<>();
    }

    // Inner class to represent neighbors map
     class neighborMap {
        private final Map<String, Integer> neighbors;

        neighborMap() {
            neighbors = new HashMap<>();
        }

        // Method to add a neighbor with weight
        public void addNeighbor(String neighbor, int weight) {
            neighbors.put(neighbor, weight);
        }

        // Method to get the neighbors map
        public Map<String, Integer> getNeighbors() {
            return neighbors;
        }

    }

    // Check if the source vertex already exists in the adjacency list
    public void addVertex(String vertex) {
        if (!sourceMap.containsKey(vertex)) {
            sourceMap.put(vertex, new neighborMap());
        }
    }

    // Method to add an edge to the MyGraph
    public void addEdge(String source, String destination, int weight) {
        addVertex(source);
        addVertex(destination);
        sourceMap.get(source).addNeighbor(destination, weight);
        sourceMap.get(destination).addNeighbor(source, weight);
        edges.add(new Edge(source, destination, weight));
        edges.add(new Edge(destination,source, weight));
    }

    // Method to print the adjacency list representation of the MyGraph
    public void printMyGraph() {
        for (String vertex : sourceMap.keySet()) {
            System.out.print(vertex + " -> ");
            neighborMap neighborMap = sourceMap.get(vertex);
            Map<String, Integer> neighbors = neighborMap.getNeighbors();
            int count = neighbors.size();
            for (Map.Entry<String, Integer> entry : neighbors.entrySet()) {
                String neighborVertex = entry.getKey();
                int weight = entry.getValue();
                System.out.print("(" + neighborVertex + ", " + weight + ")");
                count--;
                if (count > 0) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }

    }


    public void printAndVisualizeMyGraph() {
        // Setting the UI package for graph visualization and converting MyGraph to a GraphStream
        System.setProperty("org.graphstream.ui", "swing");
        SingleGraph graphStream = graphToGraphStream();

        // Set style attributes for visualization
        graphStream.setAttribute("ui.stylesheet", "node { size: 30px; fill-color: yellow; text-size: 30px; }");

        // Enable zooming
        graphStream.setAttribute("ui.quality", true);
        graphStream.setAttribute("ui.antialias", true);


        // Display the graph
        Viewer viewer = graphStream.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.EXIT);

    }


    public SingleGraph graphToGraphStream() {
        SingleGraph graphStream = new SingleGraph("MyGraph");

        // Iterate through the edges and add them to the graph
        for (Edge edge : edges) {
            String source = edge.getSource();
            String destination = edge.getDestination();
            int weight = edge.getWeight();

            if (graphStream.getNode(source) == null) {
                graphStream.addNode(source).setAttribute("ui.label", source);
            }

            // Add the destination node to the graph if it doesn't exist
            if (graphStream.getNode(destination) == null) {
                graphStream.addNode(destination).setAttribute("ui.label", destination);
            }

            // Check if the edge already exists in the graph
            if (graphStream.getEdge(source + "_" + destination) == null &&
                    graphStream.getEdge(destination + "_" + source) == null) {
                // Add the edge to the graph
                graphStream.addEdge(source + "_" + destination, source, destination, true)
                        .setAttribute("ui.label", String.valueOf(weight));
                graphStream.addEdge(destination + "_" + source, destination, source, true)
                        .setAttribute("ui.label", String.valueOf(weight));


            }
        }


        return graphStream;
    }


    // Method to get the neighbors of a vertex
    public Map<String, Integer> getNeighbors(String vertex) {
        neighborMap neighborMap = sourceMap.get(vertex);
        if (neighborMap != null) {
            return neighborMap.getNeighbors();
        }
        return new HashMap<>();
    }

    // Method to check if a vertex exists in the MyGraph
    public boolean containsVertex(String vertex) {
        return sourceMap.containsKey(vertex);
    }

    // Method to get all vertices in the MyGraph
    public Set<String> getVertices() {
        return sourceMap.keySet();
    }

    public List<Edge> getAllEdges() {
        return edges;
    }



    public int getWeight(String source, String destination) {
        if (containsVertex(source)) {
            Map<String, Integer> neighbors = getNeighbors(source);
            if (neighbors.containsKey(destination)) {
                return neighbors.get(destination);
            }
        }
        return -1;
    }
}

