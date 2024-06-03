package com.example.blogue_app;

public class StatusItem {
    private String status;
    private int color;

    public StatusItem(String status, int color) {
        this.status = status;
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public int getColor() {
        return color;
    }
}
