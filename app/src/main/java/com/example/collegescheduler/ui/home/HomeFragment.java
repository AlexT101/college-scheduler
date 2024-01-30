package com.example.collegescheduler.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.ClassCard;
import com.example.collegescheduler.ClassCardAdapter;
import com.example.collegescheduler.R;
import com.example.collegescheduler.SpacesItemDecoration;
import com.example.collegescheduler.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;

public class HomeFragment extends Fragment implements ClassCardAdapter.OnDeleteButtonClickListener {

    private FragmentHomeBinding binding;

    private RecyclerView recyclerView;
    private ClassCardAdapter adapter;
    private List<ClassCard> classCardList;
    final String default_name = "Untitled Class";
    final String default_time = "No Time";
    final String default_location = "No Location";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstance) {

        view = getView();

        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycler_view_spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        classCardList = new ArrayList<>();
        classCardList.add(new ClassCard("CS2340", "TR: 2:00-3:15", "IC103"));
        classCardList.add(new ClassCard("CS1332", "MWF: 2:00-2:50", "CULC144"));
        classCardList.add(new ClassCard("CS1332", "MWF: 2:00-2:50", "CULC144"));
        classCardList.add(new ClassCard("CS1332", "MWF: 2:00-2:50", "CULC144"));

        adapter = new ClassCardAdapter(classCardList, this);
        recyclerView.setAdapter(adapter);

        Button addButton = view.findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the dialog layout
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_class, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(dialogView);

                // Find views inside the dialog
                EditText editTextClassName = dialogView.findViewById(R.id.editTextClassName);
                EditText editTextClassTime = dialogView.findViewById(R.id.editTextClassTime);
                EditText editTextClassLocation = dialogView.findViewById(R.id.editTextClassLocation);
                Button buttonSaveClass = dialogView.findViewById(R.id.buttonSaveClass);

                // Create and show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();

                // Handle the save button click
                buttonSaveClass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get the user input
                        String className = editTextClassName.getText().toString();
                        String classTime = editTextClassTime.getText().toString();
                        String classLocation = editTextClassLocation.getText().toString();

                        if (className.equals("")) className=default_name;
                        if (classTime.equals("")) classTime=default_time;
                        if (classLocation.equals("")) classLocation=default_location;

                        // Add the new class to the ArrayList
                        classCardList.add(new ClassCard(className, classTime, classLocation));

                        // Notify the adapter that the data has changed
                        adapter.notifyDataSetChanged();

                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                });
            }
        });

    }
    @Override
    public void onEditButtonClick(int position) {
        // Inflate the dialog layout
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_class, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        // Find views inside the dialog
        EditText editTextClassName = dialogView.findViewById(R.id.editTextClassName);
        EditText editTextClassTime = dialogView.findViewById(R.id.editTextClassTime);
        EditText editTextClassLocation = dialogView.findViewById(R.id.editTextClassLocation);
        Button buttonSaveClass = dialogView.findViewById(R.id.buttonSaveClass);

        editTextClassName.setText(classCardList.get(position).getTitle());
        editTextClassTime.setText(classCardList.get(position).getTime());
        editTextClassLocation.setText(classCardList.get(position).getLocation());

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Handle the save button click
        buttonSaveClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user input
                String className = editTextClassName.getText().toString();
                String classTime = editTextClassTime.getText().toString();
                String classLocation = editTextClassLocation.getText().toString();

                if (className.equals("")) className=default_name;
                if (classTime.equals("")) classTime=default_time;
                if (classLocation.equals("")) classLocation=default_location;

                classCardList.get(position).setTitle(className);
                classCardList.get(position).setTime(classTime);
                classCardList.get(position).setLocation(classLocation);

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();

                // Dismiss the dialog
                dialog.dismiss();
            }
        });
        // Notify the adapter of item removal
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onDeleteButtonClick(int position) {
        // Remove the item from the list
        classCardList.remove(position);
        // Notify the adapter of item removal
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}