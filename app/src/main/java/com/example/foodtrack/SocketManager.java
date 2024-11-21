package com.example.foodtrack;

import android.util.Log;

import com.example.foodtrack.API.APIService;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketManager {
    private static SocketManager instance;
    private Socket mSocket;
    private APIService api;

    private SocketManager() {
        String apiLink = api.url;
//        Log.d("apiLink", apiLink);
        try {
            mSocket = IO.socket(apiLink);
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
