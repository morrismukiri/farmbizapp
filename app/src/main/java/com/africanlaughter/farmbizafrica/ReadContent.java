package com.africanlaughter.farmbizafrica;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.africanlaughter.farmbizafrica.model.Article;

public class ReadContent extends AppCompatActivity {
    WebView contentWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_content);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent thisIntent = getIntent();
        final String title = thisIntent.getStringExtra("title");
        final String articleURL= thisIntent.getStringExtra("articleURL");
        String content = thisIntent.getStringExtra("fullContent");
        contentWebView = (WebView) findViewById(R.id.contentWebView);
        setTitle(title);
//        contentWebView.setWebChromeClient(new MyWebViewClient());

        contentWebView.loadData(content, "text/html", "UTF-8");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, title+ "..." + articleURL);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
