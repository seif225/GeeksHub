package com.example.geekshub.viewModel;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.geekshub.ui.MainActivity;
import com.example.geekshub.ui.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AuthViewModel extends ViewModel {


    private FirebaseAuth mAuth;

    public void Authenticate(String mail, String password, final ProgressDialog progressDialog, final Context context) {
        progressDialog.setMessage("please wait ..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    final String userId = mAuth.getUid();
                    editor.putString("userId", userId);
                    FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child("Users").child(userId).exists()) {
                                progressDialog.dismiss();
                                sendUserToMain(context);
                            } else {
                                progressDialog.dismiss();
                                sendUserToInfo(context);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Sorry dude , something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Sorry dude , something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendUserToMain(Context context) {
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    private void sendUserToInfo(Context context) {
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public void sendUserToSignUp(Context baseContext) {
        Intent i = new Intent(baseContext, SignUpActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        baseContext.startActivity(i);
    }
}
