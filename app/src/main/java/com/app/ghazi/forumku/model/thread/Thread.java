package com.app.ghazi.forumku.model.thread;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Riesto on 27/02/2017.
 */

public class Thread {

    @SerializedName("forum_threads")
    @Expose
    private List<ForumThreadResult> forumThreads = null;

    public List<ForumThreadResult> getForumThreads() {
        return forumThreads;
    }

    public void setForumThreads(List<ForumThreadResult> forumThreads) {
        this.forumThreads = forumThreads;
    }

}
