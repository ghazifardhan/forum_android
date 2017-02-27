package com.app.ghazi.forumku.model.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Riesto on 27/02/2017.
 */

public class Post {

    @SerializedName("posts")
    @Expose
    private List<PostResult> posts = null;

    public List<PostResult> getPosts() {
        return posts;
    }

    public void setPosts(List<PostResult> posts) {
        this.posts = posts;
    }

}
