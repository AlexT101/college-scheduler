package com.example.collegescheduler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExamCardAdapter extends RecyclerView.Adapter<ExamCardAdapter.ExamCardViewHolder> {

    private List<Item> examCards; //All exams
    private List <Item> displayedCards; //Displayed exams
    private HashMap<Integer, Integer> indexMap; //Map of displayed exams to all exams
    private ExamCardAdapter.OnDeleteButtonClickListener listener;

    //Listener for each card's edit and delete buttons
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

    //Clear displayed cards and re-add items that match target filter
    private void filterItems() {
        displayedCards.clear();
        for (int i = 0; i < examCards.size(); i ++) {
            if (examCards.get(i).getType().equals("exam")) {
                displayedCards.add(examCards.get(i));
                indexMap.put(displayedCards.size() - 1, i);
            }
        }
    }

    //Clear and re-add all cards when data is updated
    public void updateItems(List<Item> newItems) {
        examCards.clear();
        examCards.addAll(newItems);
        filterItems();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExamCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_item, parent, false);
        return new ExamCardViewHolder(view);
    }

    //Display each card based on object data
    @Override
    public void onBindViewHolder(ExamCardViewHolder holder, int position) {
        //Get card at position
        Item examCard = displayedCards.get(position);

        //Update title
        holder.textViewTitle.setText(examCard.getTitle().isEmpty() ? "Untitled" : examCard.getTitle());

        //Update meeting line
        if (!examCard.getLocation().isEmpty() || !examCard.getDate().isEmpty() || !examCard.getTime().isEmpty()) {
            String separator = !examCard.getLocation().isEmpty() && !examCard.getDate().isEmpty()
                    && !examCard.getTime().isEmpty() ? " | " : "";

            String textViewTimeText = examCard.getLocation() + separator + examCard.getDate() + separator + examCard.getTime();
            holder.textViewDetails.setText(textViewTimeText);
            holder.textViewDetails.setVisibility(View.VISIBLE);
        } else {
            holder.textViewDetails.setVisibility(View.GONE);
        }

        //Update course line
        if (!examCard.getCourse().isEmpty()) {
            holder.textViewCourse.setText(examCard.getCourse());
            holder.textViewCourse.setVisibility(View.VISIBLE);
        }else{
            holder.textViewCourse.setVisibility(View.GONE);
        }

        //Upon edit click, start editing the card at the correct position in the full list given the map
        holder.examEditButton.setOnClickListener(v -> {
            if (listener != null) {
                int position2 = holder.getBindingAdapterPosition();
                if (position2 != RecyclerView.NO_POSITION) {
                    listener.onEditButtonClick(indexMap.get(position2));
                }
            }
        });

        //Upon delete click, delete the card at the correct position in the full list given the map
        holder.examDeleteButton.setOnClickListener(v -> {
            if (listener != null) {
                int position1 = holder.getBindingAdapterPosition();
                if (position1 != RecyclerView.NO_POSITION) {
                    listener.onDeleteButtonClick(indexMap.get(position1));
                }
            }
        });
    }

    //Get number of displayed cards
    @Override
    public int getItemCount() {
        return displayedCards.size();
    }

    //Find views by id
    static class ExamCardViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewDetails, textViewCourse;
        ImageButton examDeleteButton, examEditButton;

        public ExamCardViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.exam_Title);
            textViewDetails = itemView.findViewById(R.id.exam_details);
            textViewCourse = itemView.findViewById(R.id.exam_Course);
            examEditButton = itemView.findViewById(R.id.examEdit);
            examDeleteButton = itemView.findViewById(R.id.examComplete);
        }
    }
}
