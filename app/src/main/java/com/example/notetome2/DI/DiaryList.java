package com.example.notetome2.DI;

import com.example.notetome2.Diary;

import java.util.ArrayList;

public class DiaryList {

    public static ArrayList<Diary> data = new ArrayList<>();

    private DiaryList(ArrayList<Diary> data){
        this.data = data;
    }

    public static DiaryList getInstance(ArrayList<Diary> data){
        return new DiaryList(data);
    }
}
