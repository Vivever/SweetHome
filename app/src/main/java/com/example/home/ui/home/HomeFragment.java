package com.example.home.ui.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home.GlobalVar;
import com.example.home.R;
import com.google.android.gms.common.GoogleApiAvailabilityLight;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private HomeFragmentAdapter homeFragmentAdapter;
    private RecyclerView mRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        GlobalVar.toolbar.setTitle("Home");
//        GlobalVar.toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_account_circle_black_24dp));
        GlobalVar.toolbar.setTitleMarginStart(250);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.init();
        homeViewModel.getUserPost().observe(this, new Observer<List<PostObject>>() {
            @Override
            public void onChanged(List<PostObject> postObjectList) {
                homeFragmentAdapter.notifyDataSetChanged();
            }
        });
//        MainActivity.toolBarTitle.setText("Home");

         mRecyclerView=root.findViewById(R.id.home_recycler_view);
        initRecyclerView();
        return root;
    }
    private void initRecyclerView(){
        homeFragmentAdapter = new HomeFragmentAdapter(getActivity(), homeViewModel.getUserPost().getValue());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(homeFragmentAdapter);
    }
}