package com.example.geekshub.data;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public interface IFirebaseQueryRepo {

    MutableLiveData<List<BookModel>> getListOfBooks(MutableLiveData<List<BookModel>> listMutableLiveData);
    MutableLiveData<UserModel> getUser(MutableLiveData<UserModel> userModelMutableLiveData,String id);
    void setUser(Context context, UserModel userModelMutableLiveData);

}
