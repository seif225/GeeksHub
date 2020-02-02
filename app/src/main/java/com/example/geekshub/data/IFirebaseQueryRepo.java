package com.example.geekshub.data;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public interface IFirebaseQueryRepo {

    MutableLiveData<List<BookModel>> getListOfBooks(MutableLiveData<List<BookModel>> listMutableLiveData);

}
