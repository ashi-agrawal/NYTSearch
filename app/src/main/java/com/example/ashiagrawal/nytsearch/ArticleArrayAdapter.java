package com.example.ashiagrawal.nytsearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ashiagrawal on 6/20/16.
 */
public class ArticleArrayAdapter extends RecyclerView.Adapter<ArticleArrayAdapter.ViewHolder> {

    private ArrayList<Article> articles;


    public ArticleArrayAdapter(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.ivImage) ImageView ivImage;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ArticleArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_article_result, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticleArrayAdapter.ViewHolder viewHolder, int position) {
        Article article = articles.get(position);
        TextView tvTitle = viewHolder.tvTitle;
        tvTitle.setText(article.getHeadline());
        ImageView ivImage = viewHolder.ivImage;
        String thumbnail = article.getThumbnail();
        if (!TextUtils.isEmpty(thumbnail)){
            Glide.with(ivImage.getContext()).load(thumbnail).into(ivImage);
        }
    }

    @Override
    public int getItemCount() {
        if (articles == null) return 0;
        else return articles.size();
    }

}
