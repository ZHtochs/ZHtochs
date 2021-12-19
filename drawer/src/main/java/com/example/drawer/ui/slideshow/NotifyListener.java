package com.example.drawer.ui.slideshow;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-12-20 02:35
 **/
public interface NotifyListener {
    public void notifyEnterEdit();

    public void notifyCheckItemChange(int count);

    public void notifyEmptyData();
}