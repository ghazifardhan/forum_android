package com.app.ghazi.forumku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.app.ghazi.forumku.api.ApiService;
import com.app.ghazi.forumku.api.RetrofitClient;
import com.app.ghazi.forumku.model.post.PostComment;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {

    private BootstrapEditText mEditTextComment;
    private BootstrapButton mButtonComment;

    public static final String myPreferences = "myPref";
    public static final String token = "token";
    public String myToken = "";
    SharedPreferences sp;

    public static Integer threadId;
    public static Integer forumId;

    ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initUI();;
        // Shared Preference
        sp = getSharedPreferences(myPreferences, MODE_PRIVATE);
        myToken = "Bearer " + sp.getString(token, "");

        service = RetrofitClient.getClient().create(ApiService.class);

        Intent intent = getIntent();
        threadId = intent.getIntExtra("thread_id", 0);
        forumId = intent.getIntExtra("forum_id", 0);

        initEvent();
    }

    public void initUI(){
        mEditTextComment = (BootstrapEditText) findViewById(R.id.editTextComment);
        mButtonComment = (BootstrapButton) findViewById(R.id.post_comment);
    }

    public void initEvent(){
        mButtonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doPostComment();
            }
        });
    }

    public void doPostComment(){

        String myPost = mEditTextComment.getText().toString();

        Call<PostComment> postCommentCall = service.doComment(myToken, threadId, myPost);
        postCommentCall.enqueue(new Callback<PostComment>() {
            @Override
            public void onResponse(Call<PostComment> call, Response<PostComment> response) {
                Integer responseCode = response.code();
                Boolean success = response.body().getSuccess();
                if(success){
                    Intent i = new Intent(getApplicationContext(), PostActivity.class);
                    i.putExtra("forum_id", forumId);
                    i.putExtra("thread_id", threadId);
                    startActivity(i);
                } else {
                    Snackbar mySnack = Snackbar.make(mButtonComment, "Failed to post comment", Snackbar.LENGTH_LONG);
                    mySnack.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                    mySnack.show();
                }
            }

            @Override
            public void onFailure(Call<PostComment> call, Throwable t) {

            }
        });


    }
}
