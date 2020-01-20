package com.example.home.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<PostObject>>userPost;

    public HomeViewModel() {
    }

    public LiveData<List<PostObject>>getUserPost() {
        return userPost;
    }
    public void init(){
        if(userPost != null){
            return;
        }
        HomeFragmentRepositry homeFragmentRepositry = HomeFragmentRepositry.getInstance();
        userPost = homeFragmentRepositry.getUserPosts();
    }
}