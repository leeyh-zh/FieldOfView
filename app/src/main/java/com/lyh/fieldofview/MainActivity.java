package com.lyh.fieldofview;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.lyh.fieldofview.api.DailyApi;
import com.lyh.fieldofview.base.ToolbarActivity;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

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
    }

    private void setupRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new MultiTypeAdapter(items);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            drawerLayout.closeDrawers();
            findIntersting(menuItem);
            return true;
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
            default:
                return;
        }
    }
}
