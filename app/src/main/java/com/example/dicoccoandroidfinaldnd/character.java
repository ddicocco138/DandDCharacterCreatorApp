package com.example.dicoccoandroidfinaldnd;

public class character {

    private String characterID;
    private String name;


    public String getCharacterID() {
        return characterID;
    }
    public String getName() {return name;}

    public character(String characterID, String name)
    {
        this.characterID = characterID;
        this.name = name;

    }

    public void setCharacterID(String characterID) {
        this.characterID = characterID;
    }
    public void setName(String name) {
        this.name = name;
    }


}
