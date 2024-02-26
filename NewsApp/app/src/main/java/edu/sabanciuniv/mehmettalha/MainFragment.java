package edu.sabanciuniv.mehmettalha;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import edu.sabanciuniv.mehmettalha.models.NewsByCategory;
import edu.sabanciuniv.mehmettalha.models.NewsByCategoryItem;
import edu.sabanciuniv.mehmettalha.models.NewsCategory;

public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressDialog dialog;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        int catid = bundle.getInt("catid");

        dialog.show();
        NewsAppRepository repo = new NewsAppRepository();
        repo.getAllNewsByCategories(((NewsApp) getActivity().getApplication()).srv, dataHandler, catid);
    }

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            NewsByCategory data = (NewsByCategory)msg.obj;

            if(data.getServiceMessageCode() == 1) {
                List<NewsByCategoryItem> newsByCategoryItems = data.getNewsByCategoryItems();

                RecyclerAdapter adapter = new RecyclerAdapter(getActivity(), newsByCategoryItems);
                recyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            }, 1500);

            return true;
        }
    });
}