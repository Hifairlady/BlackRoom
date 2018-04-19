package com.edgar.blackroom;

public class BulletinItem {

    private String titleString;
    private boolean isBulletinNew;
    private String dateString;

    public BulletinItem(String titleString, String dateString, boolean isBulletinNew) {
        this.titleString = titleString;
        this.dateString = dateString;
        this.isBulletinNew = isBulletinNew;
    }

    public String getTitleString() {
        return titleString;
    }

    public String getDateString() {
        return dateString;
    }

    public boolean shouldBeNew() {
        return isBulletinNew;
    }

    public void setTitleString(String titleString) {
        this.titleString = titleString;
    }

    public void setBulletinNew(boolean bulletinNew) {
        isBulletinNew = bulletinNew;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
}
