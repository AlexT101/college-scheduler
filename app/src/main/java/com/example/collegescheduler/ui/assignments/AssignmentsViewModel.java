package com.example.collegescheduler.ui.assignments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AssignmentsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AssignmentsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Assignments Fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}