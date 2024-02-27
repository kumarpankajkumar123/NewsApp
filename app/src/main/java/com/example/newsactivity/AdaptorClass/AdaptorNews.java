package com.example.newsactivity.AdaptorClass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsactivity.NewsFull;
import com.example.newsactivity.R;
import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdaptorNews extends RecyclerView.Adapter<AdaptorNews.NewsVieHolder> {

    List<Article> list;

    public AdaptorNews(List<Article> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AdaptorNews.NewsVieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_recycler_activity,parent,false);
        return new NewsVieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptorNews.NewsVieHolder holder, int position) {
            Article article =  list.get(position);
            holder.text_tittle.setText(article.getTitle());
            holder.text_source.setText(article.getSource().getName());
        Picasso.get().load(article.getUrlToImage()).error(R.drawable.baseline_hide_image_24)
                .placeholder(R.drawable.baseline_hide_image_24).into(holder.design_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewsFull.class);
                intent.putExtra("url",article.getUrl());
                view.getContext().startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public void upDateData(List<Article> article) {
        list.clear();
        list.addAll(article);
    }

    public class NewsVieHolder extends RecyclerView.ViewHolder {

        ImageView design_image;
        TextView text_tittle,text_source;
        public NewsVieHolder(@NonNull View itemView) {
            super(itemView);

            design_image = (ImageView) itemView.findViewById(R.id.design_image);
            text_tittle = (TextView) itemView.findViewById(R.id.text_title);
            text_source = (TextView) itemView.findViewById(R.id.text_source);

        }
    }
}
