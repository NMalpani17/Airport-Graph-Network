package edu.neu.csye6205;

import java.util.HashMap;
import java.util.Map;

public class DisjointSet {
    private final Map<String, String> parent;
    private final Map<String, Integer> rank;

    public DisjointSet() {
        parent = new HashMap<>();
        rank = new HashMap<>();
    }

    // Create a set with only one element
    public void makeSet(String vertex) {
        parent.put(vertex, vertex);
        rank.put(vertex, 0);
    }

    // Find the root of the set containing vertex
    public String find(String vertex) {
        if (!parent.get(vertex).equals(vertex)) {
            // Path compression: making the parent of vertex the root of its set
            parent.put(vertex, find(parent.get(vertex)));
        }
        return parent.get(vertex);
    }

    // Merge two sets containing vertices v1 and v2
    public void union(String v1, String v2) {
        String root1 = find(v1);
        String root2 = find(v2);

        // Union by rank: attach smaller rank tree under root of higher rank tree
        if (!root1.equals(root2)) {
            if (rank.get(root1) < rank.get(root2)) {
                parent.put(root1, root2);
            } else if (rank.get(root1) > rank.get(root2)) {
                parent.put(root2, root1);
            } else {
                parent.put(root2, root1);
                // Increasing rank only if ranks are equal
                rank.put(root1, rank.get(root1) + 1);
            }
        }
    }



}
