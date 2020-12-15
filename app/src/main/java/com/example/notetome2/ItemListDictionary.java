package com.example.notetome2;

public class ItemListDictionary {
    private String diary;
    private String note;

    public String getDiary() {
        return diary;
    }

    public void setDiary(String diary) {
        this.diary = diary;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ItemListDictionary(String diary, String note) {
        this.diary = diary;
        this.note = note;
    }
}
