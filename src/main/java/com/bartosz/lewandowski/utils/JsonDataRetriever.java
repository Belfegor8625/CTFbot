package com.bartosz.lewandowski.utils;

import com.bartosz.lewandowski.comm.Communication;
import com.bartosz.lewandowski.models.Bot;
import com.bartosz.lewandowski.models.Flag;
import com.bartosz.lewandowski.models.MapData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import static com.bartosz.lewandowski.utils.Const.MAP_COLUMNS;
import static com.bartosz.lewandowski.utils.Const.MAP_ROWS;

public class JsonDataRetriever {
    public static void getJsonMapData(MapData map, JsonObject jsonData){
        System.out.println("Gathering map intel...");
        JsonArray jArray = jsonData.getAsJsonArray("map");
        if (jArray != null) {
            int[][] tempMap = new int[MAP_ROWS][MAP_COLUMNS];
            for (int i = 0; i < jArray.size(); i++) {
                JsonArray innerArray = jArray.get(i).getAsJsonArray();
                for (int j = 0; j < innerArray.size(); j++) {
                    tempMap[i][j] = innerArray.get(j).getAsInt();
                }
            }
            map.setMap(tempMap);
        } else {
            System.err.println("Map error\n" +
                    "Shutting down...");
            Communication.disconnect();
        }
        map.printMap();
    }

    public static void getJsonFlagData(MapData map, Gson gson, JsonObject jsonData){
        Map<String, Double> flagData = gson.fromJson(jsonData.get("flag"), new TypeToken<Map<String,Double>>(){}.getType());
        Flag tempFlag = new Flag(flagData.get("x").intValue(),flagData.get("y").intValue());
        map.setFlag(tempFlag);
        System.out.println(map.getFlag());
    }

    public static void getJsonBotId(Bot bot,JsonObject jsonData){
        System.out.println("Player ID: " + jsonData.get("playerId"));
        bot.setId(jsonData.get("playerId").getAsInt());
    }
}
