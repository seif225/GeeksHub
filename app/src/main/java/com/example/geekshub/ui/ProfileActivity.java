package com.example.geekshub.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.geekshub.R;
import com.example.geekshub.data.UserModel;
import com.example.geekshub.viewModel.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.user_name_tv)
    TextView userNameTv;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.button3)
    Button button3;
    private UserViewModel viewModel;
    private UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        SharedPreferences sp = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        final String userId = sp.getString("userId", "");
        viewModel.getUserModelMutableLiveData(userId, getApplication()).observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if (userModel != null) {
                    bindData(userModel);
                }
            }
        });
    }
    private void bindData(UserModel userModel) {
        userNameTv.setText(userModel.getName());
    }
}
