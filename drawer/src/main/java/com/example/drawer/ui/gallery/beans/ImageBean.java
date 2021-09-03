package com.example.drawer.ui.gallery.beans;

/**
 * 功能描述
 *
 * @author zhuhe
 * @since 2021-09-03
 */
public class ImageBean extends ItemBean {
    private String imageUrl;

    public ImageBean(String title, String publishData, String imageUrl) {
        super(title, publishData);
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
