package com.example.home.ui.newpost;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NewPostObject {
    private String userImageUrl=null;
    private String userName=null;
    private String postContent=null;
    private String postDate="";
    private String postTime="";
    private ArrayList<String> postImageList =new ArrayList<>();

    public NewPostObject(String userImageUrl, String userName, String postContent, String postDate, String postTime, ArrayList<String> postImageList) {
        this.userImageUrl = userImageUrl;
        this.userName = userName;
        this.postContent = postContent;
        this.postDate = postDate;
        this.postTime = postTime;
        this.postImageList = postImageList;
    }

    public NewPostObject() {
//        this.profilePic = profilePic;
//        this.userName = userName;
//        this.postContent = postContent;
//        this.postImageList = postImageList;
//        this.attachmentList = attachmentList;
    }

    public List<String> getPostImageList() {
        return postImageList;
    }

    public void setPostImageList(ArrayList<String> postImageList) {
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

//    private ArrayList<File> attachmentList;
//
//    public CircleImageView getProfilePic() {
//        return profilePic;
//    }
//
//    public void setProfilePic(CircleImageView profilePic) {
//        this.profilePic = profilePic;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getPostContent() {
//        return postContent;
//    }
//
//    public void setPostContent(String postContent) {
//        this.postContent = postContent;
//    }
//
//    public ArrayList<String> getPostImageList() {
//        return postImageList;
//    }
//
//    public void setPostImageList(ArrayList<String> postImageList) {
//        this.postImageList = postImageList;
//    }
//
//    public void setPostDate(String postDate) {
//        this.postDate = postDate;
//    }
//
//    public void setPostTime(String postTime) {
//        PostTime = postTime;
//    }
//
//    public ArrayList<File> getAttachmentList() {
//        return attachmentList;
//    }
//
//    public void setAttachmentList(ArrayList<File> attachmentList) {
//        this.attachmentList = attachmentList;
//    }
}
