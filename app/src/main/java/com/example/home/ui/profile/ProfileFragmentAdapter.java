package com.example.home.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.home.R;
import com.example.home.payment.PaymentActivity;
import com.example.home.ui.home.PostObject;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragmentAdapter extends RecyclerView.Adapter<ProfileFragmentAdapter.MyViewHolder>{

    TextView userProfileName;
    ExpandableLayout bioLayout;
    private List<PostObject> mPostObject;
    private Context mContext;

    ProfileFragmentAdapter(Context context, List<PostObject> postObject) {
        mContext=context;
        mPostObject=postObject;
        Log.i("TAG","SEXY");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        mPostObject=new ArrayList<>();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Log.i("TAG","HI "+mPostObject.size()+"");
        holder.userName.setText(mPostObject.get(i).getUserName());
        holder.userPost.setText(mPostObject.get(i).getPostContent());
        holder.postDate.setText(mPostObject.get(i).getPostDate());
        holder.postContentLayout.removeAllViewsInLayout();
        for(String img:mPostObject.get(i).getPostImageList()){
            ImageView curImage=new ImageView(mContext);
            curImage.setImageBitmap(StringToBitMap(img));
            curImage.setLayoutParams(new LinearLayout.LayoutParams(300,300));
            holder.postContentLayout.addView(curImage);
        }
        setAllButtons(holder,i);

//     //    Set the image
//        RequestOptions defaultOptions = new RequestOptions()
//                .error(R.drawable.ic_launcher_background);
//        Glide.with(mContext)
//                .setDefaultRequestOptions(defaultOptions)
//                .load(mPostObject.get(i).getUserImageUrl())
//                .into(holder.userImage);

    }

    private Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void setAllButtons(MyViewHolder holder , final int i) {
        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share= new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT,mPostObject.get(i).getUserName()+"\n "+mPostObject.get(i).getPostDate()+"\n"+mPostObject.get(i).getPostContent());
                mContext.startActivity(share);
            }
        });

        holder.donateMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, PaymentActivity.class));
            }
        });
    }
    @Override
    public int getItemCount() {
        return mPostObject.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView userName,userPost,postDate,postTime;
        private String userImageUrl;
        private CircleImageView userImage;
        private ImageButton donateMoney;
        private Button likeButton,commentButton,shareButton;
        private LinearLayout postContentLayout;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.tv_title_post_row);
            userPost=itemView.findViewById(R.id.tv_post_content_post_row);
            donateMoney=itemView.findViewById(R.id.donate_money_post);
            likeButton=itemView.findViewById(R.id.bt_like_post_row);
            commentButton=itemView.findViewById(R.id.bt_comment_post_row);
            shareButton=itemView.findViewById(R.id.bt_share_post_row);
            postContentLayout=itemView.findViewById(R.id.post_item_content_layout);
            postDate=itemView.findViewById(R.id.post_creation_date);

            //Main Profile Items
        }
    }
}
