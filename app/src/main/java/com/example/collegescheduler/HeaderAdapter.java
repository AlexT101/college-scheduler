package com.example.collegescheduler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.HeaderCardViewHolder> {
    private List<Header> HeaderCards;

    public HeaderAdapter(List<Header> HeaderCards) {
        this.HeaderCards = HeaderCards;
    }

    @Override
    public HeaderCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout. exam_list, parent, false);
        return new HeaderCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HeaderCardViewHolder holder, int position) {
        Header HeaderCard = HeaderCards.get(position);
        holder.textViewTitle.setText(HeaderCard.getName());
    }

    @Override
    public int getItemCount() {
        return HeaderCards.size();
    }

    static class HeaderCardViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;

        public HeaderCardViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.exam_Header);
        }
    }
}
