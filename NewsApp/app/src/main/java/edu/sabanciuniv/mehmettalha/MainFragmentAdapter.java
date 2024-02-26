package edu.sabanciuniv.mehmettalha;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import edu.sabanciuniv.mehmettalha.models.NewsCategory;

public class MainFragmentAdapter extends FragmentStateAdapter {

    private NewsCategory newsCategory;

    public MainFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, NewsCategory newsCategory) {
        super(fragmentManager, lifecycle);

        this.newsCategory = newsCategory;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt("catid", newsCategory.getNewsCategoryItems().get(position).getId());

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return newsCategory.getNewsCategoryItems().size();
    }
}
