package com.bartosz.lewandowski.comm;

import com.bartosz.lewandowski.ai.BotsAI;
import com.bartosz.lewandowski.ai.astar.models.Edge;
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
                                    JsonDataRetriever.getJsonBotHasFlag(bot, jsonData);
                                    JsonDataRetriever.getJsonBasePos(bot, jsonData);
                                    JsonDataRetriever.getJsonMapData(map, jsonData);
                                    JsonDataRetriever.getJsonFlagData(map, gson, jsonData);
                                    JsonDataRetriever.getJsonBotPos(bot, jsonData);
                                    JsonDataRetriever.getJsonBotMovesLeft(bot, jsonData);
                                    nodeList.clear();
                                    chooseMove(BotsAI.getPath(BotsAI.AstarSearch(nodeList, bot, map)), bot);
                                    BotsAI.clearNodesParents(nodeList);
                                    BotsAI.clearAdjacencies(nodeList);
                                    break;
                                case "ResponseOK":
                                    System.out.println("ok");
                                    break;
                                case "Connected":
                                    JsonDataRetriever.getJsonBotId(bot, jsonData);
                                    break;
                                case "GameOver":
                                    JsonDataRetriever.checkIfWinner(bot,jsonData);
                                    disconnect();
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

    public void sendConnectionRequest(String botName) {
        getWebsocket().sendText("{\n" +
                "  \"type\":\"Connect\",\n" +
                "  \"name\":\"" + botName + "\"\n" +
                "}");
    }

    private void chooseMove(List<Node> path, Bot bot) {
        Node currPos = path.get(0);
        Node nextMove = path.get(1);
        for (Edge e : currPos.adjacencies) {
            if (e.target == nextMove) {
                if (e.cost > bot.getMovesLeft()) {
                    sendMove(Const.Move.NO_MOVE, bot.getId());
                } else {
                    if (nextMove.x == currPos.x + 1)
                        sendMove(Const.Move.RIGHT, bot.getId());
                    else if (nextMove.x == currPos.x - 1)
                        sendMove(Const.Move.LEFT, bot.getId());
                    else if (nextMove.y == currPos.y + 1)
                        sendMove(Const.Move.DOWN, bot.getId());
                    else if (nextMove.y == currPos.y - 1)
                        sendMove(Const.Move.UP, bot.getId());
                }
                break;
            }
        }
    }

    private static void sendMove(Const.Move move, int id) {
        getWebsocket().sendText("{\n" +
                "  \"type\":\"Move\",\n" +
                "  \"playerId\":" + id + ",\n" +
                "  \"move\":\"" + move + "\"\n" +
                "}");
        System.out.println(move);
    }

    public static void disconnect() {
        getWebsocket().disconnect();
    }
}
