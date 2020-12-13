package com.example.domain;

public class GetTextItem {


    /**
     * id : 1333029420651540480
     * title : Android加载大图片，解决OOM问题
     * viewCount : 221
     * commentCount : 54
     * publishTime : 2020-11-29T12:45:55.029+0000
     * userName : 程序员拉大锯
     * cover : /imgs/5.png
     */

    private String id;
    private String title;
    private int viewCount;
    private int commentCount;
    private String publishTime;
    private String userName;
    private String cover;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", viewCount=" + viewCount +
                ", commentCount=" + commentCount +
                ", publishTime='" + publishTime + '\'' +
                ", userName='" + userName + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
