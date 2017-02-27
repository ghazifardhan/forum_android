package com.app.ghazi.forumku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.ghazi.forumku.adapter.PostRecyclerView;
import com.app.ghazi.forumku.adapter.RecyclerItemClickListener;
import com.app.ghazi.forumku.api.ApiService;
import com.app.ghazi.forumku.api.RetrofitClient;
import com.app.ghazi.forumku.model.post.Post;
import com.app.ghazi.forumku.model.post.PostResult;
import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity{

    public static final String myPreferences = "myPref";
    public static final String token = "token";
    public String myToken = "";
    SharedPreferences sp;

    public static Integer forumId;
    public static Integer threadId;

    private List<PostResult> postResults;

    ApiService service;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private BootstrapButton mButtonComment;

    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        initUI();
        // Shared Preference
        sp = getSharedPreferences(myPreferences, MODE_PRIVATE);
        myToken = "Bearer " + sp.getString(token, "");

        Intent intent = getIntent();
        forumId = intent.getIntExtra("forum_id",0);
        threadId = intent.getIntExtra("thread_id",0);

        service = RetrofitClient.getClient().create(ApiService.class);

        getPostList();

        initEvent();

    }
    public void initUI(){
        mButtonComment = (BootstrapButton) findViewById(R.id.comment);
    }
    public void initEvent(){
        mButtonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CommentActivity.class);
                i.putExtra("thread_id", threadId);
                i.putExtra("forum_id", forumId);
                startActivity(i);
            }
        });
    }

    public void getPostList(){

        Call<Post> postCall = service.postList(myToken, forumId, threadId);
        postCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                List<PostResult> posts = response.body().getPosts();
                postResults = posts;

                showPostList();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });

    }

    public void showPostList(){
        ArrayList<String> mPosted = new ArrayList<String>();
        ArrayList<Integer> mPostedId = new ArrayList<Integer>();
        final ArrayList<String> mPostedBy = new ArrayList<String>();
        for(int i=0;i<postResults.size();i++){
            mPosted.add(postResults.get(i).getPost());
            mPostedId.add(postResults.get(i).getId());
            mPostedBy.add(postResults.get(i).getEmail());
        }
        recyclerView = (RecyclerView) findViewById(R.id.card_view_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        PostRecyclerView postRecyclerView = new PostRecyclerView(this, mPosted, mPostedBy, mPostedId);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(PostActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Snackbar mySnack = Snackbar.make(recyclerView, "Posted By: " + mPostedBy.get(position), Snackbar.LENGTH_LONG);
                        mySnack.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                        mySnack.show();
                    }
                })
        );
        recyclerView.setAdapter(postRecyclerView);

    }

    @Override
    public void onBackPressed(){
        Intent i =  new Intent(getApplicationContext(), ThreadActivity.class);
        i.putExtra("forum_id", forumId);
        startActivity(i);
    }
}
