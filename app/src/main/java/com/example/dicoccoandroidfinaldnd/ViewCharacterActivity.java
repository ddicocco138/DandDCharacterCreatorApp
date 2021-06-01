package com.example.dicoccoandroidfinaldnd;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewCharacterActivity extends AppCompatActivity {

    private List<character> characterList = new ArrayList<>();
    private RecyclerView recyclerView;
    private characterAdapter adapter;
    private Button searchButton;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference charRef = db.collection("character").document("AmnSxEnApyLtnDkQRwf5");

    private static final String TAG = "ViewCharacterActivity";

    private String KEY_RACE = "Race";
    private String KEY_CLASS = "Class";
    private String KEY_BACKGROUND = "Background";
    private String KEY_WEAPON = "Weapon";
    private String KEY_STRENGTH = "STR";
    private String KEY_DEXTERITY = "DEX";
    private String KEY_CON = "CON";
    private String KEY_INTELLIGENCE = "INT";
    private String KEY_WISDOM = "WIS";
    private String KEY_CHARISMA = "CHR";
    private String KEY_NAME = "Name";

    private String race, charClass, backg, weap, name, charDocName;
    private int str, dex, con, intel, wis, chr;

    private TextView recyclerName;
    private  List<DocumentSnapshot> myListOfDocuments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_character);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.characterLogRecycler);
        adapter = new characterAdapter(characterList, new characterAdapter.ClickListener() {
            @Override
            public void onViewClicked(int position) {
                loadChar(position);
            }

            @Override
            public void onEditClicked(int position) {
                editChar(position);
            }

            @Override
            public void onLogClicked(int position) {
                logChar(position);
            }
        });
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        searchButton = findViewById(R.id.saveLogButton);

        recyclerName = findViewById(R.id.logRecyclerDate);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getEmail();


        db.collection("character")
                .whereEqualTo("Creator", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            myListOfDocuments = task.getResult().getDocuments();
                            Toast.makeText(getApplicationContext(),"Query Made",Toast.LENGTH_LONG).show();
                            for (int i=0; i<myListOfDocuments.size(); i++) {
                                String charID = myListOfDocuments.get(i).getId();
                                String charName = myListOfDocuments.get(i).get("Name").toString();
                                characterList.add(new character(charID, charName));
                                adapter.notifyDataSetChanged();
                            }



                        }
                    }
                });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(getApplicationContext(), SearchCharacterActivity.class);
                startActivity(p);
            }
        });
    }

    public void editChar(int position){
        Intent myStatsIntent = new Intent(this, StatCharCreationActivity.class);
        character character = adapter.characterList.get(position);
        charDocName = character.getCharacterID();
        charRef = db.collection("character").document(charDocName);
        charRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            if (documentSnapshot.exists()) {
                                race = documentSnapshot.getString(KEY_RACE);
                                charClass = documentSnapshot.getString(KEY_CLASS);
                                backg = documentSnapshot.getString(KEY_BACKGROUND);
                                weap = documentSnapshot.getString(KEY_WEAPON);
                                name = documentSnapshot.getString(KEY_NAME);
                                str = documentSnapshot.getLong(KEY_STRENGTH).intValue();
                                con = documentSnapshot.getLong(KEY_CON).intValue();
                                intel = documentSnapshot.getLong(KEY_INTELLIGENCE).intValue();
                                chr = documentSnapshot.getLong(KEY_CHARISMA).intValue();
                                dex = documentSnapshot.getLong(KEY_DEXTERITY).intValue();
                                wis = documentSnapshot.getLong(KEY_WISDOM).intValue();

                            } else {
                                Toast.makeText(ViewCharacterActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                            }

                        }
                        myStatsIntent.putExtra("Race", race);
                        myStatsIntent.putExtra("Class", charClass);
                        myStatsIntent.putExtra("Background", backg);
                        myStatsIntent.putExtra("Weapon", weap);
                        myStatsIntent.putExtra("Str", str);
                        myStatsIntent.putExtra("Dex", dex);
                        myStatsIntent.putExtra("Con", con);
                        myStatsIntent.putExtra("Int", intel);
                        myStatsIntent.putExtra("Wis", wis);
                        myStatsIntent.putExtra("Chr", chr);
                        myStatsIntent.putExtra("Edit", 1);
                        myStatsIntent.putExtra("Id", charDocName);
                        myStatsIntent.putExtra("Name", name);
                        startActivity(myStatsIntent);
                    }
                });
    }
    public void CreatePDF(){
        String path = getExternalFilesDir(null).toString()+"/user.pdf";
        File file = new File(path);

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Document document = new com.itextpdf.text.Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();

        Font myfont = new Font(Font.FontFamily.HELVETICA, 24);

        Paragraph paragraph = new Paragraph();
        paragraph.add(new Paragraph("Name: " + name));
        paragraph.add(new Paragraph("Race: " + race));
        paragraph.add(new Paragraph("Class: " + charClass));
        paragraph.add(new Paragraph("Background: " + backg +  "\n"));
        paragraph.add(new Paragraph("Health Points: HP     Armour Class: AC     Hit Dice: HD    Speed: 30\n"));
        paragraph.add(new Paragraph("Strength: " + String.valueOf(str) + " Modifier: mod\n"));
        paragraph.add(new Paragraph("Dexterity: " + String.valueOf(dex) + " Modifier: mod\n"));
        paragraph.add(new Paragraph("Constitution: " + String.valueOf(con) + " Modifier: mod\n"));
        paragraph.add(new Paragraph("Intelligence: " + String.valueOf(intel) + " Modifier: mod\n"));
        paragraph.add(new Paragraph("Wisdom: " + String.valueOf(wis) + " Modifier: mod\n"));
        paragraph.add(new Paragraph("Charisma: " + String.valueOf(chr) + " Modifier: mod\n"));
        paragraph.add(new Paragraph("Strength: " + String.valueOf(str) +   " Modifier: mod\n"));
        paragraph.add(new Paragraph("Weapon: " + weap + " Damage: 1D6\n"));
        paragraph.add(new Paragraph("Features ________________________________________________________________________________________________" +
                "________________________________________________________________________________________________" +
                "________________________________________________________________________________________________" +
                "________________________________________________________________________________________________" +
                "________________________________________________________________________________________________" +
                "________________________________________________________________________________________________" +
                "________________________________________________________________________________________________"));
        paragraph.add(new Paragraph("Notes ___________________________________________________________________________________________________"+
                "___________________________________________________________________________________________________" +
                "___________________________________________________________________________________________________" +
                "___________________________________________________________________________________________________" +
                "___________________________________________________________________________________________________" +
                "___________________________________________________________________________________________________" +
                "___________________________________________________________________________________________________" +
                "___________________________________________________________________________________________________"));




        try {
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.close();

        Toast.makeText(ViewCharacterActivity.this, "Created pdf", Toast.LENGTH_LONG).show();
    }


    public void viewPDF() {
        Intent i = new Intent(getApplicationContext(), ViewCharacterPdf.class);
        startActivity(i);
    }

    public void loadChar(int position){
        character character = adapter.characterList.get(position);
        charDocName = character.getCharacterID();
        charRef = db.collection("character").document(charDocName);
        charRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            if (documentSnapshot.exists()) {
                                race = documentSnapshot.getString(KEY_RACE);
                                charClass = documentSnapshot.getString(KEY_CLASS);
                                backg = documentSnapshot.getString(KEY_BACKGROUND);
                                weap = documentSnapshot.getString(KEY_WEAPON);
                                name = documentSnapshot.getString(KEY_NAME);
                                str = documentSnapshot.getLong(KEY_STRENGTH).intValue();
                                con = documentSnapshot.getLong(KEY_CON).intValue();
                                intel = documentSnapshot.getLong(KEY_INTELLIGENCE).intValue();
                                chr = documentSnapshot.getLong(KEY_CHARISMA).intValue();
                                dex = documentSnapshot.getLong(KEY_DEXTERITY).intValue();
                                wis = documentSnapshot.getLong(KEY_WISDOM).intValue();

                            } else {
                                Toast.makeText(ViewCharacterActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                            }
                            CreatePDF();
                            viewPDF();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ViewCharacterActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }

    public void logChar(int position)
    {
        Intent myStatsIntent = new Intent(this, LogActivity.class);
        character character = adapter.characterList.get(position);
        charDocName = character.getCharacterID();
        charRef = db.collection("character").document(charDocName);
        charRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            if (documentSnapshot.exists()) {
                                race = documentSnapshot.getString(KEY_RACE);
                                charClass = documentSnapshot.getString(KEY_CLASS);
                                backg = documentSnapshot.getString(KEY_BACKGROUND);
                                weap = documentSnapshot.getString(KEY_WEAPON);
                                name = documentSnapshot.getString(KEY_NAME);
                                str = documentSnapshot.getLong(KEY_STRENGTH).intValue();
                                con = documentSnapshot.getLong(KEY_CON).intValue();
                                intel = documentSnapshot.getLong(KEY_INTELLIGENCE).intValue();
                                chr = documentSnapshot.getLong(KEY_CHARISMA).intValue();
                                dex = documentSnapshot.getLong(KEY_DEXTERITY).intValue();
                                wis = documentSnapshot.getLong(KEY_WISDOM).intValue();

                            } else {
                                Toast.makeText(ViewCharacterActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                            }

                        }
                        myStatsIntent.putExtra("Race", race);
                        myStatsIntent.putExtra("Class", charClass);
                        myStatsIntent.putExtra("Background", backg);
                        myStatsIntent.putExtra("Weapon", weap);
                        myStatsIntent.putExtra("Str", str);
                        myStatsIntent.putExtra("Dex", dex);
                        myStatsIntent.putExtra("Con", con);
                        myStatsIntent.putExtra("Int", intel);
                        myStatsIntent.putExtra("Wis", wis);
                        myStatsIntent.putExtra("Chr", chr);
                        myStatsIntent.putExtra("Edit", 1);
                        myStatsIntent.putExtra("Id", charDocName);
                        myStatsIntent.putExtra("Name", name);
                        startActivity(myStatsIntent);
                    }
                });

    }
}