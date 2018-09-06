package com.levqo.testnews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 04.09.2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private Context mContext;
    private ArrayList<Item> mItemList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public RecyclerViewAdapter(Context context, ArrayList<Item> itemList) {
        mContext = context;
        mItemList = itemList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Item currentItem = mItemList.get(position);

        String title = currentItem.getTitle();
        String coefficient = currentItem.getCoefficient();
        String time = currentItem.getTime();
        String place = currentItem.getPlace();

        holder.mTextViewTitle.setText(title);
        holder.mTextViewCoefficient.setText(coefficient);
        holder.mTextViewTime.setText(time);
        holder.mTextViewPlace.setText(place);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextViewTitle;
        public TextView mTextViewCoefficient;
        public TextView mTextViewTime;
        public TextView mTextViewPlace;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mTextViewTitle = itemView.findViewById(R.id.tv_title);
            mTextViewCoefficient = itemView.findViewById(R.id.tv_coefficient);
            mTextViewTime = itemView.findViewById(R.id.tv_time);
            mTextViewPlace = itemView.findViewById(R.id.tv_place);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
