package com.example.collegescheduler;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.collegescheduler.TaskCard;
import com.example.collegescheduler.R;

public class TaskCardAdapter extends RecyclerView.Adapter<TaskCardAdapter.TaskCardViewHolder> {
    private List<Item> taskCards;
    private List<Item> displayedCards;
    private HashMap<Integer, Integer> indexMap;
    private OnDeleteButtonClickListener listener;

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(int position);
        void onEditButtonClick(int position);
    }

    public TaskCardAdapter(List<Item> taskCards, OnDeleteButtonClickListener listener) {
        this.taskCards = new ArrayList<>(taskCards);
        this.displayedCards = new ArrayList<>();
        this.indexMap = new HashMap<>();
        filterItems();
        this.listener = listener;
    }

    public void filterItems() {
        displayedCards.clear();
        for (int i = 0; i < taskCards.size(); i ++) {
            if (Data.showComplete || !taskCards.get(i).getComplete()) {
                displayedCards.add(taskCards.get(i));
                indexMap.put(displayedCards.size() - 1, i);
            }
        }
        notifyDataSetChanged();
    }

    public void updateItems(List<Item> newItems) {
        taskCards.clear();
        taskCards.addAll(newItems);
        filterItems();
        notifyDataSetChanged();
    }

    @Override
    public TaskCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_card, parent, false);
        return new TaskCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskCardViewHolder holder, int position) {
        Item taskCard = displayedCards.get(position);

        if (taskCard.getComplete()) {
            holder.toDoDeleteButton.setImageResource(R.drawable.complete);
        }else {
            holder.toDoDeleteButton.setImageResource(R.drawable.incomplete);
        }

        String type = taskCard.getType().equals("assignment") ? "ASSIGNMENT" : taskCard.getType().equals("exam") ? "EXAM" : "TODO";
        holder.textViewTaskType.setText(type);

        String title = taskCard.getTitle().isEmpty() ? "Untitled" : taskCard.getTitle();
        holder.textViewToDoTitle.setText(title);

        if (taskCard.getType().equals("todo")) {
            if (!taskCard.getTime().isEmpty() || !taskCard.getDate().isEmpty()) {
                String separator = !taskCard.getTime().isEmpty() && !taskCard.getDate().isEmpty() ? " | " : "";

                String textViewToDoTimeText = taskCard.getTime() + separator + taskCard.getDate();
                holder.textViewToDoTime.setText(textViewToDoTimeText);
                holder.textViewToDoTime.setVisibility(View.VISIBLE);
            } else {
                holder.textViewToDoTime.setVisibility(View.GONE);
            }
            if (!taskCard.getCourse().isEmpty()) {
                holder.textViewToDoCourse.setText(taskCard.getCourse());
                holder.textViewToDoCourse.setVisibility(View.VISIBLE);
            } else {
                holder.textViewToDoCourse.setVisibility(View.GONE);
            }


        }else if (taskCard.getType().equals("assignment")){
            if (!taskCard.getDate().isEmpty() || !taskCard.getTime().isEmpty()) {
                String separator = !taskCard.getDate().isEmpty() && !taskCard.getTime().isEmpty() ? " | " : "";

                String textViewTimeText = taskCard.getDate() + separator + taskCard.getTime();
                holder.textViewToDoTime.setText(textViewTimeText);
                holder.textViewToDoTime.setVisibility(View.VISIBLE);
            } else {
                holder.textViewToDoTime.setVisibility(View.GONE);
            }


            if (!taskCard.getCourse().isEmpty()) {
                holder.textViewToDoCourse.setText(taskCard.getCourse());
                holder.textViewToDoCourse.setVisibility(View.VISIBLE);
            }else{
                holder.textViewToDoCourse.setVisibility(View.GONE);
            }


        }else if (taskCard.getType().equals("exam")){
            if (!taskCard.getLocation().isEmpty() || !taskCard.getDate().isEmpty() || !taskCard.getTime().isEmpty()) {
                String separator = !taskCard.getLocation().isEmpty() && !taskCard.getDate().isEmpty()
                        && !taskCard.getTime().isEmpty() ? " | " : "";

                String textViewTimeText = taskCard.getLocation() + separator + taskCard.getDate() + separator + taskCard.getTime();
                holder.textViewToDoTime.setText(textViewTimeText);
                holder.textViewToDoTime.setVisibility(View.VISIBLE);
            } else {
                holder.textViewToDoTime.setVisibility(View.GONE);
            }

            if (!taskCard.getCourse().isEmpty()) {
                holder.textViewToDoCourse.setText(taskCard.getCourse());
                holder.textViewToDoCourse.setVisibility(View.VISIBLE);
            }else{
                holder.textViewToDoCourse.setVisibility(View.GONE);
            }
        }
        holder.toDoDeleteButton.setOnClickListener(v -> {
            if (listener != null) {
                int position1 = holder.getBindingAdapterPosition();
                if (position1 != RecyclerView.NO_POSITION) {
                    listener.onDeleteButtonClick(indexMap.get(position1));
                }
            }
        });

        holder.toDoEditButton.setOnClickListener(new View.OnClickListener() {
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

    static class TaskCardViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout toDoCard;
        TextView textViewToDoTitle, textViewToDoTime, textViewToDoCourse, textViewTaskType;
        ImageButton toDoDeleteButton, toDoEditButton;

        public TaskCardViewHolder(View itemView) {
            super(itemView);
            textViewToDoTitle = itemView.findViewById(R.id.textViewTaskTitle);
            textViewToDoTime = itemView.findViewById(R.id.textViewTaskTime);
            textViewToDoCourse = itemView.findViewById(R.id.textViewTaskCourse);
            toDoDeleteButton = itemView.findViewById(R.id.toDoDeleteButton);
            toDoEditButton = itemView.findViewById(R.id.toDoEditButton);
            toDoCard = itemView.findViewById(R.id.toDoCard);
            textViewTaskType = itemView.findViewById(R.id.textViewTaskType);

        }
    }
}
