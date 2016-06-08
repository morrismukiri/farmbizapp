package com.africanlaughter.farmbizafrica;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.africanlaughter.farmbizafrica.app.AppController;
import com.africanlaughter.farmbizafrica.model.Article;
import com.africanlaughter.farmbizafrica.util.ArticlesListAdapter;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContentListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String API_ROOT = "http://api.farmbizafrica.com/";
    //    private static final String API_ROOT="http://192.168.0.15/farmbiz_api/";
    private static final String WEB_ROOT = "http://new.farmbizafrica.com/";
    //    private static final String WEB_ROOT="http://192.168.0.15/farmbiz/";
    private static final String TAG = ContentListActivity.class.getSimpleName();
    private List<Article> articleList = new ArrayList<Article>();
    private ArticlesListAdapter adapter;
    private static String url = API_ROOT + "category/8/articles";
    TextView contentView;
    private ProgressDialog pDialog;
    private ListView articleListView;
    private SwipeRefreshLayout contentListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "URL: " + MainActivity.CurrentAPIURL);
        this.url = API_ROOT + MainActivity.CurrentAPIURL;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        contentView = (TextView) findViewById(R.id.article_preview);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        Log.d(TAG, "trying to log");

        contentListView = (SwipeRefreshLayout) findViewById(R.id.contentListView);
        articleListView = (ListView) findViewById(R.id.articles_list_view);
        adapter = new ArticlesListAdapter(this, articleList);
        articleListView.setAdapter(adapter);

        contentListView.setOnRefreshListener(this);
        ////---------------


        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        contentListView.post(
                new Runnable() {
                    @Override
                    public void run() {
                        contentListView.setRefreshing(true);
                        getArticles();
                    }
                }
        );
        ///--------------------


        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View view, int var3, long var4) {
                Snackbar.make(view, String.format("List item clicked Var 1:%d var 2: %d", var3, var4), Snackbar.LENGTH_LONG).setAction("Empty", null).show();
                Intent contentView = new Intent(getBaseContext(), ReadContent.class);
                TextView titleView = (TextView) view.findViewById(R.id.article_title);
                String title = titleView.getText().toString();
                TextView detailsView = (TextView) view.findViewById(R.id.article_detail);
                String details = detailsView.getText().toString();
                TextView introView = (TextView) view.findViewById(R.id.article_intro);
                String intro = introView.getText().toString();
                TextView imageURLView = (TextView) view.findViewById(R.id.article_image_url);
                String imageURL = imageURLView.getText().toString();
                TextView articleURLView = (TextView) view.findViewById(R.id.article_url);
                String articleURL = articleURLView.getText().toString();
                TextView fullContentView = (TextView) view.findViewById(R.id.article_full_content);
                String fullContent = fullContentView.getText().toString();

                contentView.putExtra("title", title);
                contentView.putExtra("details", details);
                contentView.putExtra("intro", intro);
                contentView.putExtra("imageURL", imageURL);
                contentView.putExtra("articleURL", articleURL);
                contentView.putExtra("fullContent", fullContent);

                startActivity(contentView);

            }
        });
//
    }

    void getArticles() {
        // Creating volley request obj
        // showing refresh animation before making http call
        contentListView.setRefreshing(true);

        JsonArrayRequest articleReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        Log.d(TAG, "Response:  " + response.toString());
                        hidePDialog();
//                        contentView.setText("Response: " + response.toString());
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Article article = new Article();
                                article.setId(obj.getString("id"));
                                article.setTitle(obj.getString("title"));
                                article.setArticleURL(WEB_ROOT + (!obj.getString("alias").isEmpty() ? obj.getString("alias") : "?id=" + obj.getString("id")));
                                article.setDetails(obj.getString("publish_up"));
                                article.setIntro(obj.getString("introtext"));
                                article.setImageURL(WEB_ROOT + obj.getString("intro_image"));
                                article.setArticleFullContent(obj.getString("fulltext").isEmpty() ? obj.getString("introtext") : obj.getString("fulltext"));


                                articleList.add(article);

                            } catch (JSONException e) {
                                Log.d(TAG, e.getStackTrace().toString());
                                e.printStackTrace();
                            }

                        }
                        Log.d(TAG, "Finished processing response");
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                        // stopping swipe refresh
                        contentListView.setRefreshing(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getStackTrace());
                hidePDialog();
                contentListView.setRefreshing(false);
                Log.d(TAG, error.getStackTrace().toString());
                error.printStackTrace();
            }
        });
        Log.d(TAG, "Done with assync");
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(articleReq);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void onRefresh() {
        getArticles();
    }


}
