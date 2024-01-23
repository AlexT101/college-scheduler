package com.example.collegescheduler;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private RecyclerView recyclerView;
    private ClassCardAdapter adapter;
    private List<ClassCard> classCardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //Adding class cards to recycler view
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycler_view_spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        classCardList = new ArrayList<>();
        classCardList.add(new ClassCard("CS2340", "TR: 2:00-3:15", "IC103"));
        classCardList.add(new ClassCard("CS1332", "MWF: 2:00-2:50", "CULC144"));
        classCardList.add(new ClassCard("CS1332", "MWF: 2:00-2:50", "CULC144"));
        classCardList.add(new ClassCard("CS1332", "MWF: 2:00-2:50", "CULC144"));

        adapter = new ClassCardAdapter(classCardList);
        recyclerView.setAdapter(adapter);
    }

}