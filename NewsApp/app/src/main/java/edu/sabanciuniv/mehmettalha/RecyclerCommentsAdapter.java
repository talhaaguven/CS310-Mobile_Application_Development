package edu.sabanciuniv.mehmettalha;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutorService;

import edu.sabanciuniv.mehmettalha.models.CommentByNewsItem;
import edu.sabanciuniv.mehmettalha.models.NewsByCategoryItem;

public class RecyclerCommentsAdapter extends RecyclerView.Adapter<RecyclerCommentsAdapter.ViewHolder> {
    private List<CommentByNewsItem> commentByNewsItems;
    private Activity activity;

    public RecyclerCommentsAdapter(FragmentActivity activity, List<CommentByNewsItem> commentByNewsItems) {
        this.commentByNewsItems = commentByNewsItems;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerCommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_comments, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerCommentsAdapter.ViewHolder holder, int position) {
        holder.tvName.setText(commentByNewsItems.get(holder.getAdapterPosition()).getName());
        holder.tvText.setText(commentByNewsItems.get(holder.getAdapterPosition()).getText());


    }

    @Override
    public int getItemCount() {
        return commentByNewsItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvText = itemView.findViewById(R.id.tvText);
        }
    }
}
