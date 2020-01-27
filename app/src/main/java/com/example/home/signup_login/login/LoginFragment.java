package com.example.home.signup_login.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.home.GlobalVar;
import com.example.home.MainActivity;
import com.example.home.R;
import com.example.home.signup_login.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment  {

    private LoginViewModel loginViewModel;
    private EditText userEmail,password;
    private Button signUp,logIn;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.login_fragment, container, false);
        findViewById(root);
        mAuth=FirebaseAuth.getInstance();
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkDetails())
                    return;
                progressDialog= ProgressDialog.show(getContext(),"Logging In","Please Wait");
                mAuth.signInWithEmailAndPassword(userEmail.getText().toString().trim(),password.getText().toString().trim()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
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
                            GlobalVar.mAuth=mAuth;
                            GlobalVar.currUser=mAuth.getCurrentUser();
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        }
                    }
                });

            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Please Swap Right",Toast.LENGTH_LONG).show();
//                Fragment fragment=new SignUpFragment();
//                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.pager,fragment);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        // TODO: Use the ViewModel
        loginViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {

            }
        });
    }
    private boolean checkDetails(){
        if (TextUtils.isEmpty(userEmail.getText())){
            userEmail.setError("Write Your Email");
            return false;
        }

        if (TextUtils.isEmpty(password.getText())){
            password.setError("Write Your Name");
            return false;
        }
        return true;
    }

    private void findViewById(View root){
        userEmail=root.findViewById(R.id.log_in_user_email);
        password=root.findViewById(R.id.log_in_password);
        signUp=root.findViewById(R.id.log_in_sign_up);
        logIn=root.findViewById(R.id.log_in_log_in);
    }

}
