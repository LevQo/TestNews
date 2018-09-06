package com.levqo.testnews;

/**
 * Created by user on 04.09.2018.
 */

public class Item {
    private String mTitle;
    private String mCoefficient;
    private String mTime;
    private String mPlace;
    private String mPreview;
    private String mArticle;

    public Item(String title, String coefficient, String time, String place, String preview, String article) {
        this.mTitle = title;
        this.mCoefficient = coefficient;
        this.mTime = time;
        this.mPlace = place;
        this.mPreview = preview;
        this.mArticle = article;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getCoefficient() {
        return mCoefficient;
    }

    public void setCoefficient(String mCoefficient) {
        this.mCoefficient = mCoefficient;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String mPlace) {
        this.mPlace = mPlace;
    }

    public String getPreview() {
        return mPreview;
    }

    public void setPreview(String mPreview) {
        this.mPreview = mPreview;
    }

    public String getArticle() {
        return mArticle;
    }

    public void setArticle(String mArticle) {
        this.mArticle = mArticle;
    }
}
