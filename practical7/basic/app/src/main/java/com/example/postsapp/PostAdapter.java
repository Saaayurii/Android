package com.example.postsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private final List<Post> posts;

    public PostAdapter(List<Post> posts) { this.posts = posts; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.textTitle.setText(post.getTitle());
        holder.textBody.setText(post.getBody());
    }

    @Override
    public int getItemCount() { return posts.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textBody;
        ViewHolder(View view) {
            super(view);
            textTitle = view.findViewById(R.id.textTitle);
            textBody = view.findViewById(R.id.textBody);
        }
    }
}
