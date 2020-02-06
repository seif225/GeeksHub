package com.example.geekshub.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import com.example.geekshub.R;
import com.example.geekshub.viewModel.AuthViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.vHalfCircle)
    View vHalfCircle;
    @BindView(R.id.vSmallCircle)
    View vSmallCircle;
    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.tvHello)
    TextView tvHello;
    @BindView(R.id.tvUsername)
    TextView tvUsername;
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.tvPassword)
    TextView tvPassword;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.tvIForgot)
    TextView tvIForgot;
    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.vVerticalLine)
    View vVerticalLine;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tvSignUp)
    TextView tvSignUp;
    AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
        final ProgressDialog ps = new ProgressDialog(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (mail.isEmpty()) {
                    etUsername.requestFocus();
                    etUsername.setError("you have to write your email");
                } else if (password.isEmpty()) {
                    etPassword.requestFocus();
                    etPassword.setError("you have to pick a password");
                } else {
                    viewModel.Authenticate(mail, password, ps, getBaseContext());

                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.sendUserToSignUp(getBaseContext());
            }
        });

    }
}
