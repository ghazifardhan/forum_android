package com.app.ghazi.forumku.api;

import com.app.ghazi.forumku.model.TokenResponse;
import com.app.ghazi.forumku.model.forum.Forum;
import com.app.ghazi.forumku.model.login.Login;
import com.app.ghazi.forumku.model.post.Post;
import com.app.ghazi.forumku.model.post.PostComment;
import com.app.ghazi.forumku.model.thread.Thread;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Ghazi on 27/02/2017.
 */

public interface ApiService {

    @POST("/login")
    Call<TokenResponse> doLogin(@Body Login login);

    @POST("/register")
    Call<ResponseBody> doRegister(@Body Login login);

    @GET("/forum")
    Call<Forum> forumList(@Header("Authorization") String token);

    @GET("/forum/{id}/thread")
    Call<Thread> threadList(@Header("Authorization") String token, @Path("id") Integer id);

    @GET("/forum/{idForum}/thread/{idThread}")
    Call<Post> postList(@Header("Authorization") String token, @Path("idForum") Integer idForum, @Path("idThread") Integer idThread);

    @Multipart
    @POST("/post")
    Call<PostComment> doComment(@Header("Authorization") String token, @Part("thread_id") Integer thread_id, @Part("post") String post);
}
