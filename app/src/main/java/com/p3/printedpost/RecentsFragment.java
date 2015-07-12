package com.p3.printedpost;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.p3.printedpost.parseObjects.Article;

import java.util.Vector;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecentsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public RecentsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recents, container, false);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.rv_recents);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecentsAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onPause() {
        super.onPause();


    }

    public void pause(View view) {

    }

    public void resume(View view) {

    }


}

class RecentsAdapter extends RecyclerView.Adapter<RecentsAdapter.ViewHolder> {
    Vector<Article> articles = new Vector<Article>();
    Activity ctx;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public Article article;
        public TextView tv_title;
        public RelativeTimeTextView tv_date;
        public TextView tv_excerpt;
        Activity ctx;

        public ViewHolder(View v) {
            super(v);
            tv_title = (TextView) v.findViewById(R.id.tv_article_title);
            tv_date = (RelativeTimeTextView) v.findViewById(R.id.tv_article_date);
            tv_excerpt = (TextView) v.findViewById(R.id.tv_article_exerpt);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ctx, ArticleActivity.class);
                    intent.putExtra("articleId", article.getObjectId());
                    ctx.startActivity(intent);
                }
            });
        }

        public void update(Article a, Activity ctx) {
            this.article = a;
            this.ctx = ctx;
            tv_title.setText(article.getTitle());
            tv_excerpt.setText(article.getExcerpt());
            tv_date.setReferenceTime(article.getCreatedAt().getTime());

        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecentsAdapter(Activity activity) {
        articles = PrintedPost.fachada.getArticles();
        this.ctx = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recent_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.update(articles.elementAt(position), ctx);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return articles.size();
    }
}