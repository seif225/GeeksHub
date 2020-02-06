package com.example.geekshub.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.geekshub.dataStructuresAndAlgorithims.BooksSorting;
import com.example.geekshub.R;
import com.example.geekshub.data.BookModel;
import com.example.geekshub.viewModel.BooksViewModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

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
                if (bookModels != null)
                    for (int i = 0; i <bookModels.size() ; i++) {
                        BooksSorting.DesMergeSort(bookModels);
                        Log.d(TAG, "onChanged: " + bookModels.get(i).getName() + " " + bookModels.get(i).getPrice());
                    }
            }


        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            Intent i = new Intent(this,LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }

    }
}
