package com.example.collegescheduler.ui.assignments;

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

import com.example.collegescheduler.AssignmentCardAdapter;
import com.example.collegescheduler.Data;
import com.example.collegescheduler.Item;
import com.example.collegescheduler.R;
import com.example.collegescheduler.SpacesItemDecoration;
import com.example.collegescheduler.databinding.FragmentAssignmentsBinding;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AssignmentsFragment extends Fragment implements AssignmentCardAdapter.OnDeleteButtonClickListener, AdapterView.OnItemSelectedListener {

    private FragmentAssignmentsBinding binding;
    private RecyclerView recyclerView;
    private Spinner filter;
    private AssignmentCardAdapter adapter;
    private TextView none;
    private void updateNone(){
        if (adapter.getItemCount() == 0){
            none.setVisibility(View.VISIBLE);
        }else{
            none.setVisibility(View.GONE);
        }
    }

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
                adapter.updateItems(Data.items);
                break;
            case "Due Date":
                Collections.sort(Data.items, new Comparator<Item>() {
                    @Override
                    public int compare(Item item1, Item item2) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return item1.getDate().compareTo(item2.getDate());
                    }
                });
                adapter.updateItems(Data.items);
                break;
            case "Course":
                Collections.sort(Data.items, new Comparator<Item>() {
                    @Override
                    public int compare(Item item1, Item item2) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return item1.getCourse().compareTo(item2.getCourse());
                    }
                });
                adapter.updateItems(Data.items);
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback.
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AssignmentsViewModel assignmentsViewModel =
                new ViewModelProvider(this).get(AssignmentsViewModel.class);

        binding = FragmentAssignmentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstance) {

        view = getView();

        filter = view.findViewById(R.id.spinnerFilterAssignment);
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

        recyclerView = view.findViewById(R.id.recyclerViewAssignment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycler_view_spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        adapter = new AssignmentCardAdapter(Data.items, this);
        recyclerView.setAdapter(adapter);

        none = view.findViewById(R.id.text_assignmentsNone);
        updateNone();

        Button addButton = view.findViewById(R.id.addButtonAssignment);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the dialog layout
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_assignments, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(dialogView);

                // Find views inside the dialog
                EditText editTextTitle = dialogView.findViewById(R.id.editTextAssignmentTitle);
                EditText editTextDate = dialogView.findViewById(R.id.editTextAssignmentDue);
                EditText editTextTime = dialogView.findViewById(R.id.editTextAssignmentTime);
                EditText editTextCourse = dialogView.findViewById(R.id.editTextAssignmentCourse);
                Button buttonSaveTask = dialogView.findViewById(R.id.buttonSaveAssignment);
                Button buttonCancelTask = dialogView.findViewById(R.id.buttonCancelAssignment);

                // Create and show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();

                buttonSaveTask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get the user input
                        String classTitle = editTextTitle.getText().toString();
                        String classDate = editTextDate.getText().toString();
                        String classCourse = editTextCourse.getText().toString();
                        String classTime = editTextTime.getText().toString();

                        // Add the new class to the ArrayList
                        Data.items.add(new Item("assignment", classTitle, classDate, classTime, classCourse));

                        // Notify the adapter that the data has changed
                        adapter.updateItems(Data.items);
                        updateNone();

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
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_assignments, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        // Find views inside the dialog
        EditText editTextTitle = dialogView.findViewById(R.id.editTextAssignmentTitle);
        EditText editTextTime = dialogView.findViewById(R.id.editTextAssignmentTime);
        EditText editTextDate = dialogView.findViewById(R.id.editTextAssignmentDue);
        EditText editTextCourse = dialogView.findViewById(R.id.editTextAssignmentCourse);
        Button buttonSaveTask = dialogView.findViewById(R.id.buttonSaveAssignment);
        Button buttonCancelTask = dialogView.findViewById(R.id.buttonCancelAssignment);

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
        // Remove the item from the list
        Data.items.remove(position);
        // Notify the adapter of item removal
        adapter.updateItems(Data.items);
        updateNone();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}