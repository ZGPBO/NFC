package com.pyp.nfcandroid.util;

/**
 * Created by Administrator on 2017/7/12.
 */

public class tagItem {
    private int Image;
    private String title;
    private String detail;

    public tagItem(int Image,String title,String detail){

        this.Image=Image;
        this.title=title;
        this.detail=detail;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
