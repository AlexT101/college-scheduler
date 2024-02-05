package com.example.collegescheduler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClassCardAdapter extends RecyclerView.Adapter<ClassCardAdapter.ClassCardViewHolder> {

    private List<ClassCard> classCards;
    private OnDeleteButtonClickListener listener;

    //Listener for each card's edit and delete buttons
    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(int position);
        void onEditButtonClick(int position);
    }

    public ClassCardAdapter(List<ClassCard> classCards, OnDeleteButtonClickListener listener) {
        this.classCards = classCards;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClassCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_card, parent, false);
        return new ClassCardViewHolder(view);
    }

    //Display each card based on object data
    @Override
    public void onBindViewHolder(ClassCardViewHolder holder, int position) {
        //Get card at position
        ClassCard classCard = classCards.get(position);

        //Update title
        holder.textViewTitle.setText(classCard.getTitle().isEmpty() ? "Untitled Class" : classCard.getTitle());

        //Update date line
        if (!classCard.getTime().isEmpty() || !classCard.getRepeat().isEmpty()) {
            String separator = !classCard.getTime().isEmpty() && !classCard.getRepeat().isEmpty() ? " | " : "";

            String textViewTimeText = classCard.getTime() + separator + classCard.getRepeat();
            holder.textViewTime.setText(textViewTimeText);
            holder.textViewTime.setVisibility(View.VISIBLE);
        } else {
            holder.textViewTime.setVisibility(View.GONE);
        }

        //Update location line
        if (!classCard.getLocation().isEmpty() || !classCard.getRoom().isEmpty()) {
            String separator = !classCard.getLocation().isEmpty() && !classCard.getRoom().isEmpty() ? " " : "";
            String roomPrecede = !classCard.getRoom().isEmpty() ? "Room " : "";

            String textViewLocationText = classCard.getLocation() + separator + roomPrecede + classCard.getRoom();
            holder.textViewLocation.setText(textViewLocationText);
            holder.textViewLocation.setVisibility(View.VISIBLE);
        } else {
            holder.textViewLocation.setVisibility(View.GONE);
        }

        //Update course line
        if (!classCard.getSection().isEmpty() || !classCard.getProfessor().isEmpty()) {
            String sectionPrecede = !classCard.getSection().isEmpty() ? "Section " : "";
            String professorPrecede = !classCard.getProfessor().isEmpty() ? "Prof. " : "";
            String separator = !classCard.getSection().isEmpty() && !classCard.getProfessor().isEmpty() ? " | " : "";

            String textViewProfText = sectionPrecede + classCard.getSection() + separator + professorPrecede + classCard.getProfessor();
            holder.textViewProf.setText(textViewProfText);
            holder.textViewProf.setVisibility(View.VISIBLE);
        } else {
            holder.textViewProf.setVisibility(View.GONE);
        }

        //Upon edit click, start editing the card at the correct position
        holder.editButton.setOnClickListener(v -> {
            if (listener != null) {
                int position12 = holder.getBindingAdapterPosition();
                if (position12 != RecyclerView.NO_POSITION) {
                    listener.onEditButtonClick(position12);
                }
            }
        });

        //Upon delete click, delete the card at the correct position
        holder.deleteButton.setOnClickListener(v -> {
            if (listener != null) {
                int position1 = holder.getBindingAdapterPosition();
                if (position1 != RecyclerView.NO_POSITION) {
                    listener.onDeleteButtonClick(position1);
                }
            }
        });
    }

    //Get number of displayed cards
    @Override
    public int getItemCount() {
        return classCards.size();
    }

    //Find views by id
    static class ClassCardViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewTime, textViewLocation, textViewProf;
        ImageButton deleteButton, editButton;

        public ClassCardViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewProf = itemView.findViewById(R.id.textViewProf);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }
}
