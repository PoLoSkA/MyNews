package ru.polosatuk.mycard.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.polosatuk.mycard.R;
import ru.polosatuk.mycard.network.dto.NewsDTO;
import ru.polosatuk.mycard.screen.newsList.models.NewsDisplayableModel;
import ru.polosatuk.mycard.screen.utils.ImageUtils;


public class NewsDetailsActivity extends AppCompatActivity {

    public static final String NEWS_KEY_EXTRA = "newsItem:item";

    @NonNull
    public static void start(@NonNull Context context, @NonNull NewsDisplayableModel item) {
        context.startActivity(new Intent(context, NewsDetailsActivity.class).putExtra(NEWS_KEY_EXTRA, item));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        Toolbar myToolbar = findViewById(R.id.title_tool_bar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final NewsDisplayableModel news = (NewsDisplayableModel) getIntent().getSerializableExtra(NEWS_KEY_EXTRA);
        WebView webView = findViewById(R.id.webview);
        webView.loadUrl(news.getUrl());

//        TextView tvTitle = findViewById(R.id.news_details_title);
//        TextView tvFullNews = findViewById(R.id.news_details_full_text);
//        ImageView tvImageView = findViewById(R.id.news_details_image);
//        TextView tvDate = findViewById(R.id.news_details_date);
//
//        tvTitle.setText(news.getTitle());
//        tvFullNews.setText(news.getFullText());
//        tvDate.setText(news.getPublishDate());
        myToolbar.setTitle(news.getNewsSubCategory());
//
//        RequestManager imageLoader = ImageUtils.getRequestManager(this);
//
//        imageLoader.load(news.getBigImageUrl()).into(tvImageView);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
