package ru.polosatuk.mycard.newsList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.util.Util;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import ru.polosatuk.mycard.NewsDetailsActivity;
import ru.polosatuk.mycard.R;
import ru.polosatuk.mycard.about.AboutActivity;
import ru.polosatuk.mycard.newsList.models.NewsDisplayableModel;
import ru.polosatuk.mycard.newsList.models.NewsItem;
import ru.polosatuk.mycard.utils.Utils;

public class NewsViewActivity extends MvpAppCompatActivity implements NewsView {

    private static final int SCREEN_WIDTH_DP = 600;
    private static final int LARGE_SCREEN_WIDTH_DP = 1000;
    @NonNull
    private ProgressBar progressBar;
    @NonNull
    private NewsViewAdapter adapter;
    @NonNull
    private RecyclerView recyclerView;
    @NonNull
    private Button errorBtn;
    @NonNull
    private View errorView;

    @InjectPresenter
    NewsViewPresenter newsViewPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_board);

        progressBar = findViewById(R.id.progressBar);

        errorBtn = findViewById(R.id.error_content_btn_retry);
        errorBtn.setOnClickListener(onClick ->{
            newsViewPresenter.onRetryBtnClick();
        });
        errorView = findViewById(R.id.error_content);

        Toolbar myToolbar = findViewById(R.id.title_tool_bar);
        setSupportActionBar(myToolbar);

         recyclerView = findViewById(R.id.recycler_view_news);
        initRecyclerView(recyclerView, this);


    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.news_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_about_tool_bar: {
                Intent intent = AboutActivity.getActivityAboutIntent(this);
                startActivity(intent);
            }
            break;
            default: {
                Log.d("Tag", "Клик по кнопкене сработал");
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView(@NonNull RecyclerView recyclerView, @NonNull Context context) {

        setLayoutManager(recyclerView, context);
        adapter = new NewsViewAdapter(this, newsItem -> showNewsDetail(this, newsItem)
                );
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new NewsItemDecorator(getResources().getDimensionPixelSize(R.dimen.padding_4dp)));
    }

    private void setLayoutManager(@NonNull RecyclerView recyclerView, @NonNull Context context) {

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(spanCount(displayMetrics), StaggeredGridLayoutManager.VERTICAL));
    }

    private int spanCount(DisplayMetrics displayMetrics) {

        float screenWidthInDp = displayMetrics.widthPixels / displayMetrics.density;
        int spanCount;
        if (screenWidthInDp < SCREEN_WIDTH_DP) {
            spanCount = 1;
        } else if (screenWidthInDp > SCREEN_WIDTH_DP && screenWidthInDp < LARGE_SCREEN_WIDTH_DP) {
            spanCount = 2;
        } else {
            spanCount = 3;
        }
        return spanCount;
    }

    @Override
    public void showItems(List<NewsDisplayableModel> list) {
        if (adapter != null) {
            adapter.replaceItems(list);
        }
        progressBar.setVisibility(ProgressBar.GONE);
    }

    @Override
    public void showError(Throwable th) {
        Utils.setVisible(errorView, true);
        Log.d(NewsViewActivity.class.getSimpleName(), th.getMessage(), th);
    }

    @Override
    public void showProgressBar(boolean visability) {
        Utils.setVisible(progressBar, visability);
        Utils.setVisible(recyclerView,!visability);
        Utils.setVisible(errorView, false);
    }

    @Override
    public void showNewsDetail(Context context, NewsDisplayableModel newsItem) {
        NewsDetailsActivity.start(context, newsItem);
    }
}
