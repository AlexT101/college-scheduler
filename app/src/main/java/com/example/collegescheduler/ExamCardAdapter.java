package com.example.collegescheduler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExamCardAdapter extends RecyclerView.Adapter<ExamCardAdapter.ExamCardViewHolder> {

    private List<Item> examCards;
    private List <Item> displayedCards;
    private HashMap<Integer, Integer> indexMap;
    private ExamCardAdapter.OnDeleteButtonClickListener listener;

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(int position);
        void onEditButtonClick(int position);
    }

    public ExamCardAdapter(List<Item> examCards, ExamCardAdapter.OnDeleteButtonClickListener listener) {

        this.examCards = new ArrayList<>(examCards);
        this.displayedCards = new ArrayList<>();
        this.indexMap = new HashMap<>();
        filterItems();
        this.listener = listener;
    }

    private void filterItems() {
        displayedCards.clear();
        for (int i = 0; i < examCards.size(); i ++) {
            if (examCards.get(i).getType().equals("exam")) {
                displayedCards.add(examCards.get(i));
                indexMap.put(displayedCards.size() - 1, i);
            }
        }
    }

    public void updateItems(List<Item> newItems) {
        examCards.clear();
        examCards.addAll(newItems);
        filterItems();
        notifyDataSetChanged();
    }

    @Override
    public ExamCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_item, parent, false);
        return new ExamCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExamCardViewHolder holder, int position) {
        Item examCard = displayedCards.get(position);
        holder.textViewTitle.setText(examCard.getTitle().isEmpty() ? "Untitled Exam" : examCard.getTitle());

        if (!examCard.getLocation().isEmpty() || !examCard.getDate().isEmpty() || !examCard.getTime().isEmpty()) {
            String separator = !examCard.getLocation().isEmpty() && !examCard.getDate().isEmpty()
                    && !examCard.getTime().isEmpty() ? " | " : "";

            String textViewTimeText = examCard.getLocation() + separator + examCard.getDate() + separator + examCard.getTime();
            holder.textViewDetails.setText(textViewTimeText);
            holder.textViewDetails.setVisibility(View.VISIBLE);
        } else {
            holder.textViewDetails.setVisibility(View.GONE);
        }

        Log.d("MyApp", "Location: " + examCard.getLocation());


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
                        listener.onDeleteButtonClick(indexMap.get(position));
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
                        listener.onEditButtonClick(indexMap.get(position));
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return displayedCards.size();
    }

    static class ExamCardViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewDetails, textViewCourse;
        ImageButton examDeleteButton, examEditButton;

        public ExamCardViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.exam_Title);
            textViewDetails = itemView.findViewById(R.id.exam_details);
            textViewCourse = itemView.findViewById(R.id.exam_Course);
            examDeleteButton = itemView.findViewById(R.id.examEdit);
            examEditButton = itemView.findViewById(R.id.examComplete);
        }
    }
}
