package com.example.kg_cai;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ProfileActivity extends AppCompatActivity {

    private TextView txtName, txtEmail, btnChangePic;
    private ImageView imgProfile;
    private Button btnSaveChanges;

    private FirebaseUser firebaseUser;

    Uri pickedImg;

    FirebaseAuth firebaseAuth;

    static int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtEmail = findViewById(R.id.txtProfileEmail);
        txtName = findViewById(R.id.txtProfileName);
        imgProfile = findViewById(R.id.imgProfile);
        btnChangePic = findViewById(R.id.btnChangeProfile);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();

        txtName.setText(firebaseUser.getDisplayName());
        txtEmail.setText(firebaseUser.getEmail());

        Glide.with(ProfileActivity.this).load(firebaseUser.getPhotoUrl()).into(imgProfile);

        btnChangePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= 22){
                    checkAndRequestPermission();
                }else{
                    openGallery();
                }
            }
        });

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserModel user = new UserModel(txtName.getText().toString(), txtEmail.getText().toString());
                updateUi(txtName.getText().toString(),pickedImg, firebaseAuth.getCurrentUser());
                ProfileActivity.this.finish();
            }
        });

    }

    private void checkAndRequestPermission() {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else{
            openGallery();
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image") , REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(data!=null){
                pickedImg = data.getData();
                imgProfile.setImageURI(pickedImg); //set it into registerActivity imageview
                btnSaveChanges.setVisibility(View.VISIBLE);
            }

        }
    }


    private void updateUi(String name, Uri pickedImg, FirebaseUser currentUser) {
        StorageReference reference = FirebaseStorage.getInstance().getReference().child("user_image");
        final StorageReference imgFilePath = reference.child(pickedImg.getLastPathSegment());

        imgFilePath.putFile(pickedImg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imgFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();
                        currentUser.updateProfile(userProfileChangeRequest);
                    }
                });
            }
        });
    }
}