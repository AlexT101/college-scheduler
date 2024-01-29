package com.example.collegescheduler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExamCardAdapter extends RecyclerView.Adapter<ExamCardAdapter.ExamCardViewHolder> {
    private List<ExamCard> ExamCards;

    public ExamCardAdapter(List<ExamCard> ExamCards) {
        this.ExamCards = ExamCards;
    }

    @Override
    public ExamCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_item, parent, false);
        return new ExamCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExamCardViewHolder holder, int position) {
        ExamCard ExamCard = ExamCards.get(position);
        holder.textViewTitle.setText(ExamCard.getTitle());
        holder.textViewTime.setText(ExamCard.getTime());
        holder.textViewLocation.setText(ExamCard.getLocation());
    }

    @Override
    public int getItemCount() {
        return ExamCards.size();
    }

    static class ExamCardViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewTime, textViewLocation, textViewDate;

        public ExamCardViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.exam_Title);
            textViewTime = itemView.findViewById(R.id.exam_Time);
            textViewLocation = itemView.findViewById(R.id.exam_Location);
            textViewDate = itemView.findViewById(R.id.exam_Date);
        }
    }
}
