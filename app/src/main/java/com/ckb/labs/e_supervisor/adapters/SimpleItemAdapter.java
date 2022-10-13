package com.ckb.labs.e_supervisor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.ckb.labs.e_supervisor.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class SimpleItemAdapter extends RecyclerView.Adapter<SimpleItemAdapter.SimpleItemViewHolder> {

    private List<String> stringList = new ArrayList<>();

    public void setItems(List<String> stringList) {
        this.stringList = stringList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SimpleItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimpleItemViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_simple_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleItemViewHolder holder, int position) {
        holder.bindView(stringList.get(position));
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    static class SimpleItemViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView simpleTextTv;
        public MaterialCardView cardView;

        public SimpleItemViewHolder(@NonNull View view) {
            super(view);
            simpleTextTv = view.findViewById(R.id.tv_text);
            cardView = view.findViewById(R.id.card_simple_item);
        }

        void bindView(String text) {
            this.simpleTextTv.setText(text);
        }
    }
}
