package com.africanlaughter.farmbizafrica;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.File;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static String CurrentAPIURL;
    private WebView MainBrowser;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        MainBrowser = (WebView) findViewById(R.id.browserView);
        MainBrowser.setWebViewClient(new MyWebViewClient());

        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);

        CurrentAPIURL = "articles/latest/5";
        Intent viewContentIntent = new Intent(this, ContentListActivity.class);

//        startActivity(viewContentIntent);


//        WebSettings webSettings = MainBrowser.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        MainBrowser.loadUrl("http://new.farmbizafrica.com");


//        File profilePicture = new File(this.getFilesDir(), "profilePic.png");
//        ImageView profilePicView = (ImageView) findViewById(R.id.profilePicImage);
//        if (profilePicture.exists()) {
//            profilePicView.setImageURI(Uri.parse(profilePicture.getAbsolutePath()));
//        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progress.setVisibility(View.GONE);
            MainActivity.this.progress.setProgress(100);
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progress.setVisibility(View.VISIBLE);
            MainActivity.this.progress.setProgress(0);
            super.onPageStarted(view, url, favicon);
        }
    }

    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_latest) {
            CurrentAPIURL = "articles/latest/5";
            Intent viewContentIntent = new Intent(this, ContentListActivity.class);

            startActivity(viewContentIntent);
//            MainBrowser.loadUrl("http://new.farmbizafrica.com/index.php?option=com_content&view=featured");
        } else if (id == R.id.nav_featured) {
            CurrentAPIURL = "articles/featured";
            Intent viewContentIntent = new Intent(this, ContentListActivity.class);

            startActivity(viewContentIntent);
//            MainBrowser.loadUrl("http://new.farmbizafrica.com/index.php?option=com_content&view=featured");
//        } else if (id == R.id.nav_editors_pick) {
//
////            MainBrowser.loadUrl("http://new.farmbizafrica.com/index.php?option=com_content&view=featured");
        } else if (id == R.id.nav_cat_high_yield) {
            CurrentAPIURL = "category/8/articles";
            Intent viewContentIntent = new Intent(this, ContentListActivity.class);

            startActivity(viewContentIntent);
//            MainBrowser.loadUrl("http://new.farmbizafrica.com/high-yield");
        } else if (id == R.id.nav_cat_pest_contol) {
            CurrentAPIURL = "category/9/articles";
            Intent viewContentIntent = new Intent(this, ContentListActivity.class);

            startActivity(viewContentIntent);
//            MainBrowser.loadUrl("http://new.farmbizafrica.com/pest-control");
        } else if (id == R.id.nav_cat_machinery) {
            CurrentAPIURL = "category/10/articles";
            Intent viewContentIntent = new Intent(this, ContentListActivity.class);

            startActivity(viewContentIntent);
//            MainBrowser.loadUrl("http://new.farmbizafrica.com/machinery");

        } else if (id == R.id.nav_cat_profit_boosters) {
            CurrentAPIURL = "category/11/articles";
            Intent viewContentIntent = new Intent(this, ContentListActivity.class);

            startActivity(viewContentIntent);
//            MainBrowser.loadUrl("http://new.farmbizafrica.com/profit-boosters");

        } else if (id == R.id.nav_cat_markets) {
//            MainBrowser.loadUrl("http://new.farmbizafrica.com/markets");
            CurrentAPIURL = "category/12/articles";
            Intent viewContentIntent = new Intent(this, ContentListActivity.class);

            startActivity(viewContentIntent);
        } else if (id == R.id.nav_cat_trends) {
//            MainBrowser.loadUrl("http://new.farmbizafrica.com/markets");
            CurrentAPIURL = "category/14/articles";
            Intent viewContentIntent = new Intent(this, ContentListActivity.class);

            startActivity(viewContentIntent);
        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "I am using the most informative agricultural blog... http://www.farmbizafrica.com");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));

        }
//        else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
