package com.example.blogapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<BlogClass> blogList;

    public MyAdapter(Context context, List<BlogClass> blogList) {
        this.context = context;
        this.blogList = blogList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.recTitle.setText(blogList.get(position).getTitle());
        holder.recDescription.setText(blogList.get(position).getDescription());
        holder.recID.setText(blogList.get(position).getId());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra("ID",blogList.get(holder.getAdapterPosition()).getId());
                intent.putExtra("TITLE",blogList.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("DESCRIPTION",blogList.get(holder.getAdapterPosition()).getDescription());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    TextView recTitle ,recDescription, recID;
    CardView recCard;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recCard = itemView.findViewById(R.id.recCard);
        recTitle = itemView.findViewById(R.id.recTitle);
        recID = itemView.findViewById(R.id.recID);
        recDescription = itemView.findViewById(R.id.recDescription);
    }
}