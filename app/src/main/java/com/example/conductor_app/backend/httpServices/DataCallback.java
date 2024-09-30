package com.example.conductor_app.backend.httpServices;

public interface DataCallback {
    void onDataReceived(String data);
    void onError(String errorMessage);
}