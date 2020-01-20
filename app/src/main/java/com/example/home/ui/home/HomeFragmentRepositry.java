package com.example.home.ui.home;

import androidx.lifecycle.MutableLiveData;

import com.example.home.GlobalVar;

import java.util.ArrayList;
import java.util.List;

class HomeFragmentRepositry {
    private static HomeFragmentRepositry instance;
    private ArrayList<PostObject> postObjectDataSet = new ArrayList<>();

    static HomeFragmentRepositry getInstance(){
        if(instance == null){
            instance = new HomeFragmentRepositry();
        }
        return instance;
    }

    // Pretend to get data from a webservice or online source
    MutableLiveData<List<PostObject>> getUserPosts(){
        setUserPosts();
        MutableLiveData<List<PostObject>> data = new MutableLiveData<>();
        data.setValue(postObjectDataSet);
        return data;
    }

    private void setUserPosts(){
        postObjectDataSet= GlobalVar.currUserPosts;

    }
}
