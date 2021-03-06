package com.africanlaughter.farmbizafrica.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Morris on 4/28/2016.
 */
public class Article {
    private String id;
    private String title;
    private String details;
    private String intro;
    private String imageURL;
    private String articleURL;
    private String articleFullContent;

    public Article(){}
    public Article(String id, String title, String details, String intro, String imageURL, String articleURL,String articleFullContent) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.intro = intro;
        this.imageURL = imageURL;
        this.articleURL=articleURL;
        this.articleFullContent=articleFullContent;
    }

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

    public String getDetails() {
        Date date = new Date();
        String newDates="";
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(details);
            newDates = new SimpleDateFormat("yyyy-MM-dd").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDates;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getArticleURL() {
        return articleURL;
    }

    public void setArticleURL(String articleURL) {
        this.articleURL = articleURL;
    }

    public String getArticleFullContent() {
        return articleFullContent;
    }

    public void setArticleFullContent(String articleFullContent) {
        this.articleFullContent = articleFullContent;
    }
}


