package com.example.geekshub.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.geekshub.R;
import com.example.geekshub.data.UserModel;
import com.example.geekshub.viewModel.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class InfoActivity extends AppCompatActivity {

    @BindView(R.id.vHalfCircle)
    View vHalfCircle;
    @BindView(R.id.vSmallCircle)
    View vSmallCircle;
    @BindView(R.id.profilePicture)
    CircleImageView profilePicture;
    @BindView(R.id.upload_pic_fab)
    FloatingActionButton uploadPicFab;
    @BindView(R.id.user_name_profile)
    TextView userNameProfile;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.user_name_et)
    EditText userNameEt;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.email_address_tv)
    TextView emailAddressTv;
    @BindView(R.id.mail_address_et)
    EditText mailAddressEt;
    @BindView(R.id.btn_SignUp)
    Button btnSignUp;
    @BindView(R.id.vSmallCircle2)
    View vSmallCircle2;
    private UserViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);
        SharedPreferences sp = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        final String id = sp.getString("userId","");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        viewModel.getUserModelMutableLiveData(id,getApplication()).observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                progressDialog.setTitle("Please wait ...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                bindData(userModel ,progressDialog);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userNameEt.getText().toString();
                String phone = phoneEt.getText().toString();
                String mail = mailAddressEt.getText().toString();
                if(name.isEmpty()){
                    userNameEt.requestFocus();
                    userNameEt.setError("you have to write your name");
                }
                else if (mail.isEmpty()){
                    mailAddressEt.requestFocus();
                    mailAddressEt.setError("you have to write your name");

                }
                else if (phone.isEmpty() || phone.length()<11){
                    phoneEt.requestFocus();
                    phoneEt.setError("this phone number is incorrect");
                }
                else {
                    UserModel userModel = new UserModel();
                    userModel.setEmail(mail);
                    userModel.setName(name);
                    userModel.setPhone(phone);
                    userModel.setId(id);
                    viewModel.setUserModel(userModel,getApplication());
                }
            }
        });




    }

    private void bindData(UserModel userModel, ProgressDialog progressDialog) {
        if(userModel.getPhoto()!= null) Picasso.get().load(userModel.getPhoto()).into(profilePicture);

        mailAddressEt.setText(userModel.getEmail());
        phoneEt.setText(userModel.getPhone());
        userNameEt.setText(userModel.getName());
        progressDialog.dismiss();
    }
}
