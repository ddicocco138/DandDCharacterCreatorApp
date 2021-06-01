package com.example.dicoccoandroidfinaldnd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class BackgroundCharCreation extends AppCompatActivity {

    private Button nextButton;
    private Bundle charBun;
    private String charRaceString, charClassString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_char_creation);
        Intent myBackgroundIntent = new Intent (BackgroundCharCreation.this, WeaponCharCreation.class);
        charBun = getIntent().getExtras();
        charRaceString = charBun.getString("Race");
        charClassString = charBun.getString("Class");

        nextButton = findViewById(R.id.nextButton);

        RadioButton acolyteRadio = findViewById(R.id.clubRadio);
        RadioButton criminalRadio = findViewById(R.id.greatclubRadio);
        RadioButton folkRadio = findViewById(R.id.daggerRadio);
        RadioButton nobleRadio = findViewById(R.id.nobleRadio);
        RadioButton sageRadio = findViewById(R.id.javelinRadio);
        RadioButton soldierRadio = findViewById(R.id.lighthammerRadio);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = true;
                if(acolyteRadio.isChecked()){
                    myBackgroundIntent.putExtra("Background", "Acolyte");
                }
                else if(criminalRadio.isChecked()){
                    myBackgroundIntent.putExtra("Background", "Criminal");
                }
                else if(folkRadio.isChecked()){
                    myBackgroundIntent.putExtra("Background", "Folk");
                }
                else if(nobleRadio.isChecked()){
                    myBackgroundIntent.putExtra("Background", "Noble");
                }
                else if(sageRadio.isChecked()){
                    myBackgroundIntent.putExtra("Background", "Sage");
                }
                else if(soldierRadio.isChecked()){
                    myBackgroundIntent.putExtra("Background", "Soldier");
                }
                else{
                    Toast.makeText(BackgroundCharCreation.this, "Please Select a Background",
                            Toast.LENGTH_SHORT).show();
                    selected = false;
                }

                if(selected) {
                    myBackgroundIntent.putExtra("Race", charRaceString);
                    myBackgroundIntent.putExtra("Class", charClassString);
                    startActivity(myBackgroundIntent);
                }

            }
        });


    }
}