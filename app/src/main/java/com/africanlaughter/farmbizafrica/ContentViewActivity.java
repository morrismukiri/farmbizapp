package com.africanlaughter.farmbizafrica;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.africanlaughter.farmbizafrica.app.AppController;
import com.africanlaughter.farmbizafrica.model.Article;
import com.africanlaughter.farmbizafrica.util.ArticlesListAdapter;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContentViewActivity extends AppCompatActivity {
    private static final String TAG = ContentViewActivity.class.getSimpleName();
    private List<Article> articleList = new ArrayList<Article>();
    private ArticlesListAdapter adapter;
    private static final String url = "http://192.168.1.7/farmbiz_api/category/8/articles";
    TextView contentView;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        // changing action bar color
//        getActionBar().setBackgroundDrawable(
//                new ColorDrawable(Color.parseColor("#1b1b1b")));

        ListView articleListView = (ListView) findViewById(R.id.articles_list_view);
        adapter = new ArticlesListAdapter(this, articleList);
        articleListView.setAdapter(adapter);

// Creating volley request obj
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
                                article.setTitle(obj.getString("title"));
                                article.setDetails(obj.getString("publish_up"));
                                article.setIntro(obj.getString("introtext"));
                                article.setImageURL(obj.getString("intro_image"));


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
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getStackTrace());
                hidePDialog();
                Log.d(TAG, error.getStackTrace().toString());
                error.printStackTrace();
            }
        });
        Log.d(TAG, "Done with assync");
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(articleReq);
        //Async Magic

//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(articleReq);
//        String apiUrl = "http://localhost/farmbiz_api/category/1/Article";


        //End assync magic

        //end load image

//        TextView contentView = (TextView) findViewById(R.id.article_preview);
//        contentView.setText(Html.fromHtml("<h1>Title..</h1><p>Hello<br/><a href='foo'>try</a></p>"));
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


}
