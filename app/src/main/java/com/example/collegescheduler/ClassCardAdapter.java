package com.example.collegescheduler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.collegescheduler.ClassCard;
import com.example.collegescheduler.R;

public class ClassCardAdapter extends RecyclerView.Adapter<ClassCardAdapter.ClassCardViewHolder> {

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(int position);
        void onEditButtonClick(int position);
    }

    private List<ClassCard> classCards;
    private OnDeleteButtonClickListener listener;

    public ClassCardAdapter(List<ClassCard> classCards, OnDeleteButtonClickListener listener) {
        this.classCards = classCards;
        this.listener = listener;
    }

    @Override
    public ClassCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_card, parent, false);
        return new ClassCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClassCardViewHolder holder, int position) {
        ClassCard classCard = classCards.get(position);
        holder.textViewTitle.setText(classCard.getTitle().isEmpty() ? "Untitled Class" : classCard.getTitle());
        if (!classCard.getTime().isEmpty() || !classCard.getRepeat().isEmpty()) {
            String separator = !classCard.getTime().isEmpty() && !classCard.getRepeat().isEmpty() ? " | " : "";

            String textViewTimeText = classCard.getTime() + separator + classCard.getRepeat();
            holder.textViewTime.setText(textViewTimeText);
        } else {
            holder.textViewTime.setVisibility(View.GONE);
        }
        if (!classCard.getLocation().isEmpty() || !classCard.getRoom().isEmpty()) {
            String separator = !classCard.getLocation().isEmpty() && !classCard.getRoom().isEmpty() ? " " : "";
            String roomPrecede = !classCard.getRoom().isEmpty() ? "Room " : "";

            String textViewLocationText = classCard.getLocation() + separator + roomPrecede + classCard.getRoom();
            holder.textViewLocation.setText(textViewLocationText);
        } else {
            holder.textViewLocation.setVisibility(View.GONE);
        }
        if (!classCard.getSection().isEmpty() || !classCard.getProfessor().isEmpty()) {
            String sectionPrecede = !classCard.getSection().isEmpty() ? "Section " : "";
            String professorPrecede = !classCard.getProfessor().isEmpty() ? "Prof. " : "";
            String separator = !classCard.getSection().isEmpty() && !classCard.getProfessor().isEmpty() ? " | " : "";

            String textViewProfText = sectionPrecede + classCard.getSection() + separator + professorPrecede + classCard.getProfessor();
            holder.textViewProf.setText(textViewProfText);
        } else {
            holder.textViewProf.setVisibility(View.GONE);
        }
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
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
        holder.editButton.setOnClickListener(new View.OnClickListener() {
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
        return classCards.size();
    }

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
