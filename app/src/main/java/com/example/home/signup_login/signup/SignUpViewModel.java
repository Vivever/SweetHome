package com.example.home.signup_login.signup;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.home.signup_login.User;

public class SignUpViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public MutableLiveData<String> emailAddress = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> confirmPassword = new MutableLiveData<>();

    private MutableLiveData<User> userMutableLiveData;

    public MutableLiveData<User> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClick(View view) {

        User sign_upUser = new User(userName.getValue(),emailAddress.getValue(), password.getValue(),confirmPassword.getValue(),false,0,"");

        userMutableLiveData.setValue(sign_upUser);

    }
}
