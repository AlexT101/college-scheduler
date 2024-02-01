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
import com.example.collegescheduler.Data;
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

        adapter = new ClassCardAdapter(Data.classCardList, this);
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
                EditText editTextClassTitle = dialogView.findViewById(R.id.editTextClassTitle);
                EditText editTextClassTime = dialogView.findViewById(R.id.editTextClassTime);
                EditText editTextClassRepeat = dialogView.findViewById(R.id.editTextClassRepeat);
                EditText editTextClassRoom = dialogView.findViewById(R.id.editTextClassRoom);
                EditText editTextClassLocation = dialogView.findViewById(R.id.editTextClassLocation);
                EditText editTextClassSection = dialogView.findViewById(R.id.editTextClassSection);
                EditText editTextClassProfessor = dialogView.findViewById(R.id.editTextClassProfessor);

                Button buttonSaveClass = dialogView.findViewById(R.id.buttonSaveClass);
                Button buttonCancelClass = dialogView.findViewById(R.id.buttonCancelClass);

                // Create and show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();

                // Handle the save button click
                buttonSaveClass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get the user input
                        String classTitle = editTextClassTitle.getText().toString();
                        String classTime = editTextClassTime.getText().toString();
                        String classRepeat = editTextClassRepeat.getText().toString();
                        String classRoom = editTextClassRoom.getText().toString();
                        String classLocation = editTextClassLocation.getText().toString();
                        String classSection = editTextClassSection.getText().toString();
                        String classProfessor = editTextClassProfessor.getText().toString();

                        // Add the new class to the ArrayList
                        Data.classCardList.add(new ClassCard(classTitle, classTime, classLocation, classRepeat, classProfessor, classSection, classRoom));

                        // Notify the adapter that the data has changed
                        adapter.notifyItemInserted(Data.classCardList.size()-1);

                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                });
                buttonCancelClass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
        EditText editTextClassTitle = dialogView.findViewById(R.id.editTextClassTitle);
        EditText editTextClassTime = dialogView.findViewById(R.id.editTextClassTime);
        EditText editTextClassRepeat = dialogView.findViewById(R.id.editTextClassRepeat);
        EditText editTextClassRoom = dialogView.findViewById(R.id.editTextClassRoom);
        EditText editTextClassLocation = dialogView.findViewById(R.id.editTextClassLocation);
        EditText editTextClassSection = dialogView.findViewById(R.id.editTextClassSection);
        EditText editTextClassProfessor = dialogView.findViewById(R.id.editTextClassProfessor);
        Button buttonSaveClass = dialogView.findViewById(R.id.buttonSaveClass);
        Button buttonCancelClass = dialogView.findViewById(R.id.buttonCancelClass);

        editTextClassTitle.setText(Data.classCardList.get(position).getTitle());
        editTextClassTime.setText(Data.classCardList.get(position).getTime());
        editTextClassRepeat.setText(Data.classCardList.get(position).getRepeat());
        editTextClassRoom.setText(Data.classCardList.get(position).getRoom());
        editTextClassLocation.setText(Data.classCardList.get(position).getLocation());
        editTextClassSection.setText(Data.classCardList.get(position).getSection());
        editTextClassProfessor.setText(Data.classCardList.get(position).getProfessor());

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Handle the save button click
        buttonSaveClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user input
                String classTitle = editTextClassTitle.getText().toString();
                String classTime = editTextClassTime.getText().toString();
                String classRepeat = editTextClassRepeat.getText().toString();
                String classRoom = editTextClassRoom.getText().toString();
                String classLocation = editTextClassLocation.getText().toString();
                String classSection = editTextClassSection.getText().toString();
                String classProfessor = editTextClassProfessor.getText().toString();

                Data.classCardList.get(position).setTitle(classTitle);
                Data.classCardList.get(position).setTime(classTime);
                Data.classCardList.get(position).setRepeat(classRepeat);
                Data.classCardList.get(position).setRoom(classRoom);
                Data.classCardList.get(position).setLocation(classLocation);
                Data.classCardList.get(position).setSection(classSection);
                Data.classCardList.get(position).setProfessor(classProfessor);

                // Notify the adapter that the data has changed
                adapter.notifyItemChanged(position);

                // Dismiss the dialog
                dialog.dismiss();
            }
        });
        buttonCancelClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onDeleteButtonClick(int position) {
        // Remove the item from the list
        Data.classCardList.remove(position);
        // Notify the adapter of item removal
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
