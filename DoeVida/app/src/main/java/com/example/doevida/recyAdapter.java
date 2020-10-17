package com.example.doevida;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class recyAdapter extends RecyclerView.Adapter<recyAdapter.ViewHolder>{


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView data, centro, status;
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.icon_img);
            data = itemView.findViewById(R.id.data);
            centro = itemView.findViewById(R.id.centro);
            status = itemView.findViewById(R.id.status);
            cv = itemView.findViewById(R.id.card_view);
        }


        public void bindData(final Doacao item) {
            data.setText(item.getData());
            centro.setText(item.getCentro());
            status.setText(item.getStatus());
        }
    }


    private List<Doacao> mData;
    private LayoutInflater mInflater;
    private Context context;

    public recyAdapter(List<Doacao> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;

    }
    @Override
    public int getItemCount(){
        return mData.size();
    }

    @Override
    public recyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = mInflater.inflate(R.layout.list_element, null);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element, parent, false);
        return new recyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final recyAdapter.ViewHolder holder, final int position) {
        holder.cv.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        holder.bindData(mData.get(position));
    }


    public void setItems(List<Doacao> items) {
        mData = items;
    }


}
