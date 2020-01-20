package com.example.home.signup_login;

import android.util.Patterns;

public class User {
    private   String userName,userEmail,userPassword,confirmPassword,profileBitmap;
    private int noOfPost;

    private boolean isOrganization=false;

    public User(){

    }
    public User(String userName, String userEmail, String userPassword, String confirmPassword,boolean isOrganization,int noOfPost) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.confirmPassword = confirmPassword;
        this.isOrganization=isOrganization;
        this.noOfPost=noOfPost;
    }
    public User(String userName, String userEmail, String userPassword, String confirmPassword,boolean isOrganization,int noOfPost,String profileBitmap) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.confirmPassword = confirmPassword;
        this.isOrganization=isOrganization;
        this.noOfPost=noOfPost;
        this.profileBitmap=profileBitmap;
    }

    public int getNoOfPost() {
        return noOfPost;
    }

    public boolean isOrganization() {
        return isOrganization;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getProfileBitmap() {
        return profileBitmap;
    }

    public void setProfileBitmap(String profileBitmap) {
        this.profileBitmap = profileBitmap;
    }

    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getUserEmail()).matches();
    }
}
