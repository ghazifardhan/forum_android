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
import com.app.ghazi.forumku.model.forum.Forum;
import com.app.ghazi.forumku.model.forum.ForumResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForumActivity extends AppCompatActivity {

    public static final String myPreferences = "myPref";
    public static final String token = "token";
    public String myToken = "";
    SharedPreferences sp;

    private List<ForumResult> forumResultList;

    ApiService service;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        // Shared Preference
        sp = getSharedPreferences(myPreferences, MODE_PRIVATE);
        myToken = "Bearer " + sp.getString(token, "");

        service = RetrofitClient.getClient().create(ApiService.class);

        getForumList();
    }

    public void getForumList(){

        Call<Forum> forumCall = service.forumList(myToken);
        forumCall.enqueue(new Callback<Forum>() {
            @Override
            public void onResponse(Call<Forum> call, Response<Forum> response) {
                List<ForumResult> forums = response.body().getForums();
                forumResultList = forums;

                showForumList();
            }

            @Override
            public void onFailure(Call<Forum> call, Throwable t) {

            }
        });
    }

    public void showForumList(){

        final ArrayList<String> mForumName = new ArrayList<String>();
        final ArrayList<Integer> mForumId = new ArrayList<Integer>();
        for(int i=0;i<forumResultList.size();i++){
            if(forumResultList.get(i).getParent() == 0){
                mForumName.add(forumResultList.get(i).getForumName());
                mForumId.add(forumResultList.get(i).getId());
            }
        }
        recyclerView = (RecyclerView) findViewById(R.id.card_view_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        ForumRecyclerView forumRecyclerView = new ForumRecyclerView(this, mForumName, mForumId);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(ForumActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent i = new Intent(getApplicationContext(), ThreadActivity.class);
                        i.putExtra("forum_id", mForumId.get(position));
                        startActivity(i);
                    }
                })
        );
        recyclerView.setAdapter(forumRecyclerView);

    }
}
