package com.example.kg_cai;

import static com.example.kg_cai.SplashActivity.catList;
import static com.example.kg_cai.SplashActivity.selected_cat_index;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.kg_cai.adapter.SetsAdapter;
import com.example.kg_cai.helpers.SetsModelClass;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class SetsActivity extends AppCompatActivity {

    private Toolbar setsToolbar;
    private RecyclerView setsRecyclerView;
    private FirebaseFirestore firebaseFirestore;

    public static List<SetsModelClass> setsList = new ArrayList<SetsModelClass>();
    public static List<String> setsIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        setsToolbar = findViewById(R.id.setsToolbar);
        setsRecyclerView = findViewById(R.id.setsRecyclerView);

        setSupportActionBar(setsToolbar);
       // getSupportActionBar().setTitle(catList.get(selected_cat_index).getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseFirestore = FirebaseFirestore.getInstance(); //initialization of firestore

       // loadSets();

    }


//    private void loadSets() {
//        setsList.clear(); //Clear the arraylist of the sets
//
//        firebaseFirestore.collection("QUIZ").document(catList.get(selected_cat_index).getId())
//                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot doc) {
//                if (doc.exists()) {
//                    long noOfSets = (long) doc.get("SETS");
//
//                    for (int i = 1; i <= noOfSets; i++) {
//                        String setName = doc.getString("SET" + String.valueOf(i) + "_NAME"); //getting the SETS NAME and loop it to get the CAT_NAME in firebase
//                        String setID = doc.getString("SET" + String.valueOf(i) + "_ID"); //getting the SETS ID and loop it to get the CAT_ID in firebase
//
////                        Log.d("SET NAME: ", setName);
////                        Log.d("SET ID: ", setID);
//
//                        setsList.add(new SetsModelClass(setID, setName, "0", "1"));
//                        setsIDs.add(doc.getString("SET" + String.valueOf(i) + "_ID"));
//
//
//                    }
//
//                    SetsAdapter adapter = new SetsAdapter(setsList);
//                    setsRecyclerView.setAdapter(adapter);
//                }
//            }
//        });
//    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //this is for back button
        if (item.getItemId() == android.R.id.home) {
            SetsActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}