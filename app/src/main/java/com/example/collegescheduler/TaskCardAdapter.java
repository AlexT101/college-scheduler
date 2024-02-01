package com.example.collegescheduler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.collegescheduler.TaskCard;
import com.example.collegescheduler.R;

public class TaskCardAdapter extends RecyclerView.Adapter<TaskCardAdapter.TaskCardViewHolder> {
    private List<TaskCard> taskCards;
    private OnDeleteButtonClickListener listener;

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(int position);
    }

    public TaskCardAdapter(List<TaskCard> taskCards, OnDeleteButtonClickListener listener) {
        this.taskCards = taskCards;
        this.listener = listener;
    }

    @Override
    public TaskCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_card, parent, false);
        return new TaskCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskCardViewHolder holder, int position) {
        TaskCard taskCard = taskCards.get(position);
        holder.textViewTask.setText(taskCard.getTask());
        holder.textViewToDoTitle.setText(taskCard.getToDoTitle());
        holder.toDoDeleteButton.setOnClickListener(v -> {
            if (listener != null) {
                int position1 = holder.getBindingAdapterPosition();
                if (position1 != RecyclerView.NO_POSITION) {
                    listener.onDeleteButtonClick(position1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskCards.size();
    }

    static class TaskCardViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTask, textViewToDoTitle;
        ImageButton toDoDeleteButton;

        public TaskCardViewHolder(View itemView) {
            super(itemView);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewToDoTitle = itemView.findViewById(R.id.textViewTaskTitle);
            toDoDeleteButton = itemView.findViewById(R.id.toDoDeleteButton);
        }
    }
}
