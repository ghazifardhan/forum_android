package com.app.ghazi.forumku.model.forum;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Riesto on 27/02/2017.
 */

public class Forum {

    @SerializedName("forums")
    @Expose
    private List<ForumResult> forums = null;

    public List<ForumResult> getForums() {
        return forums;
    }

    public void setForums(List<ForumResult> forums) {
        this.forums = forums;
    }
}
