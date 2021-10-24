package com.example.homework;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StaggeredGridAdapter extends RecyclerView.Adapter<StaggeredGridAdapter.LinearViewHolder> {
    private Context mContext;
    private OnItemClickListener mListener;

    public StaggeredGridAdapter(Context context,OnItemClickListener listener){
        this.mContext = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public StaggeredGridAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_straggered_grid_recyclerview_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StaggeredGridAdapter.LinearViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if(position % 2 != 0){
            holder.imageView.setImageResource(R.drawable.first);
        }else {
            holder.imageView.setImageResource(R.drawable.second);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    public class LinearViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public LinearViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv);

        }
    }

    public interface OnItemClickListener{
        void onClick(int pos);
    }
}
