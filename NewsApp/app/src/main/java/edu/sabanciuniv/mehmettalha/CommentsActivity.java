package edu.sabanciuniv.mehmettalha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import edu.sabanciuniv.mehmettalha.models.CommentByNewsItem;
import edu.sabanciuniv.mehmettalha.models.CommentsByNews;
import edu.sabanciuniv.mehmettalha.models.NewsByCategoryItem;
import edu.sabanciuniv.mehmettalha.models.NewsCategory;

public class CommentsActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private RecyclerView recyclerView;

    private int newsid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Bundle bundle = getIntent().getExtras();
        newsid = bundle.getInt("newsid");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle("Comments");
        myToolbar.inflateMenu(R.menu.menu_news_details);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(CommentsActivity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(CommentsActivity.this, DividerItemDecoration.VERTICAL));

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        dialog.show();
        NewsAppRepository repo = new NewsAppRepository();
        repo.getCommentsByNewsId(((NewsApp) getApplication()).srv, dataHandler, newsid);
    }

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            CommentsByNews data = (CommentsByNews) msg.obj;

            if(data.getServiceMessageCode() == 1) {
                List<CommentByNewsItem> commentByNewsItems = data.getCommentByNewsItem();

                RecyclerCommentsAdapter adapter = new RecyclerCommentsAdapter(CommentsActivity.this, commentByNewsItems);
                recyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(CommentsActivity.this, "Error", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_post_comments:
                Intent intent = new Intent(CommentsActivity.this, PostCommentActivity.class);
                intent.putExtra("newsid", newsid);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}