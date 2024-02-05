package com.example.collegescheduler.ui.todos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TodosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TodosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("ToDo List");
    }

    public LiveData<String> getText() {
        return mText;
    }
}