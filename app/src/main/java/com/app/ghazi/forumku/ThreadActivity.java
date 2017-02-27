package com.app.ghazi.forumku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.ghazi.forumku.adapter.ForumRecyclerView;
import com.app.ghazi.forumku.adapter.RecyclerItemClickListener;
import com.app.ghazi.forumku.api.ApiService;
import com.app.ghazi.forumku.api.RetrofitClient;
import com.app.ghazi.forumku.model.thread.ForumThreadResult;
import com.app.ghazi.forumku.model.thread.Thread;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThreadActivity extends AppCompatActivity {

    public static final String myPreferences = "myPref";
    public static final String token = "token";
    public String myToken = "";
    SharedPreferences sp;

    public static Integer forumId;

    private List<ForumThreadResult> forumThreadResults;

    ApiService service;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        // Shared Preference
        sp = getSharedPreferences(myPreferences, MODE_PRIVATE);
        myToken = "Bearer " + sp.getString(token, "");

        service = RetrofitClient.getClient().create(ApiService.class);

        Intent intent = getIntent();
        forumId = intent.getIntExtra("forum_id", 0);

        getForumList();
    }

    public void getForumList(){

        Call<Thread> threadCall = service.threadList(myToken, forumId);
        threadCall.enqueue(new Callback<Thread>() {
            @Override
            public void onResponse(Call<Thread> call, Response<Thread> response) {
                List<ForumThreadResult> forums = response.body().getForumThreads();
                forumThreadResults = forums;

                showThreadList();
            }

            @Override
            public void onFailure(Call<Thread> call, Throwable t) {

            }
        });
    }

    public void showThreadList(){

        final ArrayList<String> mForumName = new ArrayList<String>();
        final ArrayList<Integer> mForumId = new ArrayList<Integer>();
        for(int i=0;i<forumThreadResults.size();i++){
                mForumName.add(forumThreadResults.get(i).getThreadName());
                mForumId.add(forumThreadResults.get(i).getId());
        }
        recyclerView = (RecyclerView) findViewById(R.id.card_view_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        ForumRecyclerView forumRecyclerView = new ForumRecyclerView(this, mForumName, mForumId);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(ThreadActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent i = new Intent(getApplicationContext(), PostActivity.class);
                        i.putExtra("forum_id", forumId);
                        i.putExtra("thread_id", mForumId.get(position));
                        startActivity(i);
                    }
                })
        );
        recyclerView.setAdapter(forumRecyclerView);

    }
}
