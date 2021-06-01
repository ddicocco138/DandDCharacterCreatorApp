package com.example.dicoccoandroidfinaldnd;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

public class SearchCharacterActivity extends AppCompatActivity {

    private List<character> characterList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private Button searchButton, addButton;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Spinner raceSpinner, classSpinner, backgroundSpinner;
    private TextView racetext, classtext, backgroundtext;
    private DocumentReference charRef = db.collection("character").document("AmnSxEnApyLtnDkQRwf5");
    private String TAG = "SearchCharacterActivity";

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
    private String uid;

    private Intent myStatsIntent;
    private String race, charClass, backg, weap, name, charDocName;
    private int str, dex, con, intel, wis, chr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_character);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        myStatsIntent = new Intent(this, StatCharCreationActivity.class);
        recyclerView = (RecyclerView) findViewById(R.id.characterLogRecycler);
        adapter = new SearchAdapter(characterList, new SearchAdapter.TheClickListener() {
            @Override
            public void onViewClicked(int position) {
                loadChar(position);
            }
            @Override
            public void onAddClicked(int position) {
                copyCharacter(position);
            }
        });
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        searchButton = findViewById(R.id.saveLogButton);
        raceSpinner = (Spinner) findViewById(R.id.RaceSpinner);
        classSpinner = (Spinner) findViewById(R.id.ClassSpinner);
        backgroundSpinner = (Spinner) findViewById(R.id.BackgroundSpinner);
        racetext = findViewById(R.id.newlogCharNameTxt);
        classtext = findViewById(R.id.textView6);
        backgroundtext = findViewById(R.id.textView7);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getEmail();

        ArrayAdapter<String> myRaceAdapter = new ArrayAdapter<String>(SearchCharacterActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.RaceArray));
        myRaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> myClassAdapter = new ArrayAdapter<String>(SearchCharacterActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ClassArray));
        myClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> myBackgroundAdapter = new ArrayAdapter<String>(SearchCharacterActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.BackgroundArray));
        myBackgroundAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raceSpinner.setAdapter(myRaceAdapter);
        classSpinner.setAdapter(myClassAdapter);
        backgroundSpinner.setAdapter(myBackgroundAdapter);

        db.collection("character")
                .whereEqualTo("Public", true)

                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            Toast.makeText(getApplicationContext(),"Query Made",Toast.LENGTH_LONG).show();
                            for (int i=0; i<myListOfDocuments.size(); i++) {
                                String charName;
                                String charID = myListOfDocuments.get(i).getId();
                                String email = myListOfDocuments.get(i).get("Creator").toString();
                                if(email.equals(uid)){
                                    charName = myListOfDocuments.get(i).get("Name").toString() + "***";
                                }else {
                                    charName = myListOfDocuments.get(i).get("Name").toString();
                                }
                                characterList.add(new character(charID, charName));
                                adapter.notifyDataSetChanged();
                            }



                        }
                    }
                });

    }

    public void FilterResults(View view) {
        characterList.clear();
        adapter.notifyDataSetChanged();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String filterRace = raceSpinner.getSelectedItem().toString();
        String filterClass = classSpinner.getSelectedItem().toString();
        String filterBackground = backgroundSpinner.getSelectedItem().toString();



        Toast.makeText(getApplicationContext(), filterRace + filterClass + filterBackground, Toast.LENGTH_LONG).show();

        if (filterRace .equals("All") == false && filterClass.equals("All") == false && filterBackground.equals("All") == false) {


            //bool for ignore filter?
            db.collection("character")
                    .whereEqualTo("Class", filterClass)
                    .whereEqualTo("Background", filterBackground)
                    .whereEqualTo("Race", filterRace)
                    .whereEqualTo("Public", true)


                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                                for (int i = 0; i < myListOfDocuments.size(); i++) {
                                    String charName;
                                    String charID = myListOfDocuments.get(i).getId();
                                    String email = myListOfDocuments.get(i).get("Creator").toString();
                                    if(email.equals(uid)){
                                        charName = myListOfDocuments.get(i).get("Name").toString() + "***";
                                    }else {
                                        charName = myListOfDocuments.get(i).get("Name").toString();
                                    }
                                    characterList.add(new character(charID, charName));
                                    adapter.notifyDataSetChanged();

                                }
                            }
                        }

                    });
        }

        if (filterRace .equals("All") == true && filterClass.equals("All") == true && filterBackground.equals("All") == true) {
            //bool for ignore filter?
            db.collection("character")
                    .whereEqualTo("Public", true)

                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                                for (int i = 0; i < myListOfDocuments.size(); i++) {
                                    String charName;
                                    String charID = myListOfDocuments.get(i).getId();
                                    String email = myListOfDocuments.get(i).get("Creator").toString();
                                    if(email.equals(uid)){
                                        charName = myListOfDocuments.get(i).get("Name").toString() + "***";
                                    }else {
                                        charName = myListOfDocuments.get(i).get("Name").toString();
                                    }
                                    characterList.add(new character(charID, charName));
                                    adapter.notifyDataSetChanged();

                                }
                            }
                        }

                    });
        }

        if (filterRace .equals("All") == true && filterClass.equals("All") == false && filterBackground.equals("All") == true) {
            //bool for ignore filter?
            db.collection("character")
                    .whereEqualTo("Class", filterClass)
                    .whereEqualTo("Public", true)

                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                                for (int i = 0; i < myListOfDocuments.size(); i++) {
                                    String charName;
                                    String charID = myListOfDocuments.get(i).getId();
                                    String email = myListOfDocuments.get(i).get("Creator").toString();
                                    if(email.equals(uid)){
                                        charName = myListOfDocuments.get(i).get("Name").toString() + "***";
                                    }else {
                                        charName = myListOfDocuments.get(i).get("Name").toString();
                                    }
                                    characterList.add(new character(charID, charName));
                                    adapter.notifyDataSetChanged();

                                }
                            }
                        }

                    });
        }

        if (filterRace .equals("All") == true && filterClass.equals("All") == true && filterBackground.equals("All") == false) {
            //bool for ignore filter?
            db.collection("character")
                    .whereEqualTo("Background", filterBackground)
                    .whereEqualTo("Public", true)

                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                                for (int i = 0; i < myListOfDocuments.size(); i++) {
                                    String charName;
                                    String charID = myListOfDocuments.get(i).getId();
                                    String email = myListOfDocuments.get(i).get("Creator").toString();
                                    if(email.equals(uid)){
                                        charName = myListOfDocuments.get(i).get("Name").toString() + "***";
                                    }else {
                                        charName = myListOfDocuments.get(i).get("Name").toString();
                                    }
                                    characterList.add(new character(charID, charName));
                                    adapter.notifyDataSetChanged();

                                }
                            }
                        }

                    });
        }

        if (filterRace .equals("All") == false && filterClass.equals("All") == true && filterBackground.equals("All") == true) {
            //bool for ignore filter?
            db.collection("character")
                    .whereEqualTo("Race", filterRace)
                    .whereEqualTo("Public", true)

                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                                for (int i = 0; i < myListOfDocuments.size(); i++) {
                                    String charName;
                                    String charID = myListOfDocuments.get(i).getId();
                                    String email = myListOfDocuments.get(i).get("Creator").toString();
                                    if(email.equals(uid)){
                                        charName = myListOfDocuments.get(i).get("Name").toString() + "***";
                                    }else {
                                        charName = myListOfDocuments.get(i).get("Name").toString();
                                    }
                                    characterList.add(new character(charID, charName));
                                    adapter.notifyDataSetChanged();

                                }
                            }
                        }

                    });
        }

        if (filterRace .equals("All") == false && filterClass.equals("All") == false && filterBackground.equals("All") == true) {
            //bool for ignore filter?
            db.collection("character")
                    .whereEqualTo("Race", filterRace)
                    .whereEqualTo("Class", filterClass)
                    .whereEqualTo("Public", true)

                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                                for (int i = 0; i < myListOfDocuments.size(); i++) {
                                    String charName;
                                    String charID = myListOfDocuments.get(i).getId();
                                    String email = myListOfDocuments.get(i).get("Creator").toString();
                                    if(email.equals(uid)){
                                        charName = myListOfDocuments.get(i).get("Name").toString() + "***";
                                    }else {
                                        charName = myListOfDocuments.get(i).get("Name").toString();
                                    }
                                    characterList.add(new character(charID, charName));
                                    adapter.notifyDataSetChanged();

                                }
                            }
                        }

                    });
        }

        if (filterRace .equals("All") == false && filterClass.equals("All") == true && filterBackground.equals("All") == false) {
            //bool for ignore filter?
            db.collection("character")
                    .whereEqualTo("Race", filterRace)
                    .whereEqualTo("Background", filterBackground)
                    .whereEqualTo("Public", true)


                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                                for (int i = 0; i < myListOfDocuments.size(); i++) {
                                    String charName;
                                    String charID = myListOfDocuments.get(i).getId();
                                    String email = myListOfDocuments.get(i).get("Creator").toString();
                                    if(email.equals(uid)){
                                        charName = myListOfDocuments.get(i).get("Name").toString() + "***";
                                    }else {
                                        charName = myListOfDocuments.get(i).get("Name").toString();
                                    }
                                    characterList.add(new character(charID, charName));
                                    adapter.notifyDataSetChanged();

                                }
                            }
                        }

                    });
        }

        if (filterRace .equals("All") == true && filterClass.equals("All") == false && filterBackground.equals("All") == false) {
            //bool for ignore filter?
            db.collection("character")
                    .whereEqualTo("Class", filterClass)
                    .whereEqualTo("Background", filterBackground)
                    .whereEqualTo("Public", true)


                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                                for (int i = 0; i < myListOfDocuments.size(); i++) {
                                    String charName;
                                    String charID = myListOfDocuments.get(i).getId();
                                    String email = myListOfDocuments.get(i).get("Creator").toString();
                                    if(email.equals(uid)){
                                        charName = myListOfDocuments.get(i).get("Name").toString() + "***";
                                    }else {
                                        charName = myListOfDocuments.get(i).get("Name").toString();
                                    }
                                    characterList.add(new character(charID, charName));
                                    adapter.notifyDataSetChanged();

                                }
                            }
                        }

                    });
        }










    }

    public void ClearFilters(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        characterList.clear();
        adapter.notifyDataSetChanged();
        db.collection("character")
                .whereEqualTo("Public", true)

                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            Toast.makeText(getApplicationContext(), "Query Made", Toast.LENGTH_LONG).show();
                            for (int i = 0; i < myListOfDocuments.size(); i++) {
                                String charName;
                                String charID = myListOfDocuments.get(i).getId();
                                String email = myListOfDocuments.get(i).get("Creator").toString();
                                if(email.equals(uid)){
                                    charName = myListOfDocuments.get(i).get("Name").toString() + "***";
                                }else {
                                    charName = myListOfDocuments.get(i).get("Name").toString();
                                }
                                characterList.add(new character(charID, charName));
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    public void CreatePDF() {
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

        Toast.makeText(SearchCharacterActivity.this, "Created pdf", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(SearchCharacterActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                            }
                            CreatePDF();
                            viewPDF();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SearchCharacterActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }

    public void copyCharacter(int position){
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
                                Toast.makeText(SearchCharacterActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
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
                        myStatsIntent.putExtra("Edit", 2);
                        myStatsIntent.putExtra("Id", charDocName);
                        myStatsIntent.putExtra("Name", name);
                        startActivity(myStatsIntent);
                    }
                });

    }
}