package com.example.home.signup_login.signup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.home.GlobalVar;
import com.example.home.MainActivity;
import com.example.home.R;
import com.example.home.signup_login.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpFragment extends Fragment {

    private SignUpViewModel signUpViewModel;
    private EditText userName,userEmail,password,confirmPassword;
    private ToggleButton isIndividual;
    private Button signUp,logIn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDataBase;
    private DatabaseReference mDataBaseRef;
    private ProgressDialog progressDialog;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.sign_up_fragment, container, false);
        mAuth=FirebaseAuth.getInstance();
        findViewById(root);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkDetails())
                    return;
                progressDialog=ProgressDialog.show(getContext(),"Sign Up","Please Wait");
                mAuth.createUserWithEmailAndPassword(userEmail.getText().toString().trim(),password.getText().toString().trim()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Authentication Successful." ,
                                    Toast.LENGTH_SHORT).show();
                            mDataBase=FirebaseDatabase.getInstance();
                            mDataBaseRef=mDataBase.getReference().child("User").child(mAuth.getCurrentUser().getUid()).child("UserInfo");
                            GlobalVar.currUserDataBaseRef=mDataBaseRef.child(mAuth.getCurrentUser().getUid());
                            User user=new User(userName.getText().toString(),userEmail.getText().toString(),"","",isIndividual.isChecked(),0,"");
                            mDataBaseRef.setValue(user);

                            startActivity(new Intent(getActivity(), MainActivity.class));

                        }
                    }
                });
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        signUpViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {

            }
        });
    }

    private boolean checkDetails(){
        if (TextUtils.isEmpty(userName.getText())){
            userName.setError("Write Your Name");
            return false;
        }
        if (TextUtils.isEmpty(userEmail.getText())){
            userEmail.setError("Write Your Email");
            return false;
        }

        if (TextUtils.isEmpty(password.getText())||TextUtils.isEmpty(confirmPassword.getText())){
            password.setError("Write Your Name");
            confirmPassword.setError("Enter Your Password");
            return false;
        }
        if (!password.getText().toString().equals(confirmPassword.getText().toString())){
            confirmPassword.setError("Confirm Your Password");
            return false;
        }
        return true;
    }
    private void findViewById(View root){
        userName=root.findViewById(R.id.sign_up_userName);
        userEmail=root.findViewById(R.id.sign_up_userMail);
        password=root.findViewById(R.id.sign_up_password);
        confirmPassword=root.findViewById(R.id.sign_up_confirm_password);
        signUp=root.findViewById(R.id.sign_up_sign_up);
        logIn=root.findViewById(R.id.sign_up_login);
        isIndividual=root.findViewById(R.id.ind_or_org);
    }
}
