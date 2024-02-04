package com.example.collegescheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Data {
    public static List<ClassCard> classCardList = new ArrayList<>();
    public static List<Item> items = new ArrayList<>(); //Store all todos, exams, and assignments here and filter as needed
    public static boolean showComplete = true;
    public static ArrayList<String> getAllClasses(){
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < classCardList.size(); i ++){
            String cur = classCardList.get(i).getTitle();
            if (!temp.contains(cur)){
                temp.add(cur);
            }
        }
        Collections.sort(temp);
        return temp;
    }
}
