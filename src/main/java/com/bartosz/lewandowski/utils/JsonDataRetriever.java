package com.bartosz.lewandowski.utils;

import com.bartosz.lewandowski.comm.Communication;
import com.bartosz.lewandowski.models.Bot;
import com.bartosz.lewandowski.models.Flag;
import com.bartosz.lewandowski.models.MapData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.Map;


public class JsonDataRetriever {
    public static void getJsonMapData(MapData map, JsonObject jsonData) {
        System.out.println("Gathering map intel...");
        JsonObject jsonMapInfo = jsonData.getAsJsonObject("map");
        int mapWidth = jsonMapInfo.get("width").getAsInt();
        int mapHeight = jsonMapInfo.get("height").getAsInt();
        JsonArray jArray = jsonMapInfo.getAsJsonArray("fields");
        if (jArray != null) {
            map.getMap().clear();
            for (int i = 0; i < jArray.size(); i++) {
                int[] row = new int[mapWidth];
                for (int j = 0; j < jArray.get(i).getAsJsonArray().size(); j++) {
                    row[j] = jArray.get(i).getAsJsonArray().get(j).getAsInt();
                }
                map.getMap().add(row);
            }
        } else {
            System.err.println("Map error\n" +
                    "Shutting down...");
            Communication.disconnect();
        }
        map.printMap();
    }

    public static void getJsonFlagData(MapData map, Gson gson, JsonObject jsonData) {
        Map<String, Double> flagData =
                gson.fromJson(jsonData.get("flag"), new TypeToken<Map<String, Double>>() {
                }.getType());
        Flag tempFlag = new Flag(flagData.get("x").intValue(), flagData.get("y").intValue());
        map.setFlag(tempFlag);
        System.out.println(map.getFlag());
    }

    public static void getJsonBotId(Bot bot, JsonObject jsonData) {
        System.out.println("Player ID: " + jsonData.get("playerId"));
        bot.setId(jsonData.get("playerId").getAsInt());
    }

    public static void getJsonBotHasFlag(Bot bot, JsonObject jsonData){
        JsonArray botsData = jsonData.get("players").getAsJsonArray();
        for (int i = 0; i < botsData.size(); i++) {
            JsonObject botData = botsData.get(i).getAsJsonObject();
            int curr_id = botData.get("id").getAsInt();
            if (curr_id == bot.getId()){
                bot.setHasFlag(botData.get("hasFlag").getAsBoolean());
                break;
            }
        }
    }

    public static void getJsonBotPos(Bot bot, JsonObject jsonData) {
        JsonArray botsData = jsonData.get("players").getAsJsonArray();
        for (int i = 0; i < botsData.size(); i++) {
            JsonObject botData = botsData.get(i).getAsJsonObject();
            int curr_id = botData.get("id").getAsInt();
            if (curr_id == bot.getId()){
                bot.setxPos(botData.get("x").getAsInt());
                bot.setyPos(botData.get("y").getAsInt());
                break;
            }
        }
    }

    public static void getJsonBasePos(Bot bot, JsonObject jsonData) {
        JsonArray botsData = jsonData.get("players").getAsJsonArray();
        for (int i = 0; i < botsData.size(); i++) {
            JsonObject botData = botsData.get(i).getAsJsonObject();
            int curr_id = botData.get("id").getAsInt();
            if (curr_id == bot.getId()){
                JsonObject jsonBasePos = botData.get("basePosition").getAsJsonObject();
                bot.setxBasePos(jsonBasePos.get("x").getAsInt());
                bot.setyBasePos(jsonBasePos.get("y").getAsInt());
                break;
            }
        }
    }

    public static void getJsonBotMovesLeft(Bot bot, JsonObject jsonData){
        JsonArray botsData = jsonData.get("players").getAsJsonArray();
        for (int i = 0; i < botsData.size(); i++) {
            JsonObject botData = botsData.get(i).getAsJsonObject();
            int curr_id = botData.get("id").getAsInt();
            if (curr_id == bot.getId()){
                bot.setMovesLeft(botData.get("movesLeft").getAsInt());
                break;
            }
        }
    }
}
