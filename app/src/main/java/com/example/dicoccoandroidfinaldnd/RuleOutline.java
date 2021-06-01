package com.example.dicoccoandroidfinaldnd;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RuleOutline {
    private String rText;

    public RuleOutline(String text){
        rText = text;
    }

    public String getrText() {
        return rText;
    }
}
