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
    private List<Item> taskCards;
    private OnDeleteButtonClickListener listener;

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(int position);
        void onEditButtonClick(int position);
    }

    public TaskCardAdapter(List<Item> taskCards, OnDeleteButtonClickListener listener) {
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
        Item taskCard = taskCards.get(position);
        holder.textViewToDoTitle.setText(taskCard.getTitle());
        holder.textViewToDoTime.setText(taskCard.getTime() + " | " + taskCard.getDate());
        holder.textViewToDoCourse.setText(taskCard.getCourse());
        holder.toDoDeleteButton.setOnClickListener(v -> {
            if (listener != null) {
                int position1 = holder.getBindingAdapterPosition();
                if (position1 != RecyclerView.NO_POSITION) {
                    listener.onDeleteButtonClick(position1);
                }
            }
        });
        holder.toDoEditButton.setOnClickListener(new View.OnClickListener() {
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
        return taskCards.size();
    }

    static class TaskCardViewHolder extends RecyclerView.ViewHolder {
        TextView textViewToDoTitle, textViewToDoTime, textViewToDoCourse;
        ImageButton toDoDeleteButton, toDoEditButton;

        public TaskCardViewHolder(View itemView) {
            super(itemView);
            textViewToDoTitle = itemView.findViewById(R.id.textViewTaskTitle);
            textViewToDoTime = itemView.findViewById(R.id.textViewTaskTime);
            textViewToDoCourse = itemView.findViewById(R.id.textViewTaskCourse);
            toDoDeleteButton = itemView.findViewById(R.id.toDoDeleteButton);
            toDoEditButton = itemView.findViewById(R.id.toDoEditButton);

        }
    }
}
