package com.example.collegescheduler.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.ClassCard;
import com.example.collegescheduler.TaskCard;
import com.example.collegescheduler.TaskCardAdapter;
import com.example.collegescheduler.R;
import com.example.collegescheduler.SpacesItemDecoration;
import com.example.collegescheduler.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;

public class NotificationsFragment extends Fragment implements TaskCardAdapter.OnDeleteButtonClickListener {

    private FragmentNotificationsBinding binding;

    private RecyclerView recyclerView;
    private TaskCardAdapter adapter;
    private List<TaskCard> taskCardList;

    final String default_name = "Untitled Task";
    final String default_time = "No TITLE";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstance) {

        view = getView();

        recyclerView = view.findViewById(R.id.recyclerViewToDo);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycler_view_spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        taskCardList = new ArrayList<>();
        taskCardList.add(new TaskCard("Task 1", "TITLE"));

        adapter = new TaskCardAdapter(taskCardList, this);
        recyclerView.setAdapter(adapter);

        Button addButton = view.findViewById(R.id.addButtonToDo);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the dialog layout
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_task, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(dialogView);

                // Find views inside the dialog
                EditText editTextTaskName = dialogView.findViewById(R.id.editTaskName);
                EditText editTextTaskTITLE = dialogView.findViewById(R.id.editTaskTITLE);
                Button buttonSaveTask = dialogView.findViewById(R.id.buttonSaveTask);
                Button buttonCancelTask = dialogView.findViewById(R.id.buttonCancelTask);

                // Create and show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();

                // Handle the save button click
                buttonSaveTask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get the user input
                        String className = editTextTaskName.getText().toString();
                        String classTime = editTextTaskTITLE.getText().toString();

                        // Add the new class to the ArrayList
                        taskCardList.add(new TaskCard(className, classTime));

                        // Notify the adapter that the data has changed
                        adapter.notifyDataSetChanged();

                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                });
                buttonCancelTask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });
    }
    public void onEditButtonClick(int position) {
        // Inflate the dialog layout
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_task, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        // Find views inside the dialog
        EditText editTextTask = dialogView.findViewById(R.id.editTaskName);
        EditText editTextTaskTITLE = dialogView.findViewById(R.id.editTaskTITLE);
        Button buttonSaveTask = dialogView.findViewById(R.id.buttonSaveTask);
        Button buttonCancelTask = dialogView.findViewById(R.id.buttonCancelTask);

        editTextTask.setText(taskCardList.get(position).getTask());
        editTextTaskTITLE.setText(taskCardList.get(position).getToDoTitle());

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Handle the save button click
        buttonSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user input
                String className = editTextTask.getText().toString();
                String classTime = editTextTaskTITLE.getText().toString();

                if (className.equals("")) className=default_name;
                if (classTime.equals("")) classTime=default_time;

                taskCardList.get(position).setTask(className);
                taskCardList.get(position).setToDoTitle(classTime);

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();

                // Dismiss the dialog
                dialog.dismiss();
            }
        });
        buttonCancelTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // Notify the adapter of item removal
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onDeleteButtonClick(int position) {
        // Remove the item from the list
        taskCardList.remove(position);
        // Notify the adapter of item removal
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}