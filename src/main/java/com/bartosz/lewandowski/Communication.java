package com.bartosz.lewandowski;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;

public class Communication {

    private WebSocket websocket;
    public WebSocket getWebsocket() {
        return websocket;
    }

    public void setWebsocket(WebSocket websocket) {
        this.websocket = websocket;
    }
    public Communication(){
        try {
            setWebsocket( new WebSocketFactory()
                    .createSocket("ws://localhost:8000/")
                    .addListener(new WebSocketAdapter() {
                        @Override
                        public void onTextMessage(WebSocket ws, String message) {
                            System.out.println("Received msg: " + message);
                        }
                    })
                    .connect());
        }catch(Exception e){
            System.err.println("Exception: " + e.toString());
        }
    }
    public void sendConnectionRequest(){
        getWebsocket().sendText("{\n" +
                "  \"type\":\"Connect\",\n" +
                "  \"name\":\"CTF Winner\"\n" +
                "}");
    }
    public void sendMove (){
        getWebsocket().sendText("{\n" +
                "  \"type\":\"Move\",\n" +
                "  \"playerId\":0,\n" +
                "  \"move\":\"RIGHT\"\n" +
                "}");
    }
    public void disconnect(){
        getWebsocket().disconnect();
    }
}
