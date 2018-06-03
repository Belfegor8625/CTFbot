package com.bartosz.lewandowski.models;


import java.util.ArrayList;
import java.util.List;

public class MapData {

    private List<int[]> map;
    private Flag flag;

    public MapData() {
        flag = new Flag();
        map = new ArrayList<>();
    }

    public List<int[]> getMap() {
        return map;
    }

    public void setMap(List<int[]> map) {
        this.map = map;
    }

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public void printMap() {
        System.out.println();
        for (int[] aMap : map) {
            for (int anAMap : aMap) {
                System.out.print(anAMap+"\t");
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return "MapData{" +
                "map=" + map +
                ", flag=" + flag +
                '}';
    }
}
