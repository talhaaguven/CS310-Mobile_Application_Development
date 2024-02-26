package edu.sabanciuniv.mehmettalha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

import edu.sabanciuniv.mehmettalha.models.CommentByNewsItem;
import edu.sabanciuniv.mehmettalha.models.CommentsByNews;

public class PostCommentActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private EditText etName, etComment;
    private Button bPostComment;

    private int newsid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comment);

        Bundle bundle = getIntent().getExtras();
        newsid = bundle.getInt("newsid");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle("Post Comment");

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

        etName = findViewById(R.id.etName);
        etComment = findViewById(R.id.etComment);
        bPostComment = findViewById(R.id.bPostComment);
        
        bPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String comment = etComment.getText().toString();
                
                if(name.trim().length() == 0) {
                    Toast.makeText(PostCommentActivity.this, "Please write Your Name.", Toast.LENGTH_SHORT).show();
                } else {
                    if(comment.trim().length() == 0) {
                        Toast.makeText(PostCommentActivity.this, "Please write Your comment.", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            dialog.show();

                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("name", name);
                            jsonObject.put("text", comment);
                            jsonObject.put("news_id", "" + newsid);

                            NewsAppRepository repo = new NewsAppRepository();
                            repo.postComment(((NewsApp) getApplication()).srv, dataHandler, jsonObject);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    onBackPressed();
                }
            }, 1500);

            JSONObject data = (JSONObject) msg.obj;

            try {
                int serviceMessageCode = data.getInt("serviceMessageCode");
                if(serviceMessageCode == 0) {
                    Toast.makeText(PostCommentActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PostCommentActivity.this, "Comment Posted", Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    });
}