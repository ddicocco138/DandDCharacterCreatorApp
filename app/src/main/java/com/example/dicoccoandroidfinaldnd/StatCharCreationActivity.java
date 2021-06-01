package com.example.dicoccoandroidfinaldnd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StatCharCreationActivity extends AppCompatActivity {

    private TextView strStat, dexStat, conStat, intStat, wisStat, chrStat, remainingTot;
    private Button strPlus, dexPlus, conPlus, intPlus, wisPlus, chrPlus, strMinus, dexMinus, conMinus, intMinus, wisMinus, chrMinus, nextButton;
    private Bundle charBun;
    private String charRaceString, charClassString, charBackgroundString, charWeaponString, charID, charName;
    private int novice, edit;
    private int str = 8, dex = 8, con = 8, intel = 8, wis = 8, chr = 8, remaining = 27;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_char_creation);
        Intent myStatsIntent = new Intent (StatCharCreationActivity.this, CharacterReview.class);
        charBun = getIntent().getExtras();
        charRaceString = charBun.getString("Race");
        charClassString = charBun.getString("Class");
        charBackgroundString = charBun.getString("Background");
        charWeaponString = charBun.getString("Weapon");

        remainingTot = findViewById(R.id.RemainingTxt);
        nextButton = findViewById(R.id.nextButtonStats);

        strStat = findViewById(R.id.StrStat);
        strMinus = findViewById(R.id.btnStrMinus);
        strPlus = findViewById(R.id.btnStrPlus);

        dexStat = findViewById(R.id.DexStat);
        dexMinus = findViewById(R.id.btnDexMinus);
        dexPlus = findViewById(R.id.btnDexPlus);

        conStat = findViewById(R.id.ConStat);
        conMinus = findViewById(R.id.btnConMinus);
        conPlus = findViewById(R.id.btnConPlus);

        intStat = findViewById(R.id.IntStat);
        intMinus = findViewById(R.id.btnIntMinus);
        intPlus = findViewById(R.id.btnIntPlus);

        wisStat = findViewById(R.id.WisStat);
        wisMinus = findViewById(R.id.btnWisMinus);
        wisPlus = findViewById(R.id.btnWisPlus);

        chrStat = findViewById(R.id.ChrStat);
        chrMinus = findViewById(R.id.btnChrMinus);
        chrPlus = findViewById(R.id.btnChrPlus);

        novice = charBun.getInt("Novice");
        if(novice == 1){
            PresetCharStats();
        }

        edit = charBun.getInt("Edit");
        if(edit == 1){
            charName = charBun.getString("Name");
            charID = charBun.getString("Id");
            remainingTot.setVisibility(View.GONE);
            findViewById(R.id.textViewRemaing).setVisibility(View.GONE);
            EditChar();
        }else if(edit == 2){
            charName = charBun.getString("Name");
            charID = charBun.getString("Id");
            EditChar();
        }



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myStatsIntent.putExtra("Race", charRaceString);
                myStatsIntent.putExtra("Class", charClassString);
                myStatsIntent.putExtra("Background", charBackgroundString);
                myStatsIntent.putExtra("Weapon", charWeaponString);
                myStatsIntent.putExtra("Str", str);
                myStatsIntent.putExtra("Dex", dex);
                myStatsIntent.putExtra("Con", con);
                myStatsIntent.putExtra("Int", intel);
                myStatsIntent.putExtra("Wis", wis);
                myStatsIntent.putExtra("Chr", chr);
                myStatsIntent.putExtra("Id", charID);
                myStatsIntent.putExtra("Edit", edit);
                myStatsIntent.putExtra("Name", charName);
                startActivity(myStatsIntent);
            }
        });
    }

    /* Function to say that the user is out of point to be used when allocating it to the character stats*/
    public void noPoints() {
        Toast.makeText(StatCharCreationActivity.this, "Out of Points!",
                Toast.LENGTH_SHORT).show();
        return;
    }

    public void minusStr(View view) {
        if(str <= 8){
            Toast.makeText(StatCharCreationActivity.this, "Stat cannot be below 8",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        str--;
        remaining++;
        strStat.setText(String.valueOf(str));
        remainingTot.setText(String.valueOf(remaining));
    }

    public void minusDex(View view) {
        if(dex <= 8){
            Toast.makeText(StatCharCreationActivity.this, "Stat cannot be below 8",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        dex--;
        remaining++;
        dexStat.setText(String.valueOf(dex));
        remainingTot.setText(String.valueOf(remaining));
    }

    public void minusCon(View view) {
        if(con <= 8){
            Toast.makeText(StatCharCreationActivity.this, "Stat cannot be below 8",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        con--;
        remaining++;
        conStat.setText(String.valueOf(con));
        remainingTot.setText(String.valueOf(remaining));
    }

    public void minusInt(View view) {
        if(intel <= 8){
            Toast.makeText(StatCharCreationActivity.this, "Stat cannot be below 8",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        intel--;
        remaining++;
        intStat.setText(String.valueOf(intel));
        remainingTot.setText(String.valueOf(remaining));
    }

    public void minusWis(View view) {
        if(wis <= 8){
            Toast.makeText(StatCharCreationActivity.this, "Stat cannot be below 8",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        wis--;
        remaining++;
        wisStat.setText(String.valueOf(wis));
        remainingTot.setText(String.valueOf(remaining));
    }

    public void minusChr(View view) {
        if(chr <= 8){
            Toast.makeText(StatCharCreationActivity.this, "Stat cannot be below 8",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        chr--;
        remaining++;
        chrStat.setText(String.valueOf(chr));
        remainingTot.setText(String.valueOf(remaining));
    }

    public void plusStr(View view) {
        if (edit == 1){
            if(str >= 20){
                Toast.makeText(StatCharCreationActivity.this, "Stat cannot be above 20",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }else {
            if (str >= 18) {
                Toast.makeText(StatCharCreationActivity.this, "Stat cannot be above 18",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (remaining <= 0) {
                noPoints();
                return;
            }
        }
        str++;
        remaining--;
        strStat.setText(String.valueOf(str));
        remainingTot.setText(String.valueOf(remaining));
    }

    public void PlusDex(View view) {
        if(edit == 1){
            if(dex >= 20){
                Toast.makeText(StatCharCreationActivity.this, "Stat cannot be above 20",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }else {
            if (dex >= 18) {
                Toast.makeText(StatCharCreationActivity.this, "Stat cannot be above 18",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (remaining <= 0) {
                noPoints();
                return;
            }
        }
        dex++;
        remaining--;
        dexStat.setText(String.valueOf(dex));
        remainingTot.setText(String.valueOf(remaining));
    }

    public void PlusCon(View view) {
        if (edit == 1){
            if(con >= 20){
                Toast.makeText(StatCharCreationActivity.this, "Stat cannot be above 20",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }else {
            if (con >= 18) {
                Toast.makeText(StatCharCreationActivity.this, "Stat cannot be above 18",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (remaining <= 0) {
                noPoints();
                return;
            }
        }
        con++;
        remaining--;
        conStat.setText(String.valueOf(con));
        remainingTot.setText(String.valueOf(remaining));
    }

    public void PlusInt(View view) {
        if (edit == 1){
            if(intel >= 20){
                Toast.makeText(StatCharCreationActivity.this, "Stat cannot be above 20",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }else {
            if (intel >= 18) {
                Toast.makeText(StatCharCreationActivity.this, "Stat cannot be above 18",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (remaining <= 0) {
                noPoints();
                return;
            }
        }
        intel++;
        remaining--;
        intStat.setText(String.valueOf(intel));
        remainingTot.setText(String.valueOf(remaining));
    }

    public void PLusWis(View view) {
        if (edit == 1){
            if(wis >= 20){
                Toast.makeText(StatCharCreationActivity.this, "Stat cannot be above 20",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }else {
            if (wis >= 18) {
                Toast.makeText(StatCharCreationActivity.this, "Stat cannot be above 18",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (remaining <= 0) {
                noPoints();
                return;
            }
        }
        wis++;
        remaining--;
        wisStat.setText(String.valueOf(wis));
        remainingTot.setText(String.valueOf(remaining));
    }

    public void PlusChr(View view) {
        if (edit == 1){
            if(chr >= 20){
                Toast.makeText(StatCharCreationActivity.this, "Stat cannot be above 20",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }else {
            if (chr >= 18) {
                Toast.makeText(StatCharCreationActivity.this, "Stat cannot be above 18",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (remaining <= 0) {
                noPoints();
                return;
            }
        }
        chr++;
        remaining--;
        chrStat.setText(String.valueOf(chr));
        remainingTot.setText(String.valueOf(remaining));
    }

    void EditChar(){
        str = charBun.getInt("Str");
        con = charBun.getInt("Con");
        intel = charBun.getInt("Int");
        wis = charBun.getInt("Wis");
        chr = charBun.getInt("Chr");
        dex = charBun.getInt("Dex");
        remaining = 0;
        conStat.setText(String.valueOf(con));
        dexStat.setText(String.valueOf(dex));
        wisStat.setText(String.valueOf(wis));
        chrStat.setText(String.valueOf(chr));
        strStat.setText(String.valueOf(str));
        intStat.setText(String.valueOf(intel));
        remainingTot.setText(String.valueOf(remaining));

    }

    void PresetCharStats(){
        if(charClassString.equals("Barbarian")){
            str = 18;
            strStat.setText(String.valueOf(str));
            con = 14;
            conStat.setText(String.valueOf(con));
            dex = 12;
            dexStat.setText(String.valueOf(dex));
            wis = 11;
            wisStat.setText(String.valueOf(wis));
            chr = 10;
            chrStat.setText(String.valueOf(chr));
            intel = 10;
            intStat.setText(String.valueOf(intel));
            remaining = 0;
            remainingTot.setText(String.valueOf(remaining));
        }else if(charClassString.equals("Fighter")){
            str = 18;
            con = 14;
            intel = 12;
            wis = 11;
            chr = 10;
            dex = 10;
            remaining = 0;
            conStat.setText(String.valueOf(con));
            dexStat.setText(String.valueOf(dex));
            wisStat.setText(String.valueOf(wis));
            chrStat.setText(String.valueOf(chr));
            strStat.setText(String.valueOf(str));
            intStat.setText(String.valueOf(intel));
            remainingTot.setText(String.valueOf(remaining));
        }else if(charClassString.equals("Monk")){
            dex = 18;
            wis = 14;
            con = 12;
            str = 11;
            intel = 10;
            chr = 10;
            remaining = 0;
            conStat.setText(String.valueOf(con));
            dexStat.setText(String.valueOf(dex));
            wisStat.setText(String.valueOf(wis));
            chrStat.setText(String.valueOf(chr));
            strStat.setText(String.valueOf(str));
            intStat.setText(String.valueOf(intel));
            remainingTot.setText(String.valueOf(remaining));
        }else if(charClassString.equals("Rogue")){
            dex = 18;
            con = 14;
            chr = 12;
            intel = 11;
            wis = 10;
            str = 10;
            remaining = 0;
            conStat.setText(String.valueOf(con));
            dexStat.setText(String.valueOf(dex));
            wisStat.setText(String.valueOf(wis));
            chrStat.setText(String.valueOf(chr));
            strStat.setText(String.valueOf(str));
            intStat.setText(String.valueOf(intel));
            remainingTot.setText(String.valueOf(remaining));
        }else if(charClassString.equals("Paladin")){
            str = 18;
            chr = 14;
            con = 12;
            dex = 11;
            wis = 10;
            intel = 10;
            remaining = 0;
            conStat.setText(String.valueOf(con));
            dexStat.setText(String.valueOf(dex));
            wisStat.setText(String.valueOf(wis));
            chrStat.setText(String.valueOf(chr));
            strStat.setText(String.valueOf(str));
            intStat.setText(String.valueOf(intel));
            remainingTot.setText(String.valueOf(remaining));
        }else if(charClassString.equals("Ranger")) {
            str=10;
            con=12;
            dex=18;
            intel=11;
            wis=14;
            chr=10;
            remaining = 0;
            conStat.setText(String.valueOf(con));
            dexStat.setText(String.valueOf(dex));
            wisStat.setText(String.valueOf(wis));
            chrStat.setText(String.valueOf(chr));
            strStat.setText(String.valueOf(str));
            intStat.setText(String.valueOf(intel));
            remainingTot.setText(String.valueOf(remaining));
        } else if(charClassString.equals("Sorcerer")) {
            str=10;
            con=14;
            dex=12;
            intel=11;
            wis=10;
            chr=18;
            remaining = 0;
            conStat.setText(String.valueOf(con));
            dexStat.setText(String.valueOf(dex));
            wisStat.setText(String.valueOf(wis));
            chrStat.setText(String.valueOf(chr));
            strStat.setText(String.valueOf(str));
            intStat.setText(String.valueOf(intel));
            remainingTot.setText(String.valueOf(remaining));
        } else if(charClassString.equals("Warlock")) {
            str=10;
            con=12;
            dex=14;
            intel=11;
            wis=10;
            chr=18;
            remaining = 0;
            conStat.setText(String.valueOf(con));
            dexStat.setText(String.valueOf(dex));
            wisStat.setText(String.valueOf(wis));
            chrStat.setText(String.valueOf(chr));
            strStat.setText(String.valueOf(str));
            intStat.setText(String.valueOf(intel));
            remainingTot.setText(String.valueOf(remaining));
        } else if(charClassString.equals("Wizard")) {
            str=10;
            con=14;
            dex=12;
            intel=18;
            wis=11;
            chr=10;
            remaining = 0;
            conStat.setText(String.valueOf(con));
            dexStat.setText(String.valueOf(dex));
            wisStat.setText(String.valueOf(wis));
            chrStat.setText(String.valueOf(chr));
            strStat.setText(String.valueOf(str));
            intStat.setText(String.valueOf(intel));
            remainingTot.setText(String.valueOf(remaining));
        } else if(charClassString.equals("Bard")) {
            str=10;
            con=12;
            dex=14;
            intel=11;
            wis=10;
            chr=18;
            remaining = 0;
            conStat.setText(String.valueOf(con));
            dexStat.setText(String.valueOf(dex));
            wisStat.setText(String.valueOf(wis));
            chrStat.setText(String.valueOf(chr));
            strStat.setText(String.valueOf(str));
            intStat.setText(String.valueOf(intel));
            remainingTot.setText(String.valueOf(remaining));
        } else if(charClassString.equals("Cleric")) {
            str=11;
            con=14;
            dex=12;
            intel=10;
            wis=18;
            chr=10;
            remaining = 0;
            conStat.setText(String.valueOf(con));
            dexStat.setText(String.valueOf(dex));
            wisStat.setText(String.valueOf(wis));
            chrStat.setText(String.valueOf(chr));
            strStat.setText(String.valueOf(str));
            intStat.setText(String.valueOf(intel));
            remainingTot.setText(String.valueOf(remaining));
        } else if(charClassString.equals("Druid")) {
            str= 10;
            con= 14;
            dex= 12;
            intel= 11;
            wis= 18;
            chr= 10;
            remaining = 0;
            conStat.setText(String.valueOf(con));
            dexStat.setText(String.valueOf(dex));
            wisStat.setText(String.valueOf(wis));
            chrStat.setText(String.valueOf(chr));
            strStat.setText(String.valueOf(str));
            intStat.setText(String.valueOf(intel));
            remainingTot.setText(String.valueOf(remaining));
        }

    }
}