package com.example.foodtrack;

import com.example.foodtrack.API.APIService;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketManager {
    private static SocketManager instance;
    private Socket mSocket;
    private APIService api;

    private SocketManager() {
        try {
            mSocket = IO.socket("https://63cf-2a09-bac1-7ae0-10-00-17-32a.ngrok-free.app");
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
