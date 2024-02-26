package edu.sabanciuniv.mehmettalha.models;

public class NewsCategoryItem {
    private int id;
    private String name;

    public NewsCategoryItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public NewsCategoryItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
