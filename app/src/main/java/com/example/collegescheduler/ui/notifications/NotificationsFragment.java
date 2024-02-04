package com.example.collegescheduler.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.ClassCard;
import com.example.collegescheduler.Item;
import com.example.collegescheduler.TaskCard;
import com.example.collegescheduler.TaskCardAdapter;
import com.example.collegescheduler.Data;
import com.example.collegescheduler.R;
import com.example.collegescheduler.SpacesItemDecoration;
import com.example.collegescheduler.databinding.FragmentNotificationsBinding;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.appcompat.app.AlertDialog;

public class NotificationsFragment extends Fragment implements TaskCardAdapter.OnDeleteButtonClickListener, OnItemSelectedListener {

    private FragmentNotificationsBinding binding;

    private RecyclerView recyclerView;

    private Spinner filter;
    private TaskCardAdapter adapter;

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        switch((String)parent.getItemAtPosition(pos)){
            case "Name":
                Collections.sort(Data.items, new Comparator<Item>() {
                    @Override
                    public int compare(Item item1, Item item2) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return item1.getTitle().compareTo(item2.getTitle());
                    }
                });
                adapter.notifyDataSetChanged();
                break;
            case "Due Date":
                Collections.sort(Data.items, new Comparator<Item>() {
                    @Override
                    public int compare(Item item1, Item item2) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return item1.getDate().compareTo(item2.getDate());
                    }
                });
                adapter.notifyDataSetChanged();
                break;
            case "Course":
                Collections.sort(Data.items, new Comparator<Item>() {
                    @Override
                    public int compare(Item item1, Item item2) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return item1.getCourse().compareTo(item2.getCourse());
                    }
                });
                adapter.notifyDataSetChanged();
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback.
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstance) {

        view = getView();

        filter = view.findViewById(R.id.spinnerFilter);
        filter.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> filterAdapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.filter_options,
                android.R.layout.simple_spinner_item
        );

        // Specify the layout to use when the list of choices appears.
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        filter.setAdapter(filterAdapter);

        recyclerView = view.findViewById(R.id.recyclerViewToDo);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycler_view_spacing);

        adapter = new TaskCardAdapter(Data.items, this);
        recyclerView.setAdapter(adapter);

        Button addButton = view.findViewById(R.id.addButtonToDo);
        Button showComplete = view.findViewById(R.id.showCompleted);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the dialog layout
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_task, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(dialogView);

                // Find views inside the dialog
                EditText editTextTitle = dialogView.findViewById(R.id.editTaskTitle);
                EditText editTextTime = dialogView.findViewById(R.id.editTaskTime);
                EditText editTextDate = dialogView.findViewById(R.id.editTaskDate);
                EditText editTextCourse = dialogView.findViewById(R.id.editTaskCourse);
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
                        String classTitle = editTextTitle.getText().toString();
                        String classTime = editTextTime.getText().toString();
                        String classDate = editTextDate.getText().toString();
                        String classCourse = editTextCourse.getText().toString();

                        // Add the new class to the ArrayList
                        Data.items.add(new Item("todo", classTitle, classDate, classTime, classCourse));

                        // Notify the adapter that the data has changed
                        adapter.updateItems(Data.items);

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
        showComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data.showComplete = !Data.showComplete;
                adapter.filterItems();
                if (Data.showComplete){
                    showComplete.setText("Hide Complete");
                }else{
                    showComplete.setText("Show Complete");
                }
            }
        });
    }
    public void onEditButtonClick(int position) {
        // Inflate the dialog layout
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_task, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        // Find views inside the dialog
        EditText editTextTitle = dialogView.findViewById(R.id.editTaskTitle);
        EditText editTextTime = dialogView.findViewById(R.id.editTaskTime);
        EditText editTextDate = dialogView.findViewById(R.id.editTaskDate);
        EditText editTextCourse = dialogView.findViewById(R.id.editTaskCourse);
        Button buttonSaveTask = dialogView.findViewById(R.id.buttonSaveTask);
        Button buttonCancelTask = dialogView.findViewById(R.id.buttonCancelTask);

        editTextTitle.setText(Data.items.get(position).getTitle());
        editTextTime.setText(Data.items.get(position).getTime());
        editTextDate.setText(Data.items.get(position).getDate());
        editTextCourse.setText(Data.items.get(position).getCourse());

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Handle the save button click
        buttonSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user input
                String classTitle = editTextTitle.getText().toString();
                String classTime = editTextTime.getText().toString();
                String classDate = editTextDate.getText().toString();
                String classCourse = editTextCourse.getText().toString();

                Data.items.get(position).setTitle(classTitle);
                Data.items.get(position).setTime(classTime);
                Data.items.get(position).setDate(classDate);
                Data.items.get(position).setCourse(classCourse);

                // Notify the adapter that the data has changed
                adapter.updateItems(Data.items);

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

    @Override
    public void onDeleteButtonClick(int position) {
        Data.items.get(position).setComplete(!Data.items.get(position).getComplete());
        adapter.updateItems(Data.items);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}