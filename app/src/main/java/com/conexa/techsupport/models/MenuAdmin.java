package com.conexa.techsupport.models;

public class MenuAdmin {
    private String title;
    private int iconRes;
    private  int backgroundColorRes;

    public MenuAdmin(String title, int iconRes, int backgroundColorRes){
        this.title = title;
        this.iconRes = iconRes;
        this.backgroundColorRes = backgroundColorRes;
    }

    public String getTitle(){
        return title;
    }

    public int getBackgroundColorRes() {
        return backgroundColorRes;
    }

    public int getIconRes() {
        return iconRes;
    }
}
