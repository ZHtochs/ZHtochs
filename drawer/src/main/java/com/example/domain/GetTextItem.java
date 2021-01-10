package com.example.domain;

import java.util.Date;
import java.util.Random;

public class GetTextItem {
    private String title;
    private String publishTime;
    private String cover;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "GetTextItem{" +
                "title='" + title + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }

    public static GetTextItem getTextItemCreator() {
        GetTextItem getTextItem = new GetTextItem();
        getTextItem.setCover("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3610495421,999937044&fm=26&gp=0.jpg");
        getTextItem.setPublishTime("" + new Random().nextInt(1000));
        getTextItem.setTitle("" + new Date().toString());
        return getTextItem;
    }
}
