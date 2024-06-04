package com.example.blogue_app;

public class Article {
    private int id;
    private String title;
    private String content;
    private String status;

    // Constructeur
    public Article(int id, String title, String content, String status) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
