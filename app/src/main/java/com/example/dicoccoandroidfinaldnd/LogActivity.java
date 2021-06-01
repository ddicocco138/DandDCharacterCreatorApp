package com.example.dicoccoandroidfinaldnd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LogActivity extends AppCompatActivity {

    private Bundle charBun;
    private TextView charTitle;
    private Button logEntry;
    private RecyclerView recyclerView;
    private logAdapter adapter;
    private List<Log> logList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        charBun = getIntent().getExtras();
        String charID = charBun.getString("Id");
        String charName = charBun.getString("Name");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.characterLogRecycler);
        adapter = new logAdapter(logList);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        charTitle = findViewById(R.id.newlogCharNameTxt);
        charTitle.setText(charName);


        db.collection("log")
                .whereEqualTo("logCharID",charID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            Toast.makeText(getApplicationContext(),"Query Made",Toast.LENGTH_LONG).show();
                            for (int i=0; i<myListOfDocuments.size(); i++) {

                                String logDate = myListOfDocuments.get(i).get("logDate").toString();
                                String logItem = myListOfDocuments.get(i).get("logItem").toString();
                                String logStatBonus = myListOfDocuments.get(i).get("logStatBonus").toString();
                                String logComment = myListOfDocuments.get(i).get("logComment").toString();

                                logList.add(new Log(charID,logDate,logItem,logStatBonus,logComment));
                                adapter.notifyDataSetChanged();
                            }



                        }
                    }
                });

        logEntry = findViewById(R.id.saveLogButton);
        logEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent p = new Intent(getApplicationContext(), NewLog.class);
                p.putExtra("Id", charID);
                p.putExtra("Name", charName);
                startActivity(p);



            }
        });
    }
}