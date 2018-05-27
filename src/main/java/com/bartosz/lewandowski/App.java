package com.bartosz.lewandowski;


import com.bartosz.lewandowski.comm.Communication;
import com.bartosz.lewandowski.models.Bot;

public class App {
    public static void main(String[] args) throws Exception {
        Communication botCom = new Communication();
        botCom.sendConnectionRequest();
        //Bot bot = new Bot();
        //botCom.sendMove();

    }
}
