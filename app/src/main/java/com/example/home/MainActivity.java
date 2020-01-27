package com.example.home;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.home.signup_login.User;
import com.example.home.ui.home.PostObject;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Objects;


public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
         GlobalVar.toolbar=findViewById(R.id.toolbar);
         GlobalVar.appBarLayout=findViewById(R.id.app_bar);
        setSupportActionBar(GlobalVar.toolbar);
        if (GlobalVar.mDataBase==null)
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        setGlobalVariables();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search,R.id.navigation_new_post, R.id.navigation_notifications,R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sign_out) {
            if (GlobalVar.currUser != null) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "You have been signed out from your account", Toast.LENGTH_LONG).show();
                GlobalVar.currUser = FirebaseAuth.getInstance().getCurrentUser();
                return true;
            } else
                Toast.makeText(this, "You are not logged in.", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    void setGlobalVariables(){
        dummyPost();
        GlobalVar.calendar= Calendar.getInstance();
        GlobalVar.currUser=FirebaseAuth.getInstance().getCurrentUser();
        GlobalVar.mAuth=FirebaseAuth.getInstance();
        GlobalVar.mDataBase=FirebaseDatabase.getInstance();
        GlobalVar.mainDataBaseRef=GlobalVar.mDataBase.getReference();
        if(GlobalVar.currUser!=null)
        GlobalVar.currUserDataBaseRef=GlobalVar.mDataBase.getReference().child("User").child(Objects.requireNonNull(GlobalVar.mAuth.getCurrentUser()).getUid());
        if(GlobalVar.currUserDataBaseRef!=null) {
            DatabaseReference ref = GlobalVar.currUserDataBaseRef.child("Post");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    GlobalVar.currUserPosts.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren())
                        GlobalVar.currUserPosts.add(ds.getValue(PostObject.class));
                    Collections.reverse(GlobalVar.currUserPosts);

                    System.out.println("DataDownloaded");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            GlobalVar.currUserDataBaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    GlobalVar.currentUserData= dataSnapshot.child("UserInfo").getValue(User.class);
//                    key= Objects.requireNonNull(user).getNoOfPost();
//                    name=user.getUserName();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

        }
    }
    private void dummyPost(){
        GlobalVar.currUserPosts.add(new PostObject("UserName","This is dummy Post , You can add new Post by clicking +","","","",new ArrayList<String>()));
    }
}
