package edu.sabanciuniv.mehmettalha.models;

import java.util.List;

public class NewsByCategory {
    private int serviceMessageCode;
    private String serviceMessageText;
    private List<NewsByCategoryItem> newsByCategoryItems;

    public NewsByCategory(int serviceMessageCode, String serviceMessageText, List<NewsByCategoryItem> newsByCategoryItems) {
        this.serviceMessageCode = serviceMessageCode;
        this.serviceMessageText = serviceMessageText;
        this.newsByCategoryItems = newsByCategoryItems;
    }

    public NewsByCategory() {
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

    public List<NewsByCategoryItem> getNewsByCategoryItems() {
        return newsByCategoryItems;
    }

    public void setNewsByCategoryItems(List<NewsByCategoryItem> newsByCategoryItems) {
        this.newsByCategoryItems = newsByCategoryItems;
    }
}
