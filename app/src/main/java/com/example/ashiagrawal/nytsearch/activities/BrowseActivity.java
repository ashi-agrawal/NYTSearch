package com.example.ashiagrawal.nytsearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;

import com.example.ashiagrawal.nytsearch.Article;
import com.example.ashiagrawal.nytsearch.ArticleArrayAdapter;
import com.example.ashiagrawal.nytsearch.ItemClickSupport;
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

public class BrowseActivity extends AppCompatActivity {

    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;
    LinearLayoutManager list;
    @BindView(R.id.rvBrowseResults) RecyclerView rvBrowseResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        ButterKnife.bind(this);
        setUpViews();
    }

    private void setUpViews(){
        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(articles);
        rvBrowseResults.setAdapter(adapter);
        list = new LinearLayoutManager(this);
        rvBrowseResults.setLayoutManager(list);
        customLoadMoreDataFromApi(0);
        ItemClickSupport.addTo(rvBrowseResults).setOnItemClickListener(
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
        getMenuInflater().inflate(R.menu.menu_browse, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void customLoadMoreDataFromApi(final int page) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/topstories/v2/home.json";
        RequestParams params = new RequestParams();
        params.put("api-key", "1532fa81593c4ee78fa835df68fe5f1f");
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray articleJsonResults = null;
                try {
                    articleJsonResults = response.getJSONArray("results");
                    int curSize = adapter.getItemCount();
                    ArrayList<Article> newItems = Article.fromJSONArray(articleJsonResults, "browse");
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
