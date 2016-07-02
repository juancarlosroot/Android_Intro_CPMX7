package com.myfirstapp.juancarlosroot.myfirstapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by juancarlosroot on 6/26/16.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    List<Item> mList;
    private static final String TAG = "CARDADAPTER";

    public CardAdapter(List<Item> mList){
        this.mList = mList;
        Log.i(TAG, mList.size() + "ssss");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_card_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Item mItem = mList.get(position);

        holder.mTextViewTitle.setText(mItem.getmTitle());
        holder.mTextViewDescription.setText(mItem.getmDescription());

        holder.mTextViewTitle.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse("file://" + holder.mTextViewDescription.getText().toString()), "image/*");
                        holder.itemView.getContext().startActivity(intent);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextViewTitle;
        public TextView mTextViewDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewTitle = (TextView) itemView.findViewById(R.id.text_view_title);
            mTextViewDescription = (TextView) itemView.findViewById(R.id.text_view_description);

        }
    }
}
