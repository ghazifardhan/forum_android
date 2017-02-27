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

public class ForumRecyclerView  extends RecyclerView.Adapter<ForumRecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<String> mForumName = new ArrayList<String>();
    private ArrayList<Integer> mForumId = new ArrayList<Integer>();

    public ForumRecyclerView(Context context, ArrayList<String> mForumName, ArrayList<Integer> mForumId){
        this.context = context;
        this.mForumName = mForumName;
        this.mForumId = mForumId;
    }

    @Override
    public ForumRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_list, parent, false);
        return new ForumRecyclerView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForumRecyclerView.ViewHolder holder, int position) {
        holder.forumName.setText(mForumName.get(position));
    }

    @Override
    public int getItemCount() {
        return mForumName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView forumName;

        public ViewHolder(View itemView) {
            super(itemView);
            forumName = (TextView) itemView.findViewById(R.id.forum_name);
        }
    }
}
