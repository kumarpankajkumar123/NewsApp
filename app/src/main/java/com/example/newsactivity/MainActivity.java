package com.example.newsactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.newsactivity.AdaptorClass.AdaptorNews;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    AdaptorNews adaptorNews;
    List<Article> article =new ArrayList<>();
    MaterialButton gen,spt,business,technology,entertainment,science,health;
   SearchView Search;
    LinearProgressIndicator linearProgressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        linearProgressIndicator = (LinearProgressIndicator)findViewById(R.id.linearProgressIndicator);
        gen = findViewById(R.id.gen);
        spt = findViewById(R.id.spt);
        science = findViewById(R.id.science);
        technology  = findViewById(R.id.technology);
        health = findViewById(R.id.health);
        entertainment = findViewById(R.id.entertainment);
        business = findViewById(R.id.business);
        Search = findViewById(R.id.search);

        gen.setOnClickListener(this);
        spt.setOnClickListener(this);
        science.setOnClickListener(this);
        technology.setOnClickListener(this);
        health.setOnClickListener(this);
        business.setOnClickListener(this);
        entertainment.setOnClickListener(this);

        Search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getNews("General",s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        setUpRecyclerView();
        getNews("General",null);
    }

    void changeProgressBar(boolean show){
        if(show)
            linearProgressIndicator.setVisibility(View.VISIBLE);
        else
            linearProgressIndicator.setVisibility(View.INVISIBLE);
    }
     void setUpRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptorNews = new AdaptorNews(article);
         recyclerView.setAdapter(adaptorNews);
    }
    void getNews(String general,String query){
         changeProgressBar(true);
        NewsApiClient newsApiClient = new NewsApiClient("74329f6e44f847818cbfa7b062bcf98a");
        
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder().category(general).language("en").q(query).build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
//                        Log.i("Response successful",response.toString());
                        runOnUiThread(() -> {
                            changeProgressBar(false);
                            article = response.getArticles();

                            adaptorNews.upDateData(article);
                            adaptorNews.notifyDataSetChanged();

                        });
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
//                        Log.i("Failure",throwable.getMessage());
                    }
                }
        );
    }

    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        String category = btn.getText().toString();
        getNews(category,null);
    }
}