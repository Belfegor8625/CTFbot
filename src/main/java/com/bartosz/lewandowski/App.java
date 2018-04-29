package com.bartosz.lewandowski;


/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws Exception {
        /*System.out.println( "Hello World!" );
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        stompClient.connect("localhost", sessionHandler);

        new Scanner(System.in).nextLine(); // Don't close immediately.*/
        Communication botCom = new Communication();
        botCom.sendConnectionRequest();
        botCom.sendMove();
    }
}
