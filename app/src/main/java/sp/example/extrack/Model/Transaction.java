package com.example.extrack.Model;

public class Transaction {

    private int id;
    private int Image;

    private String Flow;
    private String Title;
    private String Amount;
    private String Purpose;
    private String Description;

    public Transaction(int id, int image, String flow, String title, String amount, String purpose, String description) {
        this.id = id;
        Image = image;
        Flow = flow;
        Title = title;
        Amount = amount;
        Purpose = purpose;
        Description = description;
    }

    public Transaction() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getFlow() {
        return Flow;
    }

    public void setFlow(String flow) {
        Flow = flow;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String purpose) {
        Purpose = purpose;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
