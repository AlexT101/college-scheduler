package com.example.collegescheduler.ui.classes;

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
import com.example.collegescheduler.databinding.FragmentClassesBinding;

import androidx.appcompat.app.AlertDialog;

public class ClassesFragment extends Fragment implements ClassCardAdapter.OnDeleteButtonClickListener {

    private FragmentClassesBinding binding;

    private ClassCardAdapter adapter;

    //If there are no items, display none message
    private TextView none;
    private void updateNone(){
        if (adapter.getItemCount() == 0){
            none.setVisibility(View.VISIBLE);
        }else{
            none.setVisibility(View.GONE);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ClassesViewModel classesViewModel =
                new ViewModelProvider(this).get(ClassesViewModel.class);

        binding = FragmentClassesBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance) {

        view = getView();

        //Put items into recycler view
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycler_view_spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        adapter = new ClassCardAdapter(Data.classCardList, this);
        recyclerView.setAdapter(adapter);

        //Update no items message
        none = view.findViewById(R.id.text_classesNone);
        updateNone();

        //Functionality for adding a new item
        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
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
            buttonSaveClass.setOnClickListener(v1 -> {
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
                updateNone();

                // Dismiss the dialog
                dialog.dismiss();
            });
            buttonCancelClass.setOnClickListener(v2 -> dialog.dismiss());
        });

    }

    //Functionality for editing items
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
        buttonSaveClass.setOnClickListener(v -> {
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
        });
        buttonCancelClass.setOnClickListener(v -> dialog.dismiss());
    }

    @Override
    public void onDeleteButtonClick(int position) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.delete_confirmation, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        Button buttonDeleteConfirmed = dialogView.findViewById(R.id.deleteConfirmed);
        Button buttonDeleteCancel = dialogView.findViewById(R.id.deleteCancel);

        AlertDialog dialog = builder.create();
        dialog.show();

        buttonDeleteConfirmed.setOnClickListener(v -> {
            Data.classCardList.remove(position);
            adapter.notifyItemRemoved(position);
            updateNone();
            dialog.dismiss();
        });
        buttonDeleteCancel.setOnClickListener(v -> dialog.dismiss());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
