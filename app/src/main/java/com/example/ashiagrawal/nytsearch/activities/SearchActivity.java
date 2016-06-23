package com.example.ashiagrawal.nytsearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.example.ashiagrawal.nytsearch.Article;
import com.example.ashiagrawal.nytsearch.ArticleArrayAdapter;
import com.example.ashiagrawal.nytsearch.EndlessRecyclerViewScrollListener;
import com.example.ashiagrawal.nytsearch.ItemClickSupport;
import com.example.ashiagrawal.nytsearch.ParametersDialogFragment;
import com.example.ashiagrawal.nytsearch.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;
    StaggeredGridLayoutManager grid;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.rvResults) RecyclerView rvResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setUpViews();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ParametersDialogFragment editNameDialogFragment = ParametersDialogFragment.newInstance();
        editNameDialogFragment.show(fm, "fragment_parameters");
    }

    private void setUpViews(){
        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(articles);
        rvResults.setAdapter(adapter);
        grid = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rvResults.setLayoutManager(grid);
        customLoadMoreDataFromApi(0, "", "browse");
        ItemClickSupport.addTo(rvResults).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
                        Article article = articles.get(position);
                        i.putExtra("article", Parcels.wrap(article));
                        startActivity(i);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                onArticleSearch(query);
                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_filter) {
            showEditDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onArticleSearch(final String query) {
        rvResults.clearOnScrollListeners();
        rvResults.addOnScrollListener(new EndlessRecyclerViewScrollListener(grid) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page, query, "search");
            }
        });
        articles.clear();
        customLoadMoreDataFromApi(0, query, "search");
    }

    public void customLoadMoreDataFromApi(final int page, String query, final String type) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "";
        if (type == "search") url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
        else if (type == "browse") url = "https://api.nytimes.com/svc/topstories/v2/home.json";
        RequestParams params = new RequestParams();
        if (type == "search" ) {
            params.put("api-key", "1532fa81593c4ee78fa835df68fe5f1f");
            params.put("page", page);
            params.put("q", query);
        } else if (type == "browse"){
            params.put("api-key", "1532fa81593c4ee78fa835df68fe5f1f");
        }
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray articleJsonResults = null;
                try {
                    if (type == "search") articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    else if (type == "browse") articleJsonResults = response.getJSONArray("results");
                    int curSize = adapter.getItemCount();
                    ArrayList<Article> newItems = Article.fromJSONArray(articleJsonResults, type);
                    articles.addAll(newItems);
                    adapter.notifyDataSetChanged();
                    //adapter.notifyItemRangeInserted(curSize, newItems.size() - 1);
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

}
