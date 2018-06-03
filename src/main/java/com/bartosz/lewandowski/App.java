package com.bartosz.lewandowski;


import com.bartosz.lewandowski.comm.Communication;
import com.bartosz.lewandowski.models.Bot;
import com.sun.xml.internal.bind.v2.TODO;

public class App {
    public static void main(String[] args) throws Exception {
        Communication botCom = new Communication();
        botCom.sendConnectionRequest();
        //Bot bot = new Bot();
        //botCom.sendMove();
        //TODO
        // - wysyłanie poleceń ruchu na podstawie ścieżki
        // - realizacja drogi od flagi do bazy

    }
}
