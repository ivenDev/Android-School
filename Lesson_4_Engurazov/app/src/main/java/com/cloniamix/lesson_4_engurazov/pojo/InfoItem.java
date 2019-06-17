package com.cloniamix.lesson_4_engurazov.pojo;

public class InfoItem {

    private String name;
    private String hint;
    private int iconResId;
    private boolean attention;
    private boolean singleInLine;


    public InfoItem(String name, String hint, int iconResId, boolean attention) {
        this.name = name;
        this.hint = hint;
        this.iconResId = iconResId;
        this.attention = attention;
        this.singleInLine = false;
    }

    public InfoItem(String name, int iconResId) {
        this.name = name;
        this.iconResId = iconResId;
        this.singleInLine = true;
    }

    //region getters & setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public boolean isAttention() {
        return attention;
    }

    public void setAttention(boolean attention) {
        this.attention = attention;
    }

    public boolean isSingleInLine() {
        return singleInLine;
    }

    public void setSingleInLine(boolean singleInLine) {
        this.singleInLine = singleInLine;
    }
    //endregion
}
