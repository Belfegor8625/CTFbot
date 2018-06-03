package com.bartosz.lewandowski.models;

import com.bartosz.lewandowski.ai.astar.models.Node;

import java.util.ArrayList;
import java.util.List;

public class Bot {
    private int id;
    private String name;
    private int xPos;
    private int yPos;
    private int movesLeft;
    private boolean hasFlag;
    private boolean isAlive;
    private int xBasePos;
    private int yBasePos;
    private List<Node> fieldOfViewNodes;
    public Bot(){
        id = 17;
        name = "CTF Winner ;)";
        xPos = 0;
        yPos = 0;
        movesLeft = 5;
        hasFlag = false;
        isAlive = true;
        xBasePos = 0;
        yBasePos = 0;
        fieldOfViewNodes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public void setMovesLeft(int movesLeft) {
        this.movesLeft = movesLeft;
    }

    public boolean getHasFlag() {
        return hasFlag;
    }

    public void setHasFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getxBasePos() {
        return xBasePos;
    }

    public void setxBasePos(int xBasePos) {
        this.xBasePos = xBasePos;
    }

    public int getyBasePos() {
        return yBasePos;
    }

    public void setyBasePos(int yBasePos) {
        this.yBasePos = yBasePos;
    }

    @Override
    public String toString() {
        return "Bot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", xPos=" + xPos +
                ", yPos=" + yPos +
                ", movesLeft=" + movesLeft +
                ", hasFlag=" + hasFlag +
                ", isAlive=" + isAlive +
                ", xBasePos=" + xBasePos +
                ", yBasePos=" + yBasePos +
                '}';
    }
}
