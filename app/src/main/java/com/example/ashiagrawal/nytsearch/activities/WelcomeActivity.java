package com.example.ashiagrawal.nytsearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.ashiagrawal.nytsearch.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void onSearch(View view){
        Intent intent = new Intent(WelcomeActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    public void onBrowse(View view){

    }

    public void onSearchQuestion(View view){
        Toast.makeText(this, "Search for articles within the NYT database.", Toast.LENGTH_SHORT).show();
    }

    public void onBrowseQuestion(View view){
        Toast.makeText(this, "Browse the most popular articles in various categories.", Toast.LENGTH_SHORT).show();
    }
}
