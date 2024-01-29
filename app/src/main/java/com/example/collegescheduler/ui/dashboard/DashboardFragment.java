package com.example.collegescheduler.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.ExamCard;
import com.example.collegescheduler.ExamCardAdapter;
import com.example.collegescheduler.Header;
import com.example.collegescheduler.HeaderAdapter;
import com.example.collegescheduler.R;
import com.example.collegescheduler.SpacesItemDecoration;
import com.example.collegescheduler.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    private RecyclerView recyclerView;
    private ExamCardAdapter ExamAdapter;

    private HeaderAdapter headerAdapter;
    private List<ExamCard> ExamCardList;
    private List<Header> headerList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstance) {

        view = getView();


        recyclerView = view.findViewById(R.id.ExamAssignmentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycler_view_spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        ExamCardList = new ArrayList<>();
        ExamCardList.add(new ExamCard("CS 2340 Final", "6:00", "IC103","1/24/24"));

        ExamAdapter = new ExamCardAdapter(ExamCardList);
        recyclerView.setAdapter(ExamAdapter);

        headerList = new ArrayList<>();
        headerList.add(new Header("Exams"));

        headerAdapter = new HeaderAdapter(headerList);
        recyclerView.setAdapter(headerAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}