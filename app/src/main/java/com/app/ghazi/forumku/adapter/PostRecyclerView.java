package com.app.ghazi.forumku.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.ghazi.forumku.R;

import java.util.ArrayList;

/**
 * Created by Riesto on 27/02/2017.
 */

public class PostRecyclerView extends RecyclerView.Adapter<PostRecyclerView.ViewHolder>{

    private Context context;
    private ArrayList<String> mPosted = new ArrayList<String>();
    private ArrayList<String> mPostedBy = new ArrayList<String>();
    private ArrayList<Integer> mPostedId = new ArrayList<Integer>();

    public PostRecyclerView(Context context, ArrayList<String> mPosted, ArrayList<String> mPostedBy, ArrayList<Integer> mPostedId){
        this.context = context;
        this.mPosted = mPosted;
        this.mPostedBy = mPostedBy;
        this.mPostedId = mPostedId;
    }

    @Override
    public PostRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list, parent, false);
        return new PostRecyclerView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostRecyclerView.ViewHolder holder, int position) {
        holder.postedComment.setText(mPosted.get(position));
        holder.postedBy.setText(mPostedBy.get(position));
    }

    @Override
    public int getItemCount() {
        return mPosted.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView postedComment;
        public TextView postedBy;

        public ViewHolder(View itemView) {
            super(itemView);
            postedComment = (TextView) itemView.findViewById(R.id.comment);
            postedBy = (TextView) itemView.findViewById(R.id.posted_by);
        }
    }

}
