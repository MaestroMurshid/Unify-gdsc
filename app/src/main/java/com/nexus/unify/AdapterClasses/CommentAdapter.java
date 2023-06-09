package com.nexus.unify.AdapterClasses;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nexus.unify.ModelClasses.Comment;
import com.nexus.unify.ModelClasses.User;
import com.nexus.unify.R;


import java.util.List;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private final Context mContext;
    private final List<Comment> mComment;
    private FirebaseUser firebaseUser;


    public CommentAdapter(Context mContext, List<Comment> mComment) {
        this.mContext = mContext;
        this.mComment = mComment;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.comment_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, int i) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
      final   Comment comment=mComment.get(i);

 try {
     if (comment.getType().equals("nrm")){
         getUserinfo(viewholder.image_profile,viewholder.username,comment.getPublisher());
     }
 }catch (Exception e){
     getUserinfoanms(viewholder.image_profile,viewholder.username,comment.getPublisher());
 }
        viewholder.comment.setText(comment.getComment());

        viewholder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mComment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image_profile;
        public TextView username,comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_profile=itemView.findViewById(R.id.image_profile);
            username=itemView.findViewById(R.id.username);


            comment=itemView.findViewById(R.id.comment);

        }
    }
    private void getUserinfo(ImageView imageView, TextView username, String publisherid){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("profiles").child(publisherid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                Glide.with(mContext).load(user.getImg1()).into(imageView);
                username.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void getUserinfoanms(ImageView imageView, TextView username, String publisherid){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("profiles").child(publisherid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                Glide.with(mContext).load(user.getAnmsimg()).into(imageView);
                username.setText(user.getAnmsname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}