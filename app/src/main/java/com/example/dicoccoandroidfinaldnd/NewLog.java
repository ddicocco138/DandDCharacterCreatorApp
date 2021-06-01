package com.example.dicoccoandroidfinaldnd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NewLog extends AppCompatActivity {

    private Bundle charBun;
    private Button newLog;
    private EditText Date;
    private EditText Items;
    private EditText StatBonus;
    private EditText Comment;
    private TextView CharName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_log);

        Intent myAddIntent = new Intent (NewLog.this, LogActivity.class);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        newLog = findViewById(R.id.saveLogButton);
        Date = findViewById(R.id.editTextLogDate);
        Items = findViewById(R.id.editTextLogItems);
        StatBonus = findViewById(R.id.editTextLogStats);
        Comment = findViewById(R.id.editTextLogComment);
        CharName = findViewById(R.id.newlogCharNameTxt);

        charBun = getIntent().getExtras();
        String charID = charBun.getString("Id");
        String charName = charBun.getString("Name");

        String currentDate = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());

        CharName.setText(charName);
        Date.setText(currentDate);

        newLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newlogDate = Date.getText().toString();
                String newlogItems = Items.getText().toString();
                String newlogStatBonus = StatBonus.getText().toString();
                String newlogComment = Comment.getText().toString();



                Map<String, Object> Log = new HashMap<>();
                Log.put("logCharID", charID);
                Log.put("logComment", newlogComment);
                Log.put("logDate", newlogDate);
                Log.put("logItem", newlogItems);
                Log.put("logStatBonus", newlogStatBonus);

                db.collection("log")
                        .add(Log)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(),"New Log Added!" + documentReference.getId(),Toast.LENGTH_LONG).show();
                                startActivity(myAddIntent);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Error adding Log",Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });

    }
}