package com.example.dicoccoandroidfinaldnd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;


import java.util.HashMap;
import java.util.Map;



public class CharacterReview extends AppCompatActivity {

    private Bundle charBun;
    private String charRaceString, charClassString, charBackgroundString, charWeaponString, charName, charID;
    private int str, dex, con, intel, wis, chr;
    private TextView userRace, userClass, userBackgroud, userWeapon, userStr, userDex, userCon, userInt, userWis, userChr;
    private int edit;
    private Button confirmButton;
    private FirebaseAuth mAuth;
    private EditText characterName;
    private RadioButton publicButton;
    private Boolean publicCharacter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_review);

        Intent myReviewIntent = new Intent (CharacterReview.this, MainMenu.class);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        characterName = findViewById(R.id.editTextCharName);
        charBun = getIntent().getExtras();
        charRaceString = charBun.getString("Race");
        charClassString = charBun.getString("Class");
        charBackgroundString = charBun.getString("Background");
        charWeaponString = charBun.getString("Weapon");
        str = charBun.getInt("Str");
        dex = charBun.getInt("Dex");
        con = charBun.getInt("Con");
        intel = charBun.getInt("Int");
        wis = charBun.getInt("Wis");
        chr = charBun.getInt("Chr");
        edit = charBun.getInt("Edit");
        if (edit == 1){
            charName = charBun.getString("Name");
            charID = charBun.getString("Id");
            characterName.setText(charName);
        }

        publicButton = findViewById(R.id.radioButtonPrivate);

        confirmButton=findViewById(R.id.btnConfirm);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getEmail();










        userRace = findViewById(R.id.textViewUserRace);
        userClass = findViewById(R.id.textViewUserClass);
        userBackgroud = findViewById(R.id.textViewUserBackground);
        userWeapon = findViewById(R.id.textViewUserWeapon);
        userStr = findViewById(R.id.textViewUserStr);
        userDex = findViewById(R.id.textViewUserDex);
        userCon = findViewById(R.id.textViewUserCon);
        userInt = findViewById(R.id.textViewUserInt);
        userWis = findViewById(R.id.textViewUserWis);
        userChr = findViewById(R.id.textViewUserChr);

        userRace.setText(charRaceString);
        userClass.setText(charClassString);
        userBackgroud.setText(charBackgroundString);
        userWeapon.setText(charWeaponString);
        userStr.setText(String.valueOf(str));
        userDex.setText(String.valueOf(dex));
        userCon.setText(String.valueOf(con));
        userInt.setText(String.valueOf(intel));
        userWis.setText(String.valueOf(wis));
        userChr.setText(String.valueOf(chr));





        confirmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String CharacterName = characterName.getText().toString();

                Map<String, Object> character = new HashMap<>();
                character.put("Background", charBackgroundString);
                character.put("CHR", chr);
                character.put("Class", charClassString);
                character.put("DEX", dex);
                character.put("INT", intel);
                character.put("Race", charRaceString);
                character.put("STR", str);
                character.put("CON", con);
                character.put("WIS", wis);
                character.put("Weapon", charWeaponString);
                character.put("Name", CharacterName);
                character.put("Creator", uid);
                if (publicButton.isChecked()) {
                    publicCharacter = true;
                }else {
                    publicCharacter = false;
                }

                if (publicCharacter == true)
                    character.put("Public", true);
                else
                    character.put("Public", false);


// Add a new document with a generated ID

                if(edit == 1){
                    db.collection("character").document(charID).update("Background", charBackgroundString);
                    db.collection("character").document(charID).update("CHR", chr);
                    db.collection("character").document(charID).update("Class", charClassString);
                    db.collection("character").document(charID).update("DEX", dex);
                    db.collection("character").document(charID).update("INT", intel);
                    db.collection("character").document(charID).update("Race", charRaceString);
                    db.collection("character").document(charID).update("STR", str);
                    db.collection("character").document(charID).update("CON", con);
                    db.collection("character").document(charID).update("WIS", wis);
                    db.collection("character").document(charID).update("Weapon", charWeaponString);
                    db.collection("character").document(charID).update("Name", CharacterName);
                    Toast.makeText(getApplicationContext(),"Character: " + charName + " successfully updated",Toast.LENGTH_LONG).show();
                    startActivity(myReviewIntent);
                }else {
                    db.collection("character")
                            .add(character)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getApplicationContext(), "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show();
                                    startActivity(myReviewIntent);

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error adding document", Toast.LENGTH_LONG).show();
                                }
                            });
                }

            }
        });




    }
}