package edu.sabanciuniv.mehmettalha.models;

import java.io.Serializable;

public class NewsByCategoryItem implements Serializable {
    private int id;
    private String title,text,date,image,categoryName;

    public NewsByCategoryItem(int id, String title, String text, String date, String image, String categoryName) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
        this.image = image;
        this.categoryName = categoryName;
    }

    public NewsByCategoryItem() {
    }

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
