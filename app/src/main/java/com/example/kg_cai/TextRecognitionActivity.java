package com.example.kg_cai;

import static com.example.kg_cai.TextRecogAdapter.correctAns;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kg_cai.helpers.TextRecogModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;

import java.util.ArrayList;
import java.util.List;

public class TextRecognitionActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private Button btnScan;
    private FloatingActionButton btnCapture;
    private TextView txtScanText;
    private RecyclerView textRecogRv;
    private ImageView imgTextRecog;
    Bitmap imageBitmap;

    FirebaseUser firebaseUser;
    String textRecogListAct,correctAnsText;

    TextRecogAdapter textRecogAdapter;
    DatabaseReference databaseReference;
    List<TextRecogModel> list;

    int modified_score_textRecog = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition);

        btnScan = findViewById(R.id.scanTextImg);
        btnCapture = findViewById(R.id.captureTextImg);
        txtScanText = findViewById(R.id.txtScanText);

        imgTextRecog = findViewById(R.id.imgTextRecog);
        textRecogRv= findViewById(R.id.textRecogRv);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("TextRecognition");
        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        textRecogRv.setLayoutManager(manager);
        textRecogRv.setHasFixedSize(true);

        databaseReference.orderByChild("Instructions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TextRecogModel textRecogModel = dataSnapshot.getValue(TextRecogModel.class);
                    list.add(textRecogModel);
                }
                textRecogAdapter = new TextRecogAdapter(list, TextRecognitionActivity.this);
                textRecogRv.setAdapter(textRecogAdapter);
                //ProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
                txtScanText.setText("");
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detectTextFromImg();
                //Toast.makeText(getApplicationContext(), txtScanText.getText().toString().trim(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            btnScan.setVisibility(View.VISIBLE);
            txtScanText.setVisibility(View.VISIBLE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imgTextRecog.setImageBitmap(imageBitmap);
        }
    }

    private void detectTextFromImg() {
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextDetector firebaseVisionTextDetector = FirebaseVision.getInstance().getVisionTextDetector();
        firebaseVisionTextDetector.detectInImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                displayTextFromImg(firebaseVisionText);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Error - ", e.getMessage());
            }
        });
    }

    private void displayTextFromImg(FirebaseVisionText firebaseVisionText) {
        List<FirebaseVisionText.Block> blockList = firebaseVisionText.getBlocks();

        if(blockList.size()==0){
            Toast.makeText(getApplicationContext(), "No text found in Image", Toast.LENGTH_SHORT).show();
        }else{
            for(FirebaseVisionText.Block block: firebaseVisionText.getBlocks()){
                correctAnsText = block.getText();
                txtScanText.setText(correctAnsText);

                //Toast.makeText(getApplicationContext(),correctAns + correctAnsText, Toast.LENGTH_SHORT).show();
            }
                    if(correctAnsText.equals(correctAns)){
                        FirebaseDatabase.getInstance().getReference().child("TextRecognitionScore").child(firebaseUser.getUid()).child("score").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                modified_score_textRecog += Integer.parseInt(snapshot.getValue().toString());
                            }
                            snapshot.getRef().setValue(modified_score_textRecog);
                            Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_LONG).show();
                            TextRecognitionActivity.this.finish();
                            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "Sorry, it's wrong...", Toast.LENGTH_SHORT).show();
                }
        }
    }



}