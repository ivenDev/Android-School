package com.cloniamix.lesson_6_engurazov.POJO;

public class PagerData {
    private int imageResId;
    private String text;

    public PagerData(int imageResId, String text) {
        this.imageResId = imageResId;
        this.text = text;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getText() {
        return text;
    }
}
