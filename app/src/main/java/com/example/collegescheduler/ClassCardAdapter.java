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
        holder.textViewTitle.setText(classCard.getTitle());
        holder.textViewTime.setText(classCard.getTime());
        holder.textViewLocation.setText(classCard.getLocation());
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
    }

    @Override
    public int getItemCount() {
        return classCards.size();
    }

    static class ClassCardViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewTime, textViewLocation;
        ImageButton deleteButton;

        public ClassCardViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
