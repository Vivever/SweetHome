package com.example.home.ui.newpost;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.home.ui.home.PostObject;

public class NewPostViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<PostObject> postObject;

    public NewPostViewModel() {
        postObject= new MutableLiveData<>();
    }

    public MutableLiveData<PostObject> getPostObject() {
        return postObject;
    }
}
