package com.example.collegescheduler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskCardAdapter extends RecyclerView.Adapter<TaskCardAdapter.TaskCardViewHolder> {
    private List<Item> taskCards; //All tasks
    private List<Item> displayedCards; //Displayed tasks
    private HashMap<Integer, Integer> indexMap; //Map of displayed tasks to all tasks
    private OnDeleteButtonClickListener listener;

    //Listener for each card's edit and delete buttons
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

    //Clear displayed cards and re-add items that match target filter
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

    //Clear and re-add all cards when data is updated
    public void updateItems(List<Item> newItems) {
        taskCards.clear();
        taskCards.addAll(newItems);
        filterItems();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_card, parent, false);
        return new TaskCardViewHolder(view);
    }

    //Display each card based on object data
    @Override
    public void onBindViewHolder(@NonNull TaskCardViewHolder holder, int position) {
        //Get card at position
        Item taskCard = displayedCards.get(position);

        //Update checkmark icon if completed
        if (taskCard.getComplete()) {
            holder.toDoDeleteButton.setImageResource(R.drawable.complete);
        }else {
            holder.toDoDeleteButton.setImageResource(R.drawable.incomplete);
        }

        //Update type of item
        String type = taskCard.getType().equals("assignment") ? "ASSIGNMENT" : taskCard.getType().equals("exam") ? "EXAM" : "TODO";
        holder.textViewTaskType.setText(type);

        //Update title
        String title = taskCard.getTitle().isEmpty() ? "Untitled" : taskCard.getTitle();
        holder.textViewToDoTitle.setText(title);

        //Update card based on type
        switch (taskCard.getType()) {
            case "todo":
                //Update date line
                if (!taskCard.getTime().isEmpty() || !taskCard.getDate().isEmpty()) {
                    String separator = !taskCard.getTime().isEmpty() && !taskCard.getDate().isEmpty() ? " | " : "";

                    String textViewToDoTimeText = taskCard.getTime() + separator + taskCard.getDate();
                    holder.textViewToDoTime.setText(textViewToDoTimeText);
                    holder.textViewToDoTime.setVisibility(View.VISIBLE);
                } else {
                    holder.textViewToDoTime.setVisibility(View.GONE);
                }

                //Update course line
                if (!taskCard.getCourse().isEmpty()) {
                    holder.textViewToDoCourse.setText(taskCard.getCourse());
                    holder.textViewToDoCourse.setVisibility(View.VISIBLE);
                } else {
                    holder.textViewToDoCourse.setVisibility(View.GONE);
                }


                break;
            case "assignment":
                //Update date line
                if (!taskCard.getDate().isEmpty() || !taskCard.getTime().isEmpty()) {
                    String separator = !taskCard.getDate().isEmpty() && !taskCard.getTime().isEmpty() ? " | " : "";

                    String textViewTimeText = taskCard.getDate() + separator + taskCard.getTime();
                    holder.textViewToDoTime.setText(textViewTimeText);
                    holder.textViewToDoTime.setVisibility(View.VISIBLE);
                } else {
                    holder.textViewToDoTime.setVisibility(View.GONE);
                }

                //Update course line
                if (!taskCard.getCourse().isEmpty()) {
                    holder.textViewToDoCourse.setText(taskCard.getCourse());
                    holder.textViewToDoCourse.setVisibility(View.VISIBLE);
                } else {
                    holder.textViewToDoCourse.setVisibility(View.GONE);
                }


                break;
            case "exam":
                //Update meeting line
                if (!taskCard.getLocation().isEmpty() || !taskCard.getDate().isEmpty() || !taskCard.getTime().isEmpty()) {
                    String separator = !taskCard.getLocation().isEmpty() && !taskCard.getDate().isEmpty()
                            && !taskCard.getTime().isEmpty() ? " | " : "";

                    String textViewTimeText = taskCard.getLocation() + separator + taskCard.getDate() + separator + taskCard.getTime();
                    holder.textViewToDoTime.setText(textViewTimeText);
                    holder.textViewToDoTime.setVisibility(View.VISIBLE);
                } else {
                    holder.textViewToDoTime.setVisibility(View.GONE);
                }

                //Update course line
                if (!taskCard.getCourse().isEmpty()) {
                    holder.textViewToDoCourse.setText(taskCard.getCourse());
                    holder.textViewToDoCourse.setVisibility(View.VISIBLE);
                } else {
                    holder.textViewToDoCourse.setVisibility(View.GONE);
                }
                break;
        }

        //Upon edit click, start editing the card at the correct position in the full list given the map
        holder.toDoEditButton.setOnClickListener(v -> {
            if (listener != null) {
                int position2 = holder.getBindingAdapterPosition();
                if (position2 != RecyclerView.NO_POSITION) {
                    listener.onEditButtonClick(indexMap.get(position2));
                }
            }
        });

        //Upon delete click, delete the card at the correct position in the full list given the map
        holder.toDoDeleteButton.setOnClickListener(v -> {
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
