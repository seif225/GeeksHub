package com.example.geekshub.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.geekshub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.vHalfCircle)
    View vHalfCircle;
    @BindView(R.id.vSmallCircle)
    View vSmallCircle;
    @BindView(R.id.tvSignUp)
    TextView tvSignUp;
    @BindView(R.id.tvHello)
    TextView tvHello;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.tvPassword)
    TextView tvPassword;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.tvConfirmPassword)
    TextView tvConfirmPassword;
    @BindView(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.vVerticalLine)
    View vVerticalLine;
    @BindView(R.id.btn_SignUp)
    Button btnSignUp;
    @BindView(R.id.tvHaveAccount)
    TextView tvHaveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String passwordRe = etConfirmPassword.getText().toString();
                if (email.isEmpty()) {
                    etEmail.requestFocus();
                    etEmail.setError("you have to write you email address");
                } else if (password.isEmpty()) {
                    etPassword.requestFocus();
                    etPassword.setError("you have to make a password");
                } else if (passwordRe.isEmpty()) {
                    etEmail.requestFocus();
                    etEmail.setError("you have to confirm your passowrd");
                } else if (!password.equals(passwordRe)) {
                    Toast.makeText(SignUpActivity.this, "Your password doesn't match", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(email, password, progressDialog);

                }

            }
        });
    }

    private void registerUser(String email, String password, final ProgressDialog progressDialog) {

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        progressDialog.setCancelable(false);
        progressDialog.setMessage("please wait ...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("MyPref", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userId", mAuth.getUid());
                    progressDialog.dismiss();
                    sendUserToInfo();
                }

                progressDialog.dismiss();

            }
        });


    }

    private void sendUserToInfo() {
        Intent i = new Intent(this, InfoActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
