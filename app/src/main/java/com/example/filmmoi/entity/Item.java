package com.example.filmmoi.entity;

public class Item {
    private Content content;

    public Item() {
    }

    public Item(Content content) {
        this.content = content;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
