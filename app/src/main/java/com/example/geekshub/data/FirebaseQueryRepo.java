package com.example.geekshub.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseQueryRepo implements IFirebaseQueryRepo {

    private static FirebaseQueryRepo sInstance;

    private Application application;
    private final static DatabaseReference BOOKS_REF = FirebaseDatabase.getInstance().getReference().child("Books");

    private FirebaseQueryRepo(Application application) {
        this.application = application;
    }

    @Override
    public MutableLiveData<List<BookModel>> getListOfBooks(final MutableLiveData<List<BookModel>> listMutableLiveData) {
        BOOKS_REF.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //GenericTypeIndicator<List<BookModel>> t = new GenericTypeIndicator<List<BookModel>>() {};
                List<BookModel> list = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    list.add(data.getValue(BookModel.class));
                }
                listMutableLiveData.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return listMutableLiveData;
    }

    public static FirebaseQueryRepo getInstance(Application application) {
        if (sInstance == null) {
            synchronized (FirebaseQueryRepo.class) {
                if (sInstance == null) {
                    sInstance = new FirebaseQueryRepo(application);
                }
            }
        }
        return sInstance;
    }
}
