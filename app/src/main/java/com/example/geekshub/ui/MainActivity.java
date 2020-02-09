package com.example.geekshub.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geekshub.R;
import com.example.geekshub.data.BookModel;
import com.example.geekshub.viewModel.BooksViewModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    BooksViewModel booksViewModel;
    private static final String TAG = "MainActivity";
    @BindView(R.id.recycler_view_horizontal_list)
    RecyclerView recyclerViewHorizontalList;
    RecyclerViewAdapter recyclerViewAdapter;
    List<BookModel> bookModelArrayList;
    @BindView(R.id.item_txt_title)
    TextView itemTxtTitle;
    @BindView(R.id.item_txt_more)
    TextView itemTxtMore;
    @BindView(R.id.logout_btn)
    Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        FirebaseApp.initializeApp(this);
        bookModelArrayList = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(bookModelArrayList, this);
        recyclerViewHorizontalList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHorizontalList.setAdapter(recyclerViewAdapter);
        booksViewModel = ViewModelProviders.of(this).get(BooksViewModel.class);
        booksViewModel.getListMutableLiveData(this.getApplication()).observe(this, new Observer<List<BookModel>>() {
            @Override
            public void onChanged(List<BookModel> bookModels) {
                recyclerViewAdapter = new RecyclerViewAdapter(bookModels, getBaseContext());
                recyclerViewHorizontalList.setLayoutManager(new LinearLayoutManager(getBaseContext()
                        , LinearLayoutManager.HORIZONTAL, false));
                recyclerViewHorizontalList.setAdapter(recyclerViewAdapter);
            }


        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                sendUserToLogin();

            }
        });


    }

    private void sendUserToLogin() {
    Intent i = new Intent (this , LoginActivity.class);
    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(i);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent i = new Intent(this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }

    }
}
