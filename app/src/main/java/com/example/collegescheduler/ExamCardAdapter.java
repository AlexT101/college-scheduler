package com.example.collegescheduler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExamCardAdapter extends RecyclerView.Adapter<ExamCardAdapter.ExamCardViewHolder> {

    private List<Item> examCards;
    private ExamCardAdapter.OnDeleteButtonClickListener listener;

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(int position);
        void onEditButtonClick(int position);
    }

    public ExamCardAdapter(List<Item> examCards, ExamCardAdapter.OnDeleteButtonClickListener listener) {

        this.examCards = examCards;
        this.listener = listener;
    }

    @Override
    public ExamCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_item, parent, false);
        return new ExamCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExamCardViewHolder holder, int position) {
        Item examCard = examCards.get(position);
        holder.textViewTitle.setText(examCard.getTitle().isEmpty() ? "Untitled Class" : examCard.getTitle());

        if (!examCard.getLocation().isEmpty() || !examCard.getDate().isEmpty() || !examCard.getTime().isEmpty()) {
            String separator = !examCard.getLocation().isEmpty() && !examCard.getDate().isEmpty()
                    && !examCard.getTime().isEmpty() ? " | " : "";

            String textViewTimeText = examCard.getLocation() + separator + examCard.getDate() + separator + examCard.getTime();
            holder.textViewTime.setText(textViewTimeText);
            holder.textViewTime.setVisibility(View.VISIBLE);
        } else {
            holder.textViewTime.setVisibility(View.GONE);
        }


        if (!examCard.getCourse().isEmpty()) {
            holder.textViewCourse.setText(examCard.getCourse());
            holder.textViewCourse.setVisibility(View.VISIBLE);
        }else{
            holder.textViewCourse.setVisibility(View.GONE);
        }

        holder.examEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = holder.getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteButtonClick(position);
                    }
                }
            }
        });

        holder.examDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = holder.getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onEditButtonClick(position);
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return examCards.size();
    }

    static class ExamCardViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewTime, textViewLocation, textViewDate, textViewCourse;
        ImageButton examDeleteButton, examEditButton;

        public ExamCardViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.exam_Title);
            textViewTime = itemView.findViewById(R.id.exam_Time);
            textViewLocation = itemView.findViewById(R.id.exam_Location);
            textViewDate = itemView.findViewById(R.id.exam_Date);
            textViewCourse = itemView.findViewById(R.id.exam_Course);
            examDeleteButton = itemView.findViewById(R.id.examEdit);
            examEditButton = itemView.findViewById(R.id.examComplete);
        }
    }
}
