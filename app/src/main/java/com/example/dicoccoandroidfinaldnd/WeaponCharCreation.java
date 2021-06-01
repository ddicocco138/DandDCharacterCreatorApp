package com.example.dicoccoandroidfinaldnd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class WeaponCharCreation extends AppCompatActivity {

    private Button nextButton;
    private Bundle charBun;
    private String charRaceString, charClassString, charBackgroundString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon_char_creation);
        Intent myWeaponIntent = new Intent (WeaponCharCreation.this, StatCharCreationActivity.class);
        charBun = getIntent().getExtras();
        charRaceString = charBun.getString("Race");
        charClassString = charBun.getString("Class");
        charBackgroundString = charBun.getString("Background");

        nextButton = findViewById(R.id.nextButton);

        RadioButton clubRadio = findViewById(R.id.clubRadio);
        RadioButton daggerRadio = findViewById(R.id.daggerRadio);
        RadioButton greatClubRadio = findViewById(R.id.greatclubRadio);
        RadioButton handAxeRadio = findViewById(R.id.handaxeRadio);
        RadioButton javelinRadio = findViewById(R.id.javelinRadio);
        RadioButton lightHammerRadio = findViewById(R.id.lighthammerRadio);
        RadioButton maceRadio = findViewById(R.id.maceRadio);
        RadioButton quarterStaffRadio = findViewById(R.id.quaterstaffRadio);
        RadioButton sickleRadio = findViewById(R.id.sickleRadio);
        RadioButton spearRadio = findViewById(R.id.spearRadio);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean selected = true;
                if(clubRadio.isChecked()){
                    myWeaponIntent.putExtra("Weapon", "Club");
                }
                else if(daggerRadio.isChecked()){
                    myWeaponIntent.putExtra("Weapon", "Dagger");
                }
                else if(greatClubRadio.isChecked()){
                    myWeaponIntent.putExtra("Weapon", "GreatClub");
                }
                else if(handAxeRadio.isChecked()){
                    myWeaponIntent.putExtra("Weapon", "HandAxe");
                }
                else if(javelinRadio.isChecked()){
                    myWeaponIntent.putExtra("Weapon", "Javelin");
                }
                else if(lightHammerRadio.isChecked()){
                    myWeaponIntent.putExtra("Weapon", "LightHammer");
                }
                else if(maceRadio.isChecked()){
                    myWeaponIntent.putExtra("Weapon", "Mace");
                }
                else if(quarterStaffRadio.isChecked()){
                    myWeaponIntent.putExtra("Weapon", "QuarterStaff");
                }
                else if(sickleRadio.isChecked()){
                    myWeaponIntent.putExtra("Weapon", "Sickle");
                }
                else if(spearRadio.isChecked()){
                    myWeaponIntent.putExtra("Weapon", "Spear");
                }
                else{
                    Toast.makeText(WeaponCharCreation.this, "Please Select a Weapon",
                            Toast.LENGTH_SHORT).show();
                    selected = false;
                }

                if(selected) {
                    myWeaponIntent.putExtra("Race", charRaceString);
                    myWeaponIntent.putExtra("Class", charClassString);
                    myWeaponIntent.putExtra("Background", charBackgroundString);
                    startActivity(myWeaponIntent);
                }
            }
        });
    }
}