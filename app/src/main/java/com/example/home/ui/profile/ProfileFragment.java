package com.example.home.ui.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.home.GlobalVar;
import com.example.home.R;
import com.example.home.signup_login.SignUpLogInActivity;
import com.example.home.signup_login.User;
import com.example.home.ui.home.HomeViewModel;
import com.example.home.ui.home.PostObject;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment implements View.OnClickListener {
    int PICK_PROFILE_IMG_REQUEST = 101;
    private TextView name,userBio,userAddress,userDonations;
    private ImageButton changeUserProfile;
    private HomeViewModel homeViewModel;
    private ProfileFragmentAdapter profileFragmentAdapter;
    private RecyclerView mRecyclerView;
    private ExpandableRelativeLayout bioLayout,addressLayout,donationLayout;
    private ArrayList<String> donatedMoney=new ArrayList<>();
    private ArrayList<String> donor=new ArrayList<>();
    private CircleImageView userProfilePic;
    private Bitmap bitmap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        if(GlobalVar.currUser==null){
            startActivity(new Intent(getActivity(), SignUpLogInActivity.class));
//            getActivity().finish();
        }
        final View root = inflater.inflate(R.layout.fragment_profile, container, false);
        add();
        findViewById(root);
        bioLayout.collapse();
        addressLayout.collapse();
        donationLayout.collapse();
        userBio.setOnClickListener(this);
        userAddress.setOnClickListener(this);
        userDonations.setOnClickListener(this);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.init();
        homeViewModel.getUserPost().observe(this, new Observer<List<PostObject>>() {
            @Override
            public void onChanged(List<PostObject> postObjectList) {
                profileFragmentAdapter.notifyDataSetChanged();
            }
        });
        if(GlobalVar.currUser!=null)
            setUserData();
        initRecyclerView();
        initRecyclerViewForDonation(root);
        return root;
    }
    private void initRecyclerView(){
        profileFragmentAdapter = new ProfileFragmentAdapter(getActivity(), homeViewModel.getUserPost().getValue());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(profileFragmentAdapter);
    }

    private void initRecyclerViewForDonation(View view){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView=view.findViewById(R.id.donation_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.smoothScrollToPosition(0);
        DonationAdapter donationAdapter= new DonationAdapter(donatedMoney,donor);
        recyclerView.setAdapter(donationAdapter);
    }
    @Override
    public void onClick(View v) {
//        if(v.getId()==R.id.change_profile_pic)
//            openFileChooser();
        switch (v.getId()) {
            case R.id.user_bio: {
                bioLayout.toggle();
                addressLayout.collapse();
                donationLayout.collapse();
            }
            case R.id.user_address: {
                addressLayout.toggle();
                bioLayout.collapse();
                donationLayout.collapse();
            }
            case R.id.user_donations: {
                if (!donationLayout.isExpanded()&&v.getId()==R.id.user_donations)
                    Toast.makeText(getContext(),"Please Scroll Horizontally",Toast.LENGTH_SHORT).show();
                donationLayout.toggle();
                addressLayout.collapse();
                bioLayout.collapse();
            }

        }
    }
    private void findViewById(View root){
        bioLayout=root.findViewById(R.id.bio_layout);
        addressLayout=root.findViewById(R.id.address_layout);
        donationLayout=root.findViewById(R.id.donation_layout);
        name=root.findViewById(R.id.user_profile_name);
        userAddress=root.findViewById(R.id.user_address);
        userDonations=root.findViewById(R.id.user_donations);
        userBio=root.findViewById(R.id.user_bio);
        changeUserProfile=root.findViewById(R.id.change_profile_pic);
        userProfilePic=root.findViewById(R.id.user_profile_image);
        mRecyclerView=root.findViewById(R.id.user_own_post_recyclerview);

    }
    private void setUserData(){
        User user=GlobalVar.currentUserData;
        name.setText(user.getUserName());
//        if(user.getProfileBitmap()!="")
//            userProfilePic.setImageBitmap(StringToBitMap(user.getProfileBitmap()));
    }
    private void openFileChooser(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//
//        if(data!=null) {
//            Uri imageUri = data.getData();
////            Bitmap bitmap = null;
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            bitmap= (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            userProfilePic.setImageBitmap(bitmap);

//         bitmap= (Bitmap)data.getExtras().get("data");
//        userProfilePic.setImageBitmap(bitmap);
    }

    @Override
    public void onStop() {
//        if(GlobalVar.currentUserData!=null) {
//            final DatabaseReference ref = GlobalVar.currUserDataBaseRef.child("User").child(GlobalVar.currUser.getUid()).child("UserInfo");
//            ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    User user = dataSnapshot.getValue(User.class);
////                    user.setProfileBitmap(bitMapToString(bitmap));
//                    ref.setValue(user);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }
        super.onStop();
    }
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
    public String bitMapToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayOutputStream);
        byte [] b=byteArrayOutputStream.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
    private void add(){
        donatedMoney.add("67$");
        donor.add("Vivek");
        donatedMoney.add("844$");
        donor.add("Shubham");
        donatedMoney.add("10$");
        donor.add("Andrew");
        donatedMoney.add("670$");
        donor.add("Patil");
        donatedMoney.add("56$");
        donor.add("Vivek");
        donatedMoney.add("28$");
        donor.add("Vivek");
    }
}
