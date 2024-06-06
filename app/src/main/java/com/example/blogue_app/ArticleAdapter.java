package com.example.blogue_app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private final Context context;
    private final List<Article> articleList;
    private final OnArticleClickListener listener;

    public ArticleAdapter(Context context, List<Article> articleList, OnArticleClickListener listener) {
        this.context = context;
        this.articleList = articleList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.article_item, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article currentArticle = articleList.get(position);
        holder.bind(currentArticle);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView;
        ImageView deleteImageView;

        ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            deleteImageView = itemView.findViewById(R.id.deleteImageView);
            itemView.setOnClickListener(this);
            deleteImageView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Article article = articleList.get(position);
                    listener.onDeleteClick(article);
                }
            });
        }

        void bind(Article article) {
            titleTextView.setText(article.getTitle());
            int borderColor = getBorderColor(article.getStatus());
            Drawable background = ContextCompat.getDrawable(context, R.drawable.article_border);
            if (background != null) {
                background.setTint(borderColor);
                itemView.setBackground(background);
            }
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Article clickedArticle = articleList.get(position);
                listener.onArticleClick(clickedArticle);
            }
        }

        private int getBorderColor(String status) {
            switch (status) {
                case "Todo":
                    return ContextCompat.getColor(context, R.color.colorTodo);
                case "InProgress":
                    return ContextCompat.getColor(context, R.color.colorInProgress);
                case "Done":
                    return ContextCompat.getColor(context, R.color.colorDone);
                case "Bug":
                    return ContextCompat.getColor(context, R.color.colorBug);
                default:
                    return ContextCompat.getColor(context, android.R.color.transparent);
            }
        }
    }

    public interface OnArticleClickListener {
        void onArticleClick(Article article);
        void onDeleteClick(Article article);
    }
}
