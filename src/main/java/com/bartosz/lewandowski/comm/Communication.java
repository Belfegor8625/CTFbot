package com.bartosz.lewandowski.comm;

import com.bartosz.lewandowski.ai.BotsAI;
import com.bartosz.lewandowski.ai.astar.models.Node;
import com.bartosz.lewandowski.models.Bot;
import com.bartosz.lewandowski.models.MapData;
import com.bartosz.lewandowski.utils.Const;
import com.bartosz.lewandowski.utils.JsonDataRetriever;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.util.ArrayList;
import java.util.List;

public class Communication {

    private static WebSocket websocket;

    private static WebSocket getWebsocket() {
        return websocket;
    }

    private void setWebsocket(WebSocket websocket) {
        Communication.websocket = websocket;
    }

    public Communication() {
        final Gson gson = new Gson();
        final MapData map = new MapData();
        final Bot bot = new Bot();
        final List<Node> nodeList = new ArrayList<>();
        final boolean[] firstMessage = {true};
        try {
            setWebsocket(new WebSocketFactory()
                    .createSocket("ws://localhost:8000/")
                    .addListener(new WebSocketAdapter() {
                        @Override
                        public void onTextMessage(WebSocket ws, String message) {
                            System.out.println("Received msg: " + message);
                            JsonObject jsonData = gson.fromJson(message, JsonObject.class);
                            String type = jsonData.get("type").toString().replace("\"", "");
                            System.out.println("typ: " + type);

                            switch (type) {
                                case "MoveRequest":
                                    if (firstMessage[0]) {
                                        JsonDataRetriever.getJsonMapData(map, jsonData);
                                        JsonDataRetriever.getJsonFlagData(map, gson, jsonData);
                                        BotsAI.AstarSearch(nodeList, bot, map);
                                        firstMessage[0] = false;
                                    } else {
                                        System.out.println("move request");
                                        sendMove(Const.Move.LEFT);
                                    }
                                    break;
                                case "ResponseOK":
                                    System.out.println("ok");
                                    break;
                                case "Connected":
                                    JsonDataRetriever.getJsonBotId(bot, jsonData);
                                    break;
                                default:
                                    System.err.println("błąd");
                                    disconnect();
                                    break;
                            }
                        }
                    })
                    .connect());
        } catch (Exception e) {
            System.err.println("Exception: " + e.toString());
        }
    }

    public void sendConnectionRequest() {
        getWebsocket().sendText("{\n" +
                "  \"type\":\"Connect\",\n" +
                "  \"name\":\"CTF Winner\"\n" +
                "}");
    }

//    public void chooseMove(){
//        if ()
//        sendMove();
//    }

    private static void sendMove(Const.Move move) {
        getWebsocket().sendText("{\n" +
                "  \"type\":\"Move\",\n" +
                "  \"playerId\":0,\n" +
                "  \"move\":\"" + move + "\"\n" +
                "}");
    }

    public static void disconnect() {
        getWebsocket().disconnect();
    }
}
