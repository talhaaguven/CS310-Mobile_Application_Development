package edu.sabanciuniv.mehmettalha;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import android.os.Handler;

import edu.sabanciuniv.mehmettalha.models.CommentByNewsItem;
import edu.sabanciuniv.mehmettalha.models.CommentsByNews;
import edu.sabanciuniv.mehmettalha.models.NewsByCategory;
import edu.sabanciuniv.mehmettalha.models.NewsByCategoryItem;
import edu.sabanciuniv.mehmettalha.models.NewsCategory;
import edu.sabanciuniv.mehmettalha.models.NewsCategoryItem;

public class NewsAppRepository {

    public void getAllNewsCategories(ExecutorService srv, Handler uiHandler) {

        srv.execute(() -> {
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getallnewscategories");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {

                    buffer.append(line);

                }

                JSONObject jsonObject = new JSONObject(buffer.toString());
                int serviceMessageCode = jsonObject.getInt("serviceMessageCode");
                String serviceMessageText = jsonObject.getString("serviceMessageText");
                JSONArray items = jsonObject.getJSONArray("items");

                List<NewsCategoryItem> data_items = new ArrayList<>();

                for (int i = 0; i < items.length(); i++) {
                    JSONObject current = items.getJSONObject(i);

                    NewsCategoryItem newsCategoryItem = new NewsCategoryItem(current.getInt("id"),
                            current.getString("name"));
                    data_items.add(newsCategoryItem);
                }

                NewsCategory data = new NewsCategory(serviceMessageCode, serviceMessageText, data_items);

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void getAllNewsByCategories(ExecutorService srv, Handler uiHandler, int catid) {

        srv.execute(() -> {
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getbycategoryid/" + catid);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {

                    buffer.append(line);

                }

                JSONObject jsonObject = new JSONObject(buffer.toString());
                int serviceMessageCode = jsonObject.getInt("serviceMessageCode");
                String serviceMessageText = jsonObject.getString("serviceMessageText");
                JSONArray items = jsonObject.getJSONArray("items");

                List<NewsByCategoryItem> data_items = new ArrayList<>();

                for (int i = 0; i < items.length(); i++) {
                    JSONObject current = items.getJSONObject(i);

                    NewsByCategoryItem newsByCategoryItem = new NewsByCategoryItem(current.getInt("id"),
                            current.getString("title"), current.getString("text"), current.getString("date"),
                            current.getString("image"), current.getString("categoryName"));
                    data_items.add(newsByCategoryItem);
                }

                NewsByCategory data = new NewsByCategory(serviceMessageCode, serviceMessageText, data_items);

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void getCommentsByNewsId(ExecutorService srv, Handler uiHandler, int newsid) {

        srv.execute(() -> {
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getcommentsbynewsid/" + newsid);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {

                    buffer.append(line);

                }

                JSONObject jsonObject = new JSONObject(buffer.toString());
                int serviceMessageCode = jsonObject.getInt("serviceMessageCode");
                String serviceMessageText = jsonObject.getString("serviceMessageText");
                JSONArray items = jsonObject.getJSONArray("items");

                List<CommentByNewsItem> data_items = new ArrayList<>();

                for (int i = 0; i < items.length(); i++) {
                    JSONObject current = items.getJSONObject(i);

                    CommentByNewsItem commentByNewsItem = new CommentByNewsItem(current.getInt("id"),
                            current.getInt("news_id"), current.getString("text"), current.getString("name"));
                    data_items.add(commentByNewsItem);
                }

                CommentsByNews data = new CommentsByNews(serviceMessageCode, serviceMessageText, data_items);

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void postComment(ExecutorService srv, Handler uiHandler, JSONObject comment) {

        srv.execute(() -> {
            try {
                StringBuilder buffer = new StringBuilder();

                URL url = new URL("http://10.3.0.14:8080/newsapp/savecomment");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/json");
                conn.connect();

                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.writeBytes(comment.toString());

                if(conn.getResponseCode()==HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String line = "";

                    while ((line = reader.readLine()) != null) {

                        buffer.append(line);

                    }
                }

                JSONObject jsonObject = new JSONObject(buffer.toString());

                Message msg = new Message();
                msg.obj = jsonObject;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void downloadImage(ExecutorService srv, Handler uiHandler,String path){
        srv.execute(()->{
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());

                Message msg = new Message();
                msg.obj = bitmap;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
