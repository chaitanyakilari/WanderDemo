package com.wanderdemo.model.rest;


import com.wanderdemo.model.NotesData;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface ApiInterface {


    @GET("/wander/.json")
    Call<Map<String, NotesData>> getNotesData();

    @GET("/wander/.json")
    Observable<Map<String, NotesData>> getNotes();

    @PUT("/wander/{title}.json")
    Observable<NotesData> saveNotes(@Path("title") String title, @Body NotesData notesData);

}
