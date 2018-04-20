package com.edgar.blackroom;

public class BulletinItem {

    private String titleString;
    private boolean isBulletinNew;
    private String dateString;
    private String urlString;

    public BulletinItem(String titleString, String dateString,
                        boolean isBulletinNew, String urlString) {
        this.titleString = titleString;
        this.dateString = dateString;
        this.isBulletinNew = isBulletinNew;
        this.urlString = urlString;
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

    public void setUrlString(String urlString) {
        this.urlString = urlString;
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

    public String getUrlString() {
        return urlString;
    }

    @Override
    public String toString() {
        String str = "";
        if (isBulletinNew) {
            str = "NEW: " + titleString + " " + dateString + " " + urlString;
        } else {
            str = titleString + " " + dateString + " " + urlString;
        }
        return str;
    }
}
