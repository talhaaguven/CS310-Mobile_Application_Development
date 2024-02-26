package edu.sabanciuniv.mehmettalha.models;

import java.util.List;

public class NewsCategory {
    private int serviceMessageCode;
    private String serviceMessageText;
    private List<NewsCategoryItem> newsCategoryItems;

    public NewsCategory(int serviceMessageCode, String serviceMessageText, List<NewsCategoryItem> newsCategoryItems) {
        this.serviceMessageCode = serviceMessageCode;
        this.serviceMessageText = serviceMessageText;
        this.newsCategoryItems = newsCategoryItems;
    }

    public NewsCategory() {
    }

    public int getServiceMessageCode() {
        return serviceMessageCode;
    }

    public void setServiceMessageCode(int serviceMessageCode) {
        this.serviceMessageCode = serviceMessageCode;
    }

    public String getServiceMessageText() {
        return serviceMessageText;
    }

    public void setServiceMessageText(String serviceMessageText) {
        this.serviceMessageText = serviceMessageText;
    }

    public List<NewsCategoryItem> getNewsCategoryItems() {
        return newsCategoryItems;
    }

    public void setNewsCategoryItems(List<NewsCategoryItem> newsCategoryItems) {
        this.newsCategoryItems = newsCategoryItems;
    }
}
