package com.example.hogvartss.dto;

public class CreateFaculty {
    private String name, color;


    public CreateFaculty(String name, String color) {
        this.name = name;
        this.color = color;

    }

    public CreateFaculty() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
