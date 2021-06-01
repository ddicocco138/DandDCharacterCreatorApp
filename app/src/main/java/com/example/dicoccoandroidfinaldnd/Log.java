package com.example.dicoccoandroidfinaldnd;

public class Log {

    private String charID;
    private String Name;
    private String Items;
    private String StatBonus;
    private String Comments;
    private String Date;


    public String getCharID() {
        return charID;
    }
    public String getName() {return Name;}
    public String getItems() {return Items;}
    public String getStatBonus() {return StatBonus;}
    public String getComments() {return Comments;}
    public String getDate() {return Date;}


    public Log(String charID, String Date, String Items, String StatBonus, String Comments)
    {
        this.charID = charID;
        this.Date = Date;
        this.Items = Items;
        this.StatBonus = StatBonus;
        this.Comments = Comments;


    }

    public void setCharID(String charID) {
        this.charID = charID;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public void setItems(String Items) {
        this.Items = Items;
    }
    public void setStatBonus(String bonus) {
        this.StatBonus = bonus;
    }
    public void setComments(String com) {
        this.Comments = com;
    }
    public void setDate(String date) {
        this.Date = date;
    }
}
