package com.bartosz.lewandowski;

public class Flag {

    private int xPos;
    private int yPos;

    public Flag(){
        xPos = 0;
        yPos = 0;
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
