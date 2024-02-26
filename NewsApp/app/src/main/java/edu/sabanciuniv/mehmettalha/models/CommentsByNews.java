package edu.sabanciuniv.mehmettalha.models;

import java.util.List;

public class CommentsByNews {
    private int serviceMessageCode;
    private String serviceMessageText;
    private List<CommentByNewsItem> commentByNewsItem;

    public CommentsByNews(int serviceMessageCode, String serviceMessageText, List<CommentByNewsItem> commentByNewsItem) {
        this.serviceMessageCode = serviceMessageCode;
        this.serviceMessageText = serviceMessageText;
        this.commentByNewsItem = commentByNewsItem;
    }

    public CommentsByNews() {
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

    public List<CommentByNewsItem> getCommentByNewsItem() {
        return commentByNewsItem;
    }

    public void setCommentByNewsItem(List<CommentByNewsItem> commentByNewsItem) {
        this.commentByNewsItem = commentByNewsItem;
    }
}
