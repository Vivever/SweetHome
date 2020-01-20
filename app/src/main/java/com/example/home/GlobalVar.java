package com.example.home;

import androidx.appcompat.widget.Toolbar;

import com.example.home.signup_login.User;
import com.example.home.ui.home.PostObject;
import com.example.home.ui.profile.ProfileObject;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class GlobalVar {

    public static FirebaseAuth mAuth = null;
    public static FirebaseUser currUser = null;
    public static FirebaseDatabase mDataBase=null;
    public static DatabaseReference mainDataBaseRef=null,currUserDataBaseRef=null;
    public static ArrayList<PostObject> currUserPosts=new ArrayList<>();
    public static ProfileObject userProfile=new ProfileObject();
    public static Toolbar toolbar;
    public static AppBarLayout appBarLayout;
    public static User currentUserData;
    public static int totalNoOfPost=0;
    public static Calendar calendar;
}