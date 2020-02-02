package com.example.geekshub.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.example.geekshub.R;
import com.example.geekshub.data.BookModel;
import com.example.geekshub.data.FirebaseQueryRepo;
import com.example.geekshub.viewModel.BooksViewModel;
import com.google.firebase.FirebaseApp;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    BooksViewModel booksViewModel;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        booksViewModel = ViewModelProviders.of(this).get(BooksViewModel.class);
        booksViewModel.getListMutableLiveData(this.getApplication()).observe(this, new Observer<List<BookModel>>() {
            @Override
            public void onChanged(List<BookModel> bookModels) {
                if (bookModels != null) Log.d(TAG, "onChanged: " + bookModels.get(0).getName());
            }

        });




    }
}
