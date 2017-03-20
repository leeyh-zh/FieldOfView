package com.lyh.fieldofview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jakewharton.rxbinding.support.v4.widget.RxSwipeRefreshLayout;
import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;
import com.lyh.fieldofview.activity.FindInterestingActivity;
import com.lyh.fieldofview.activity.VideoAuthorActivity;
import com.lyh.fieldofview.api.DailyApi;
import com.lyh.fieldofview.base.ToolbarActivity;
import com.lyh.fieldofview.model.Category;
import com.lyh.fieldofview.model.Daily;
import com.lyh.fieldofview.rx.ErrorAction;
import com.lyh.fieldofview.rx.RxScroller;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends ToolbarActivity {

    public static final String PROVIDER_ITEM = "item";
    public static final String CATEGORY_ID = "categoryId";
    public static final String TITLE = "title";

    private MultiTypeAdapter adapter;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private DrawerLayout drawerLayout;

    private DailyApi dailyApi;
    private Items items = new Items();
    private String dateTime = "";

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        ab.setHomeAsUpIndicator(R.mipmap.ic_menu);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(navigationView);

        dailyApi = InteressantFactory.getRetrofit().createApi(DailyApi.class);
        setupRecyclerView();

        RxSwipeRefreshLayout.refreshes(refreshLayout)
                .compose(bindToLifecycle())
                .subscribe(aVoid -> loadData(true));
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        loadData(true);
    }

    private void loadData(boolean clear) {
        Observable<Daily> result;
        if (clear) result = dailyApi.getDaily();
        else result = dailyApi.getDaily(Long.decode(dateTime));

        result.compose(bindToLifecycle())
                .filter(daily -> daily != null)
                .doOnNext(daily -> {
                    if (clear) items.clear();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> refreshLayout.setRefreshing(false))
                .subscribe(this::addData, ErrorAction.errorAction(this));
    }

    private void addData(Daily daily) {
        for (Daily.IssueList issueList : daily.issueList) {
            String date = issueList.itemList.get(0).data.text;
            items.add(new Category(date == null ? "Today" : date));
            items.addAll(issueList.itemList);
        }
        String nextPageUrl = daily.nextPageUrl;
        Log.d("1111", "nextPageUrl = " + nextPageUrl);
        dateTime = nextPageUrl.substring(nextPageUrl.indexOf("=") + 1,
                nextPageUrl.indexOf("&"));
        Log.d("1111", "dateTime = " + dateTime);
        adapter.notifyDataSetChanged();
    }

    private void setupRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new MultiTypeAdapter(items);

        Register.registerItem(adapter, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnTouchListener((v, event) -> refreshLayout.isRefreshing());

        RxRecyclerView.scrollStateChanges(recyclerView)
                .filter(integer -> !refreshLayout.isRefreshing())
                .compose(bindToLifecycle())
                .compose(RxScroller.scrollTransformer(layoutManager, adapter, items))
                .subscribe(newState -> {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        loadData(false);
                    }
                });
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                findIntersting(item);
                return true;
            }
        });
    }

    private void findIntersting(MenuItem item) {
        int id;
        String title;
        switch (item.getItemId()) {
            case R.id.nav_cute_pet:
                id = 26;
                title = getResources().getString(R.string.cute_pet);
                break;
            case R.id.nav_funny:
                id = 28;
                title = getResources().getString(R.string.funny);
                break;
            case R.id.nav_game:
                id = 30;
                title = getResources().getString(R.string.game);
                break;
            case R.id.nav_science:
                id = 32;
                title = getResources().getString(R.string.science);
                break;
            case R.id.nav_highlights:
                id = 34;
                title = getResources().getString(R.string.highlights);
                break;
            case R.id.nav_life:
                id = 36;
                title = getResources().getString(R.string.life);
                break;
            case R.id.nav_variety:
                id = 38;
                title = getResources().getString(R.string.variety);
                break;
            case R.id.nav_eating:
                id = 4;
                title = getResources().getString(R.string.eating);
                break;
            case R.id.nav_foreshow:
                id = 8;
                title = getResources().getString(R.string.foreshow);
                break;
            case R.id.nav_ad:
                id = 14;
                title = getResources().getString(R.string.advertisement);
                break;
            case R.id.nav_record:
                id = 22;
                title = getResources().getString(R.string.record);
                break;
            case R.id.nav_fashion:
                id = 24;
                title = getResources().getString(R.string.fashion);
                break;
            case R.id.nav_creative:
                id = 2;
                title = getResources().getString(R.string.creative);
                break;
            case R.id.nav_sports:
                id = 18;
                title = getResources().getString(R.string.sports);
                break;
            case R.id.nav_journey:
                id = 6;
                title = getResources().getString(R.string.journey);
                break;
            case R.id.nav_story:
                id = 12;
                title = getResources().getString(R.string.story);
                break;
            case R.id.nav_cartoon:
                id = 10;
                title = getResources().getString(R.string.cartoon);
                break;
            case R.id.nav_music:
                id = 20;
                title = getResources().getString(R.string.music);
                break;
            case R.id.nav_author:
                Intent intent = new Intent(this, VideoAuthorActivity.class);
                startActivity(intent);
            default:
                return;
        }
        Intent intent = new Intent(this, FindInterestingActivity.class);
        intent.putExtra(CATEGORY_ID, id);
        intent.putExtra(TITLE, title);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(Gravity.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
