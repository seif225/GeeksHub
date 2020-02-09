package com.example.geekshub.data;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.geekshub.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FirebaseQueryRepo implements IFirebaseQueryRepo {
    private static FirebaseQueryRepo sInstance;
    private static final String TAG = "FirebaseQueryRepo";
    private Application application;
    private final static StorageReference STORAGE_REF = FirebaseStorage.getInstance().getReference();
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
    public MutableLiveData<UserModel> getUser(final MutableLiveData<UserModel> userModelMutableLiveData, String id) {
        USER_REF.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
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
                    if (task.isSuccessful()) {
                        Toast.makeText(application, "User info has been updated", Toast.LENGTH_SHORT).show();
                        sendUserToMainActivity(context);
                    } else {
                        Toast.makeText(context, " something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void addBook(BookModel bookModel, final Context context) {
        Observable<BookModel> observable = Observable.just(bookModel);
        Observer<BookModel> single = new Observer<BookModel>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(final BookModel bookModel) {

                final String random = UUID.randomUUID().toString();
                final String fileName = random + ".jpg";
                STORAGE_REF.child(fileName).putFile(bookModel.getImageUri()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        STORAGE_REF.child(fileName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.e(TAG, uri + " this is my uri piiiic ");
                                String link = uri.toString();
                                Log.e(TAG, link + " this is pic link ");
                                bookModel.setPicture(link);
                                Log.e(TAG, link + " added the pic to the object ! ");

                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                BOOKS_REF.child(random).setValue(bookModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.e(TAG, "onComplete: " + " i went this far");
                                        if (task.isSuccessful()) {
                                            Toast.makeText(context, bookModel.getName() + " has been added successfully", Toast.LENGTH_SHORT).show();
                                            Log.e(TAG, "success: " );
                                        } else {
                                            Toast.makeText(context, "Error has occurred.. try again", Toast.LENGTH_SHORT).show();
                                            Log.e(TAG, "not success: " );

                                        }
                                    }
                                });

                            }
                        });

                    }
                });

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }


            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: " );
            }
        };
        observable.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribe(single);
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
