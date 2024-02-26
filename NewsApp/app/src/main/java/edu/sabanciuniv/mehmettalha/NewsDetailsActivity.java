package edu.sabanciuniv.mehmettalha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import edu.sabanciuniv.mehmettalha.models.NewsByCategoryItem;
import edu.sabanciuniv.mehmettalha.models.NewsCategory;

public class NewsDetailsActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private ImageView imageView;
    private TextView tvTitle, tvDate, tvText;

    private NewsByCategoryItem newsByCategoryItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        Bundle bundle = getIntent().getExtras();
        newsByCategoryItem = (NewsByCategoryItem) bundle.getSerializable("news");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle(newsByCategoryItem.getCategoryName());
        myToolbar.inflateMenu(R.menu.menu_news_details);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);

        imageView = findViewById(R.id.imageView);
        tvTitle = findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        tvText = findViewById(R.id.tvText);

        tvTitle.setText(newsByCategoryItem.getTitle());

        String date = newsByCategoryItem.getDate();
        String[] date2 = date.split("T")[0].split("-");
        tvDate.setText(date2[2] + "/" + date2[1] + "/" + date2[0]);

        tvText.setText(newsByCategoryItem.getText());

        dialog.show();
        NewsAppRepository repo = new NewsAppRepository();
        repo.downloadImage(((NewsApp) getApplication()).srv, imgHandler, newsByCategoryItem.getImage());
    }

    Handler imgHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Bitmap img = (Bitmap) msg.obj;
            imageView.setImageBitmap(img);

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
        getMenuInflater().inflate(R.menu.menu_news_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_show_comments:
                Intent intent = new Intent(NewsDetailsActivity.this, CommentsActivity.class);
                intent.putExtra("newsid", newsByCategoryItem.getId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}