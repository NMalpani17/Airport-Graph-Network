package edu.neu.csye6205;

import java.util.List;

public class Utils {

    public static MyGraph buildMyGraphFromCSV(List<String[]> data) {
        MyGraph MyGraph = new MyGraph();
        // Iterate through the data from the CSV and add vertices and edges to the MyGraph
        for (String[] row : data) {
            String source = row[0];
            String destination = row[1];
            int distance = Integer.parseInt(row[2]);
            MyGraph.addEdge(source, destination, distance);
        }
        return MyGraph;
    }
}
