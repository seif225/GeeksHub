package com.example.geekshub.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.geekshub.R;
import com.example.geekshub.data.BookModel;
import com.example.geekshub.viewModel.BooksViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdminPanelActivity extends AppCompatActivity {

    private static final String TAG = "AdminPanelActivity";
    @BindView(R.id.tv_publisher)
    TextView tvPublisher;
    @BindView(R.id.et_publisher)
    EditText etPublisher;
    @BindView(R.id.tv_authorName)
    TextView tvAuthorName;
    @BindView(R.id.et_AuthorName)
    EditText etAuthorName;
    @BindView(R.id.tv_bookName)
    TextView tvBookName;
    @BindView(R.id.et_bookName)
    EditText etBookName;
    @BindView(R.id.tv_bookPrice)
    TextView tvBookPrice;
    @BindView(R.id.et_bookPrice)
    EditText etBookPrice;
    @BindView(R.id.tv_bookGenre)
    TextView tvBookGenre;
    @BindView(R.id.spinnerGenre)
    Spinner spinnerGenre;
    @BindView(R.id.tv_bookPhoto)
    TextView tvBookPhoto;
    @BindView(R.id.img_bookPhoto)
    CircleImageView imgBookPhoto;
    @BindView(R.id.btn_upload_bookPhoto)
    FloatingActionButton btnUploadBookPhoto;
    @BindView(R.id.btn_done)
    Button btnDone;
    Uri resultUri;
    BooksViewModel viewModel;
    @BindView(R.id.tv_Description)
    TextView tvDescription;
    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.logout_button)
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(BooksViewModel.class);
        btnUploadBookPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(AdminPanelActivity.this);

            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String publisher = etPublisher.getText().toString();
                String author = etAuthorName.getText().toString();
                String bookName = etBookName.getText().toString();
                String price = etBookPrice.getText().toString();
                String gener = getGenerName(spinnerGenre.getSelectedItemPosition());
                String des =etDescription.getText().toString();
                if (publisher.isEmpty()) {
                    etPublisher.requestFocus();
                    etPublisher.setError(" you cant leave this field empty");
                } else if (author.isEmpty()) {
                    etAuthorName.requestFocus();
                    etAuthorName.setError(" you cant leave this field empty");
                } else if (bookName.isEmpty()) {
                    etBookName.requestFocus();
                    etBookName.setError(" you cant leave this field empty");
                } else if (price.isEmpty()) {
                    etBookPrice.requestFocus();
                    etBookPrice.setError(" you cant leave this field empty");
                } else if (gener.isEmpty() || gener == null) {
                    Toast.makeText(AdminPanelActivity.this, "you must choose a category", Toast.LENGTH_SHORT).show();
                } else if (resultUri == null) {
                    Toast.makeText(AdminPanelActivity.this, "you must choose a photo", Toast.LENGTH_SHORT).show();
                }
                 else if (des.isEmpty()) {
                etDescription.requestFocus();
                etDescription.setError(" you cant leave this field empty");
            }
                else {
                    BookModel bookModel = new BookModel();
                    bookModel.setName(bookName);
                    bookModel.setPrice(Integer.parseInt(price));
                    bookModel.setAuthor(author);
                    bookModel.setPublisher(publisher);
                    bookModel.setDescribtion(des);
                    bookModel.setImageUri(resultUri);
                    Log.e(TAG, "onClick: " + resultUri );
                    viewModel.addBook(bookModel, getBaseContext(),getApplication());
                }


            }
        });

    }

    private String getGenerName(int selectedItemId) {

        String genere = "";
        switch (selectedItemId) {
            case 0:
                genere = "Comedy";
                break;
            case 1:
                genere = "Programming";
                break;
            case 2:
                genere = "Sci-fi";
                break;
            case 3:
                genere = "Political";
                break;
            case 4:
                genere = "Children";
                break;
            case 5:
                genere = "Philosophy";
                break;
            case 6:
                genere = "Religion";
                break;
            case 7:
                genere = "Adventure";
                break;


        }
        return genere;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
