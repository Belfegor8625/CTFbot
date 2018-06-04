package com.bartosz.lewandowski.ai.astar.models;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {

    public final int x;
    public final int y;
    public double g_scores;
    public double h_scores;
    public double f_scores;
    public List<Edge> adjacencies;
    public Node parent;

    public Node(int x, int y, double hVal) {
        this.x = x;
        this.y = y;
        this.h_scores = hVal;
        this.f_scores = 0;
        this.g_scores = 0;
        this.adjacencies = new ArrayList<>();
    }

    @Override
    public int compareTo(Node n) {
        if (this.h_scores > n.h_scores)
            return 1;
        else if (this.h_scores < n.h_scores)
            return -1;
        return 0;
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                "}";
    }
}