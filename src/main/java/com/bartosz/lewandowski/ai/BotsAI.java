package com.bartosz.lewandowski.ai;

import com.bartosz.lewandowski.ai.astar.models.Edge;
import com.bartosz.lewandowski.ai.astar.models.Node;
import com.bartosz.lewandowski.models.Bot;
import com.bartosz.lewandowski.models.MapData;

import java.util.*;

import static com.bartosz.lewandowski.utils.Const.FIELD_OF_VIEW;
import static com.bartosz.lewandowski.utils.Const.MAP_COLUMNS;
import static com.bartosz.lewandowski.utils.Const.MAP_ROWS;

public class BotsAI {


    //h scores is the stright-line distance from the current city to Bucharest

    private static void nodesMaker(List<Node> list, MapData map, Bot bot) {
        int centerXFOV = bot.getxPos();
        int centerYFOV = bot.getyPos();
        int flagXPos = map.getFlag().getxPos();
        int flagYPos = map.getFlag().getyPos();
        for (int x = -FIELD_OF_VIEW; x <= FIELD_OF_VIEW; x++) {
            for (int y = -FIELD_OF_VIEW; y <= FIELD_OF_VIEW; y++) {
                int rangeX = x + centerXFOV;
                int rangeY = y + centerYFOV;
                if (rangeX >= 0 && rangeX < MAP_COLUMNS && rangeY >= 0 && rangeY < MAP_ROWS) {
                    double distance = Math.sqrt(Math.pow(flagXPos - rangeX, 2) + Math.pow(flagYPos - rangeY, 2));
                    list.add(new Node(rangeX, rangeY, distance));
                }
            }
        }
    }


    private static void makeAdjacencies(int[][] map, List<Node> list) {

        for (Node node : list) {

            if (node.x - 1 > 0) {
                Optional<Node> searchedNode = list
                        .stream()
                        .filter(n -> (n.x == node.x - 1 && n.y == node.y))
                        .findAny();
                if (searchedNode.isPresent()) {
                    Node snode = searchedNode.get();
                    Edge e = new Edge(snode, map[snode.y][snode.x]);
                    if (!node.adjacencies.contains(e)) {
                        node.adjacencies.add(e);
                    }
                }
            }

            if (node.y + 1 < MAP_ROWS) {
                Optional<Node> searchedNode = list
                        .stream()
                        .filter(n -> (n.x == node.x && n.y == node.y + 1))
                        .findAny();
                if (searchedNode.isPresent()) {
                    Node snode = searchedNode.get();
                    Edge e = new Edge(snode, map[snode.y][snode.x]);
                    if (!node.adjacencies.contains(e)) {
                        node.adjacencies.add(e);
                    }
                }
            }

            if (node.x + 1 < MAP_COLUMNS) {
                Optional<Node> searchedNode = list
                        .stream()
                        .filter(n -> (n.x == node.x + 1 && n.y == node.y))
                        .findAny();
                if (searchedNode.isPresent()) {
                    Node snode = searchedNode.get();
                    Edge e = new Edge(snode, map[snode.y][snode.x]);
                    if (!node.adjacencies.contains(e)) {
                        node.adjacencies.add(e);
                    }
                }
            }

            if (node.y - 1 < MAP_ROWS && node.y - 1 > 0) {
                Optional<Node> searchedNode = list
                        .stream()
                        .filter(n -> (n.x == node.x && n.y == node.y - 1))
                        .findAny();
                if (searchedNode.isPresent()) {
                    Node snode = searchedNode.get();
                    Edge e = new Edge(snode, map[snode.y][snode.x]);
                    if (!node.adjacencies.contains(e)) {
                        node.adjacencies.add(e);
                    }
                }
            }
        }
    }

    private static Node getSourceNodeFromBotPos(List<Node> list, Bot bot) {
        Optional<Node> botPosNode = list
                .stream()
                .filter(n -> (n.x == bot.getxPos() && n.y == bot.getyPos()))
                .findAny();
        return botPosNode.get();
    }

    private static Node findClosestNodeToGoal(List<Node> list) {
        Collections.sort(list, (o1, o2) -> o1.compareTo(o2));
        System.out.println("Closest node: " + list.get(0).h_scores);
        return list.get(0);
    }

    public static List<Node> getPath(Node target) {
        List<Node> path = new ArrayList<>();

        for (Node node = target; node != null; node = node.parent) {
            path.add(node);
        }

        Collections.reverse(path);

        return path;
    }

    public static void AstarSearch(List<Node> list, Bot bot, MapData map) {

        nodesMaker(list, map, bot);
        makeAdjacencies(map.getMap(), list);
        Node source = getSourceNodeFromBotPos(list, bot);
        Node goal = findClosestNodeToGoal(list);
        Set<Node> explored = new HashSet<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(25,
                (i, j) -> {
                    if (i.f_scores > j.f_scores) {
                        return 1;
                    } else if (i.f_scores < j.f_scores) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
        );
        //cost from start
        source.g_scores = 0;
        queue.add(source);
        boolean found = false;
        while ((!queue.isEmpty()) && (!found)) {
            //the node in having the lowest f_score value
            Node current = queue.poll();
            explored.add(current);
            //goal found
            if (current.x == goal.x && current.y == goal.y) {
                found = true;
            }
            //check every child of current node
            for (Edge e : current.adjacencies) {
                Node child = e.target;
                double cost = e.cost;
                double temp_g_scores = current.g_scores + cost;
                double temp_f_scores = temp_g_scores + child.h_scores;
                                /*if child node has been evaluated and
                                the newer f_score is higher, skip*/
                if ((explored.contains(child)) && (temp_f_scores >= child.f_scores)) {
                    continue;
                }
                                /*else if child node is not in queue or
                                newer f_score is lower*/
                else if ((!queue.contains(child)) || (temp_f_scores < child.f_scores)) {
                    child.parent = current;
                    child.g_scores = temp_g_scores;
                    child.f_scores = temp_f_scores;
                    if (queue.contains(child)) {
                        queue.remove(child);
                    }
                    queue.add(child);
                }
            }
        }
        System.out.println(getPath(goal));
    }
}



