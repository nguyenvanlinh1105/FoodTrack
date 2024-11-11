package com.example.foodtrack;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketManager {
    private static SocketManager instance;
    private Socket mSocket;

    private SocketManager() {
        try {
            mSocket = IO.socket("http://192.168.1.10:3000");
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    // Singleton để lấy instance của SocketManager
    public static synchronized SocketManager getInstance() {
        if (instance == null) {
            instance = new SocketManager();
        }
        return instance;
    }

    public Socket getSocket() {
        return mSocket;
    }
}
