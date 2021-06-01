package com.example.dicoccoandroidfinaldnd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }




    public void Move_CharacterCreate(View view) {
        Intent i = new Intent (this, NewCharacterActivity.class);
        startActivity(i);
    }

    public void Move_Rules(View view) {
        Intent i = new Intent (this, RulesActivity.class);
        startActivity(i);
    }

    public void Move_ViewCharacters(View view) {
        Intent i = new Intent (this, ViewCharacterActivity.class);
        startActivity(i);
    }
}