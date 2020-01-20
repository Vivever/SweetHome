package com.example.home.ui.profile;

import androidx.lifecycle.MutableLiveData;

class ProfileFragmentRepository {
    private static ProfileFragmentRepository instance;
    private ProfileObject profile;
    private static ProfileFragmentRepository getInstance(){
        if(instance==null)
            instance=new ProfileFragmentRepository();
        return instance;
    }

     MutableLiveData<ProfileObject> getUserPosts(){
         setUserProfile();
         MutableLiveData<ProfileObject> data = new MutableLiveData<>();
         data.setValue(profile);
         return data;
     }

    private void setUserProfile() {
    }
}
