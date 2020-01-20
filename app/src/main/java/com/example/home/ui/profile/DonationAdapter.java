package com.example.home.ui.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home.R;

import java.util.ArrayList;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.ViewHolder>{
    private ArrayList<String> donatedMoney,donor;
    public DonationAdapter(ArrayList<String> donatedMoney, ArrayList<String> donor) {
        this.donatedMoney = donatedMoney;
        this.donor = donor;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.donation_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.donatedAmount.setText(donatedMoney.get(position));
        holder.donor.setText(donor.get(position));
    }

    @Override
    public int getItemCount() {
        return donatedMoney.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView donatedAmount, donor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            donatedAmount=itemView.findViewById(R.id.donated_amount);
            donor =itemView.findViewById(R.id.donor);
        }
    }
}
