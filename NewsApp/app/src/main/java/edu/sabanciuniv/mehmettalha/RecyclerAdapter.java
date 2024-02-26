package edu.sabanciuniv.mehmettalha;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

import edu.sabanciuniv.mehmettalha.models.NewsByCategoryItem;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<NewsByCategoryItem> newsByCategoryItems;
    private Activity activity;

    public RecyclerAdapter(FragmentActivity activity, List<NewsByCategoryItem> newsByCategoryItems) {
        this.newsByCategoryItems = newsByCategoryItems;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_news, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        String date = newsByCategoryItems.get(holder.getAdapterPosition()).getDate();
        String[] date2 = date.split("T")[0].split("-");
        holder.tvDate.setText(date2[2] + "/" + date2[1] + "/" + date2[0]);
        holder.tvTitle.setText(newsByCategoryItems.get(holder.getAdapterPosition()).getTitle());

        NewsApp app = (NewsApp) activity.getApplication();

        holder.downloadImage(app.srv, newsByCategoryItems.get(holder.getAdapterPosition()).getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, NewsDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("news", (Serializable) newsByCategoryItems.get(holder.getAdapterPosition()));
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsByCategoryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvDate, tvTitle;
        boolean imageDownloaded;

        Handler imgHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                Bitmap img = (Bitmap) msg.obj;
                imageView.setImageBitmap(img);
                imageDownloaded = true;
                return true;
            }
        });

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }

        public void downloadImage(ExecutorService srv, String path) {
            if (!imageDownloaded) {
                NewsAppRepository repo = new NewsAppRepository();
                repo.downloadImage(srv, imgHandler, path);
            }
        }

    }
}
