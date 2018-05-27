package com.bartosz.lewandowski.models;


import java.util.Arrays;

import static com.bartosz.lewandowski.utils.Const.*;

public class MapData {


    private int[][] map;
    private Flag flag;

    public MapData() {
        flag = new Flag();
        map = new int[MAP_ROWS][MAP_COLUMNS];
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public void printMap() {
        for (int[] aMap : map) {
            for (int anAMap : aMap) {
                System.out.print(anAMap);
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return "MapData{" +
                "map=" + Arrays.toString(map) +
                ", flag=" + flag +
                '}';
    }
}
