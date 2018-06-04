package com.bartosz.lewandowski.ai.astar.models;

public class Edge {
    public final int cost;
    public final Node target;

    public Edge(Node targetNode, int costVal) {
        target = targetNode;
        cost = costVal;
    }
}

