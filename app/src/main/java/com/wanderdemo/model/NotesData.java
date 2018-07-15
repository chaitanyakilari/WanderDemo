package com.wanderdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class NotesData implements Serializable {

    @SerializedName("notes")
    @Expose
    private String notes;

//    @SerializedName("wander")
//    @Expose
//    private ArrayList<ResultData> resultDataArrayList;
//
//
//    public ArrayList<ResultData> getResultDataArrayList() {
//        return resultDataArrayList;
//    }
//
//    public void setResultDataArrayList(ArrayList<ResultData> resultDataArrayList) {
//        this.resultDataArrayList = resultDataArrayList;
//    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


//    public class ResultData {
//
//
//        @SerializedName("notestext")
//        @Expose
//        private String notestext;
//
//
//        public String getNotestext() {
//            return notestext;
//        }
//
//        public void setNotestext(String notestext) {
//            this.notestext = notestext;
//        }
//    }
}
