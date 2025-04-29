package com.example.tootsay;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tootsay.ui.home.HomeFragment;
import com.example.tootsay.ui.home.NewsFullActivity;
import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class rcadapter extends RecyclerView.Adapter<rcadapter.NewsViewholder> {
    List<Article> articleList;

    public rcadapter(List<Article> articleList){this.articleList = articleList;}
    @NonNull
    @Override
    public NewsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return new NewsViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewholder holder, int position) {
        Article article = articleList.get(position);

        holder.title.setText(article.getTitle());
        holder.source.setText(article.getSource().getName());

        Picasso.get().load(article.getUrlToImage())
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image)
                .into(holder.image2);

        holder.itemView.setOnClickListener((v->{
            Intent intent=new Intent(v.getContext(), NewsFullActivity.class);
            intent.putExtra("url",article.getUrl());
            v.getContext().startActivity(intent);
        }));

    }
    public void updateData(List<Article> data){
        articleList.clear();
        articleList.addAll(data);
    }

    @Override
    public int getItemCount() {
      return articleList.size();
    }

    public class NewsViewholder extends RecyclerView.ViewHolder {

        TextView title, source;
        ImageView image2;
        public NewsViewholder(@NonNull View itemView) {

            super(itemView);
            title = itemView.findViewById(R.id.article_title);
            source = itemView.findViewById(R.id.source);
            image2 = itemView.findViewById(R.id.imagesrc);

        }

    }
}
