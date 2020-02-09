package com.example.geekshub.data;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.geekshub.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    private final static DatabaseReference USER_REF = FirebaseDatabase.getInstance().getReference().child("User");
    public FirebaseQueryRepo(Application application) {
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

    @Override
    public MutableLiveData<UserModel> getUser(final MutableLiveData<UserModel> userModelMutableLiveData , String id) {

        USER_REF.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    userModelMutableLiveData.setValue(dataSnapshot.getValue(UserModel.class));

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return userModelMutableLiveData;
    }

    @Override
    public void setUser(final Context context, UserModel userModel) {
        if (userModel != null) {
            USER_REF.child(userModel.getId()).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(application, "User info has been updated", Toast.LENGTH_SHORT).show();
                        sendUserToMainActivity(context);
                    }
                    else {
                        Toast.makeText(context, " something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void sendUserToMainActivity(Context context) {
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
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
