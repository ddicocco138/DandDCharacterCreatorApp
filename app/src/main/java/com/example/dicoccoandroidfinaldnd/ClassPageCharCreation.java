package com.example.dicoccoandroidfinaldnd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ClassPageCharCreation extends AppCompatActivity {

    private Button nextButton;
    private RadioGroup rg1;
    private RadioGroup rg2;
    private Bundle charRaceBun;
    private String charRaceString;

    private RadioGroup.OnCheckedChangeListener listener1 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                rg2.setOnCheckedChangeListener(null); // remove the listener before clearing so we don't throw that stackoverflow exception(like Vladimir Volodin pointed out)
                rg2.clearCheck(); // clear the second RadioGroup!
                rg2.setOnCheckedChangeListener(listener2); //reset the listener
                Log.e("XXX2", "do the work");
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener listener2 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                rg1.setOnCheckedChangeListener(null);
                rg1.clearCheck();
                rg1.setOnCheckedChangeListener(listener1);
                Log.e("XXX2", "do the work");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_page_char_creation);
        Intent myClassIntent = new Intent (ClassPageCharCreation.this, BackgroundCharCreation.class);
        charRaceBun = getIntent().getExtras();
        charRaceString = charRaceBun.getString("Race");

        RadioButton bardRadio = findViewById(R.id.bardRadio);
        RadioButton barbarianRadio = findViewById(R.id.barbarianRadio);
        RadioButton clericRadio = findViewById(R.id.clericRadio);
        RadioButton druidRadio = findViewById(R.id.druidRadio);
        RadioButton fighterRadio = findViewById(R.id.fighterRadio);
        RadioButton paladinRadio = findViewById(R.id.paladinRadio);
        RadioButton rangerRadio = findViewById(R.id.rangerRadio);
        RadioButton rougeRadio = findViewById(R.id.rougeRadio);
        RadioButton sorcererRadio = findViewById(R.id.sorcererRadio);
        RadioButton warlockRadio = findViewById(R.id.warlockRadio);
        RadioButton wizardRadio = findViewById(R.id.wizardRadio);



        nextButton = findViewById(R.id.nextButton);
        rg1 = findViewById(R.id.raceRadioGroup);
        rg2 = findViewById(R.id.raceRadioGroup2);
        rg1.clearCheck(); // this is so we can start fresh, with no selection on both RadioGroups
        rg2.clearCheck();
        rg1.setOnCheckedChangeListener(listener1);
        rg2.setOnCheckedChangeListener(listener2);


        nextButton = findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = true;
                if(bardRadio.isChecked()){
                    myClassIntent.putExtra("Class", "Bard");
                }
                else if(barbarianRadio.isChecked()){
                    myClassIntent.putExtra("Class", "Barbarian");
                }
                else if(clericRadio.isChecked()){
                    myClassIntent.putExtra("Class", "Cleric");
                }
                else if(druidRadio.isChecked()){
                    myClassIntent.putExtra("Class", "Druid");
                }
                else if(fighterRadio.isChecked()){
                    myClassIntent.putExtra("Class", "Fighter");
                }
                else if(paladinRadio.isChecked()){
                    myClassIntent.putExtra("Class", "Paladin");
                }
                else if(rangerRadio.isChecked()){
                    myClassIntent.putExtra("Class", "Ranger");
                }
                else if(rougeRadio.isChecked()){
                    myClassIntent.putExtra("Class", "Rogue");
                }
                else if(sorcererRadio.isChecked()){
                    myClassIntent.putExtra("Class", "Sorcerer");
                }
                else if(warlockRadio.isChecked()){
                    myClassIntent.putExtra("Class", "Warlock");
                }
                else if(wizardRadio.isChecked()){
                    myClassIntent.putExtra("Class", "Wizard");
                }
                else{
                    Toast.makeText(ClassPageCharCreation.this, "Please Select a Class",
                            Toast.LENGTH_SHORT).show();
                    selected = false;
                }

                if(selected) {
                    myClassIntent.putExtra("Race", charRaceString);
                    startActivity(myClassIntent);
                }

            }
        });


    }
}