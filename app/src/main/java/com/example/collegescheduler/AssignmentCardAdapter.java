package com.example.collegescheduler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AssignmentCardAdapter extends RecyclerView.Adapter<AssignmentCardAdapter.AssignmentCardViewHolder> {

    private List<Item> assignmentCards;
    private List <Item> displayedCards;
    private HashMap<Integer, Integer> indexMap;
    private AssignmentCardAdapter.OnDeleteButtonClickListener listener;

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(int position);
        void onEditButtonClick(int position);
    }

    public AssignmentCardAdapter(List<Item> assignmentCards, AssignmentCardAdapter.OnDeleteButtonClickListener listener) {

        this.assignmentCards = new ArrayList<>(assignmentCards);
        this.displayedCards = new ArrayList<>();
        this.indexMap = new HashMap<>();
        filterItems();
        this.listener = listener;
    }

    private void filterItems() {
        displayedCards.clear();
        for (int i = 0; i < assignmentCards.size(); i ++) {
            if (assignmentCards.get(i).getType().equals("assignment")) {
                displayedCards.add(assignmentCards.get(i));
                indexMap.put(displayedCards.size() - 1, i);
            }
        }
    }

    public void updateItems(List<Item> newItems) {
        assignmentCards.clear();
        assignmentCards.addAll(newItems);
        filterItems();
        notifyDataSetChanged();
    }

    @Override
    public AssignmentCardAdapter.AssignmentCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_item, parent, false);
        return new AssignmentCardAdapter.AssignmentCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AssignmentCardViewHolder holder, int position) {
        Item assignmentCard = displayedCards.get(position);
        holder.textViewTitle.setText(assignmentCard.getTitle().isEmpty() ? "Untitled Assignment" : assignmentCard.getTitle());

        if (!assignmentCard.getDate().isEmpty() || !assignmentCard.getTime().isEmpty()) {
            String separator = !assignmentCard.getDate().isEmpty() && !assignmentCard.getTime().isEmpty() ? " | " : "";

            String textViewTimeText = assignmentCard.getDate() + separator + assignmentCard.getTime();
            holder.textViewDetails.setText(textViewTimeText);
            holder.textViewDetails.setVisibility(View.VISIBLE);
        } else {
            holder.textViewDetails.setVisibility(View.GONE);
        }


        if (!assignmentCard.getCourse().isEmpty()) {
            holder.textViewCourse.setText(assignmentCard.getCourse());
            holder.textViewCourse.setVisibility(View.VISIBLE);
        }else{
            holder.textViewCourse.setVisibility(View.GONE);
        }

        holder.assignmentEditButton.setOnClickListener(new View.OnClickListener() {
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

        holder.assignmentDeleteButton.setOnClickListener(new View.OnClickListener() {
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

    static class AssignmentCardViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewDetails, textViewCourse;
        ImageButton assignmentDeleteButton, assignmentEditButton;

        public AssignmentCardViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.assignment_Title);
            textViewDetails = itemView.findViewById(R.id.assignment_Details);
            textViewCourse = itemView.findViewById(R.id.Assignment_Course);
            assignmentDeleteButton = itemView.findViewById(R.id.assignment_Edit);
            assignmentEditButton = itemView.findViewById(R.id.assignmentComplete);
        }
    }
}
