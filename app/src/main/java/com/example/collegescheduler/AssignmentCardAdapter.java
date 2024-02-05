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

public class AssignmentCardAdapter extends RecyclerView.Adapter<AssignmentCardAdapter.AssignmentCardViewHolder> {

    private List<Item> assignmentCards; //All assignments
    private List <Item> displayedCards; //Displayed assignments
    private HashMap<Integer, Integer> indexMap; //Map of displayed assignments : all assignments
    private AssignmentCardAdapter.OnDeleteButtonClickListener listener;

    //Listener for each card's edit and delete buttons
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

    //Clear displayed cards and re-add items that match target filter
    private void filterItems() {
        displayedCards.clear();
        for (int i = 0; i < assignmentCards.size(); i ++) {
            if (assignmentCards.get(i).getType().equals("assignment")) {
                displayedCards.add(assignmentCards.get(i));
                indexMap.put(displayedCards.size() - 1, i);
            }
        }
    }

    //Clear and re-add all cards when data is updated
    public void updateItems(List<Item> newItems) {
        assignmentCards.clear();
        assignmentCards.addAll(newItems);
        filterItems();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AssignmentCardAdapter.AssignmentCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_item, parent, false);
        return new AssignmentCardAdapter.AssignmentCardViewHolder(view);
    }

    //Display each card based on object data
    @Override
    public void onBindViewHolder(AssignmentCardViewHolder holder, int position) {
        //Get card at position
        Item assignmentCard = displayedCards.get(position);

        //Update title
        holder.textViewTitle.setText(assignmentCard.getTitle().isEmpty() ? "Untitled" : assignmentCard.getTitle());

        //Update date line
        if (!assignmentCard.getDate().isEmpty() || !assignmentCard.getTime().isEmpty()) {
            String separator = !assignmentCard.getDate().isEmpty() && !assignmentCard.getTime().isEmpty() ? " | " : "";

            String textViewTimeText = assignmentCard.getDate() + separator + assignmentCard.getTime();
            holder.textViewDetails.setText(textViewTimeText);
            holder.textViewDetails.setVisibility(View.VISIBLE);
        } else {
            holder.textViewDetails.setVisibility(View.GONE);
        }

        //Update course line
        if (!assignmentCard.getCourse().isEmpty()) {
            holder.textViewCourse.setText(assignmentCard.getCourse());
            holder.textViewCourse.setVisibility(View.VISIBLE);
        }else{
            holder.textViewCourse.setVisibility(View.GONE);
        }

        //Upon edit click, start editing the card at the correct position in the full list given the map
        holder.assignmentEditButton.setOnClickListener(v -> {
            if (listener != null) {
                int position2 = holder.getBindingAdapterPosition();
                if (position2 != RecyclerView.NO_POSITION) {
                    listener.onEditButtonClick(indexMap.get(position2));
                }
            }
        });

        //Upon delete click, delete the card at the correct position in the full list given the map
        holder.assignmentDeleteButton.setOnClickListener(v -> {
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
    static class AssignmentCardViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewDetails, textViewCourse;
        ImageButton assignmentDeleteButton, assignmentEditButton;

        public AssignmentCardViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.assignment_Title);
            textViewDetails = itemView.findViewById(R.id.assignment_Details);
            textViewCourse = itemView.findViewById(R.id.Assignment_Course);
            assignmentEditButton = itemView.findViewById(R.id.assignment_Edit);
            assignmentDeleteButton = itemView.findViewById(R.id.assignmentComplete);
        }
    }
}
