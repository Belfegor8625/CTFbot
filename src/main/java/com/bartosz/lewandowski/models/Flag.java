package com.bartosz.lewandowski.models;

public class Flag {

    private int xPos;
    private int yPos;

    Flag(){
        xPos = 0;
        yPos = 0;
    }
    public Flag(int x, int y){
        xPos = x;
        yPos = y;
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

    @Override
    public String toString() {
        return "Flag{" +
                "xPos=" + xPos +
                ", yPos=" + yPos +
                '}';
    }

}
