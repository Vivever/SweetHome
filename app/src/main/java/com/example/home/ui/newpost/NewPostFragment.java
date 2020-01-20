package com.example.home.ui.newpost;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home.GlobalVar;
import com.example.home.MainActivity;
import com.example.home.R;
import com.example.home.signup_login.SignUpLogInActivity;
import com.example.home.signup_login.User;
import com.example.home.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewPostFragment extends Fragment implements View.OnClickListener {

    private NewPostViewModel mViewModel;
    private EditText newPostContent;
    private ImageButton camera,videoRecorder,attachFile,sendPost;
    private CircleImageView profilePic;
    private TextView userName;
    private LinearLayout postLayout;
    private NewPostObject curPost;
    private DatabaseReference mDataBaseRef;
    private ProgressBar progressBar;
    private int key;
    private ProgressDialog progressDialogPosting;
    private String name="";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(NewPostViewModel.class);
        if(GlobalVar.currUser==null){
            startActivity(new Intent(getActivity(), SignUpLogInActivity.class));
        }
        View root= inflater.inflate(R.layout.fragment_new_post, container, false);
        curPost=new NewPostObject();
        GlobalVar.toolbar.setTitle("New Post");
        findViewById(root);
        mDataBaseRef= GlobalVar.currUserDataBaseRef;
//        GlobalVar.currUserDataBaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                User user= dataSnapshot.child("UserInfo").getValue(User.class);
//                key= Objects.requireNonNull(user).getNoOfPost();
//                name=user.getUserName();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity(),"Your Post can not be updated, Please try again",Toast.LENGTH_LONG).show();
//            }
//        });
//        if(GlobalVar.currentUserData!=null) {
            key = GlobalVar.currentUserData.getNoOfPost();
            name = GlobalVar.currentUserData.getUserName();
            userName.setText(name);

        sendPost.setOnClickListener(this);
        camera.setOnClickListener(this);
        return root;
    }

    private void findViewById(View root) {
        profilePic=root.findViewById(R.id.new_post_img);
        userName=root.findViewById(R.id.new_post_user_name);
        newPostContent=root.findViewById(R.id.new_post_content);
        camera=root.findViewById(R.id.new_post_camera);
        videoRecorder=root.findViewById(R.id.new_post_recorder);
        attachFile=root.findViewById(R.id.new_post_attachment);
        sendPost=root.findViewById(R.id.new_post_send);
        postLayout=root.findViewById(R.id.post_scrollview_layout);
    }

    @Override
    public void onClick(final View v) {
        if(v==camera){
            Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,0);
        }
        else if(v==sendPost){
            if(GlobalVar.currUser==null){
                Toast.makeText(getActivity(),"Your are not logged in",Toast.LENGTH_SHORT).show();
                return;
            }

            if(newPostContent.getText().toString().trim()==""&&curPost.getPostImageList().size()==0){
                Toast.makeText(getActivity(),"Your Post is empty.",Toast.LENGTH_SHORT).show();
                return;
            }
            addAllPostDetail();
            progressDialogPosting = ProgressDialog.show(getContext(), "",
                    "Posting. Please wait...", true);
            if(mDataBaseRef!=null)
                mDataBaseRef.child("Post").child(Integer.toString(key + 1)).setValue(curPost).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        GlobalVar.currUserDataBaseRef.child("UserInfo").child("noOfPost").setValue(key + 1);
//                        PostObject newPost = new PostObject(name, curPost.getPostContent(), "", "", "");
                        Toast.makeText(getActivity(), "Your Post is updated", Toast.LENGTH_LONG).show();
                        assert getFragmentManager() != null;
                        progressDialogPosting.dismiss();
                        ((InputMethodManager) Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE))).hideSoftInputFromWindow(v.getWindowToken(),0);
                        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();
//                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }
                });
            }
    }

    private void addAllPostDetail() {
        curPost.setUserName(name);
        curPost.setPostContent(newPostContent.getText().toString().trim());
        curPost.setPostDate(SimpleDateFormat.getDateInstance().format(GlobalVar.calendar.getTime()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap= (Bitmap)data.getExtras().get("data");
        ImageView curImage=new ImageView(getContext());
        curImage.setImageBitmap(bitmap);
        curImage.setLayoutParams(new LinearLayout.LayoutParams(300,300));
        postLayout.addView(curImage);
        curPost.getPostImageList().add(bitMapToString(bitmap));
        System.out.println();
    }


    public String bitMapToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayOutputStream);
        byte [] b=byteArrayOutputStream.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
        @Override
    public void onStart() {
        userName.setText(name);
        super.onStart();
    }
    @Override
    public void onStop() {

        super.onStop();
    }
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(NewPostViewModel.class);
//        // TODO: Use the ViewModel
//    }

}
