package com.example.extrack.Model;

public class Articles {

    private int articleImage;
    private String articleTitle;
    private String articleLink;

    public Articles(int articleImage, String articleTitle, String articleLink) {
        this.articleImage = articleImage;
        this.articleTitle = articleTitle;
        this.articleLink = articleLink;
    }

    public Articles() {

    }

    public int getArticleImage() {
        return articleImage;
    }

    public void setArticleImage(int articleImage) {
        this.articleImage = articleImage;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleLink() {
        return articleLink;
    }

    public void setArticleLink(String articleLink) {
        this.articleLink = articleLink;
    }
}
