package com.example.home.ui.home;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class PostObject {
    private String userName, postContent,postDate,postTime,userImageUrl;
    private List<String> postImageList=new ArrayList<>();

    public PostObject(){

    }

    public PostObject(String userName, String postContent, String postDate, String postTime, String userImageUrl,List<String> postImageList) {
        this.userName = userName;
        this.postContent = postContent;
        this.postDate = postDate;
        this.postTime = postTime;
        this.userImageUrl = userImageUrl;
        this.postImageList=postImageList;
    }

    public List<String> getPostImageList() {
        return postImageList;
    }

    public void setPostImageList(List<String> postImageList) {
        this.postImageList = postImageList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

}
