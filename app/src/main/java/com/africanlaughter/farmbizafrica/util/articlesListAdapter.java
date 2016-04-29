package com.africanlaughter.farmbizafrica.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.africanlaughter.farmbizafrica.R;
import com.africanlaughter.farmbizafrica.app.AppController;
import com.africanlaughter.farmbizafrica.model.Article;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by Morris on 4/28/2016.
 */
//public class articlesListAdapter {
//}
public class ArticlesListAdapter extends ArrayAdapter<String> {
    private String[] title;
    private String[] details;
    private String[] intro;
    private Bitmap[] thumbnails;
    private String[] imageURLS;
    private Activity context;
    private List<Article> articles;
    private ImageLoader imageLoader= AppController.getInstance().getImageLoader();
    public ArticlesListAdapter(Activity context, List<Article> articles) {
        super(context, R.layout.articles_list_view);
        this.context = context;
        this.articles=articles;
    }
    @Override
    public int getCount() {
        return articles.size();
    }

//    @Override
//    public Object getItem(int location) {
//        return articles.get(location);
//    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View articlesListViewItem = inflater.inflate(R.layout.articles_list_view, null, true);

//        ImageView articleImage = (ImageView) articlesListViewItem.findViewById(R.id.article_image);
        NetworkImageView articleThumbNail = (NetworkImageView) articlesListViewItem.findViewById(R.id.article_image);
        TextView articleTitle = (TextView) articlesListViewItem.findViewById(R.id.article_title);
        TextView articleDetail = (TextView) articlesListViewItem.findViewById(R.id.article_detail);
        TextView articleIntro = (TextView) articlesListViewItem.findViewById(R.id.article_intro);

        Article atcl= articles.get(position);


        articleTitle.setText(atcl.getTitle());
        articleDetail.setText("Published on " + atcl.getDetails());
        articleIntro.setText(Html.fromHtml(atcl.getIntro()));
        articleThumbNail.setImageUrl(atcl.getImageURL(),imageLoader);

//        Log.d(atcl.getImageURL());


//        TextView textViewId = (TextView) articlesListViewItem.findViewById(R.id.textViewId);
//        TextView textViewName = (TextView) articlesListViewItem.findViewById(R.id.textViewName);
//        TextView textViewEmail = (TextView) articlesListViewItem.findViewById(R.id.textViewEmail);

//        textViewId.setText(title[position]);
//        textViewName.setText(details[position]);
//        textViewEmail.setText(intro[position]);

        return articlesListViewItem;
    }
}