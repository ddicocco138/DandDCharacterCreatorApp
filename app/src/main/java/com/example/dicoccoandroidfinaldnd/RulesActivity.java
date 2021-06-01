package com.example.dicoccoandroidfinaldnd;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class RulesActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView, combatRecyclerView, racialRecyclerView;
    private RecyclerView.Adapter mAdapter, combatAdapter, raceAdapter;
    private RecyclerView.LayoutManager mLayoutManager, combatLayoutManager, raceLayoutManager;

    Button btn1, btn2, btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        //basic rule list
        ArrayList<RuleOutline> ruleList = new ArrayList<>();
        ruleList.add(new RuleOutline("\u2022 Rule 1: Create your character and answer the questions for" +
                " the guided character selection or if you're an advanced player use" +
                " the advanced character creation!"));
        ruleList.add(new RuleOutline("\u2022 Rule 2: Picking a class is important because it is determines the" +
                " abilities you will posses. Melee specializes in close combat, ranged attacks from afar, and" +
                " support aids allies with buffs or debuffing enemies."));
        ruleList.add(new RuleOutline("\u2022 Rule 3: Choosing a race is choosing who you are. Each race offers different \n" +
                "abilities and bonus stats."));
        ruleList.add(new RuleOutline("\u2022 Rule 4: Stats are important because depending on your class, certain " +
                "stats will make you stronger. " +
                "STR: Strength, the higher the strength the stronger the character is physically. \n" +
                "DEX: Dexterity, improves movement, accuracy, and reaction time. \n" +
                "CON: Constitution, improves their immunity to diseases and stronger defense. \n" +
                "INT: Intelligence, improves knowledge and thinking. \n" +
                "WIS: Wisdom, the higher, the easier to read people, notice situations and people better. \n" +
                "CHA: Charisma, higher charisma will make it easier to talk to people and get along."));
        ruleList.add(new RuleOutline("\u2022 Rule 5: Starting weapon is important because " +
                "that will be the weapon you wield in the beginning." +
                " Your weapon is what will be used to attack or defend" +
                " so choose wisely."));
        ruleList.add(new RuleOutline("\u2022 Rule 6: Choose your background to choose where your character came from! Their backstory " +
                "is important because that is the story of your character and how they came to be."));
        ruleList.add(new RuleOutline("\u2022 Rule 7: The dungeon master is the creator of the realm you play in and he/she determines what adventure your characters go through."));

        mRecyclerView = findViewById(R.id.theTavernRules);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RuleAdapter(ruleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //combat rule list
        ArrayList<RuleOutline> combatRuleList = new ArrayList<>();
        combatRuleList.add(new RuleOutline("Combat Rule 1: Every turn is 6 seconds long in-game time."));
        combatRuleList.add(new RuleOutline("Combat Rule 2: D&D heavily relies on dice like a 20 dice to determine outcomes. Example: I want my character to jump over a broken bridge! Low number they will fail or high number they will succeed it will all be determined by the Dungeon Master."));
        combatRuleList.add(new RuleOutline("Combat Rule 3: You have to roll a D20 to determine if you hit an opponent or not. If the roll is 1 higher than the target's armor class then you landed a hit and if you roll the same or lower than the target's armor class then you missed.\n" +
                "A critical hit is when you roll a 20."));
        combatRuleList.add(new RuleOutline("Combat Rule 4: While in combat you would always have to roll certain dice that the weapon requires and this will determine the damage." +
                " A critical hit would deal 2x damage."));
        combatRuleList.add(new RuleOutline("Combat Rule 5: A 20 dice can be rolled to determine if you can dodge a stun or restrain skill and add your stat points to the roll"));
        combatRuleList.add(new RuleOutline("Combat Example: You are fighting a an ogre with 20 Health points and an armor class of 12. You roll a D20 and result is a 20, because 20 is higher than 12 you landed a hit. \n" +
                "You then roll a D8 because your weapon damage requires a D8 and you roll a 6. \n" +
                "Since you rolled a 20 for critical hit you do 12 damage total and the ogre's health is down to 8."));
        combatRuleList.add(new RuleOutline("Combat Rule 6: Any rules above can differ or be ignored depending on your dungeon master, good luck."));

        combatRecyclerView = findViewById(R.id.theCombatRules);
        combatRecyclerView.setHasFixedSize(true);
        combatLayoutManager = new LinearLayoutManager(this);
        combatAdapter = new RuleAdapter(combatRuleList);

        combatRecyclerView.setLayoutManager(combatLayoutManager);
        combatRecyclerView.setAdapter(combatAdapter);

        //racial trait list
        ArrayList<RuleOutline> racialRuleList = new ArrayList<>();
        racialRuleList.add(new RuleOutline("Dragonborn Racial Traits \n" +
                "Ability Score Increase: Your Strength score increases by 2, and your Charisma score increases by 1.\n" +
                "Speed: You can walk a maximum of 30 ft. per turn.\n" +
                "Draconic Ancestry: You have draconic ancestry. Choose one type of dragon from the Draconic Ancestry table. Your breath weapon and damage resistance are determined by the dragon type \n" +
                "Black Dragon - Acid Damage Type, 5 by 30 ft. line \n" +
                "Blue Dragon - Lightning Damage Type, 5 ny 30 ft. line \n" +
                "Brass Dragon - Fire Damage Type, 5 by 30 ft. line \n" +
                "Bronze Dragon - Lightning Damage Type, 5 by 30ft. line \n" +
                "Copper Dragon - Acid Damage Type, 5 by 30ft. line \n" +
                "Gold Dragon - Fire Damage Type, 15 ft. cone \n" +
                "Green Dragon - Poison Damage Type, 15 ft. cone \n" +
                "Red Dragon - Fire Damage Type, 15 ft. cone \n" +
                "Silver Dragon - Cold Damage Type, 15 ft. cone \n" +
                "White Dragon - Cold Damage Type, 15 ft. cone \n" +
                "Damage Resistance: You have resistance to the damage type associated with your draconic ancestry."));
        racialRuleList.add(new RuleOutline("Dwarf Racial Traits \n" +
                "Ability Score Increase: Your Constitution score increases by 2. \n" +
                "Speed: You can walk a maximum of 25 ft. per turn. \n" +
                "Darkvision: Accustomed to life underground, you have superior vision in dark and dim conditions. You can see in dim light within 60 feet of you as if it were bright light, and in darkness as if it were dim light. You can’t discern color in darkness, only shades of gray.\n" +
                "Dwarven Resilience: You have advantage on saving throws against poison, and you have resistance against poison damage (explained in the “Combat” section)\n" +
                "Dwarven Combat Training: You have proficiency with the battleaxe, handaxe, light hammer, and warhammer.\n" +
                "Tool Proficiency: You gain proficiency with the artisan’s tools of your choice: smith’s tools, brewer’s supplies, or mason’s tools.\n"));
        racialRuleList.add(new RuleOutline("Elf Racial Traits \n" +
                "Ability Score Increase: Your Dexterity score increases by 2. \n" +
                "Speed: You can walk a maximum of 30 ft. per turn \n" +
                "Darkvision: Accustomed to twilit forests and the night sky, you have superior vision in dark and dim conditions. You can see in dim light within 60 feet of you as if it were bright light, and in darkness as if it were dim light. You can’t discern color in darkness, only shades of gray.\n" +
                "Keen Senses: You have proficiency in the Perception skill.\n" +
                "Fey Ancestry: You have advantage on saving throws against being charmed, and magic can’t put you to sleep.\n"));
        racialRuleList.add(new RuleOutline("Gnome Racial Traits \n" +
                "Ability Score Increase: Your Intelligence score increases by 2. \n" +
                "Speed: You can walk a maximum of 25 ft. per turn. \n" +
                "Darkvision: Accustomed to life underground, you have superior vision in dark and dim conditions. You can see in dim light within 60 feet of you as if it were bright light, and in darkness as if it were dim light. You can’t discern color in darkness, only shades of gray.\n" +
                "Gnome Cunning: You have advantage on all Intelligence, Wisdom, and Charisma saving throws against magic."));
        racialRuleList.add(new RuleOutline("Human Racial Traits \n" +
                "Ability Score Increase: Your ability scores each increase by 1. \n" +
                "Speed: You can walk a maximum of 30 ft. per turn."));
        racialRuleList.add(new RuleOutline("Tiefling Racial Traits \n" +
                "Ability Score Increase: Your Intelligence score increases by 1, and your Charisma score increases by 2. \n" +
                "Speed: You can walk a maximum of 30 ft. per turn. \n" +
                "Darkvision: Thanks to your infernal heritage, you have superior vision in dark and dim conditions. You can see in dim light within 60 feet of you as if it were bright light, and in darkness as if it were dim light. You can’t discern color in darkness, only shades of gray.\n" +
                "Hellish Resistance: You have resistance to fire damage.\n" +
                "Infernal Legacy: You know the thaumaturgy cantrip. When you reach 3rd level, you can cast the hellish rebuke spell as a 2nd-level spell once with this trait and regain the ability to do so when you finish a long rest. When you reach 5th level, you can cast the darkness spell once with this trait and regain the ability to do so when you finish a long rest. Charisma is your spellcasting ability for these spells."));

        racialRecyclerView = findViewById(R.id.theRaceRules);
        racialRecyclerView.setHasFixedSize(true);
        raceLayoutManager = new LinearLayoutManager(this);
        raceAdapter = new RuleAdapter(racialRuleList);

        racialRecyclerView.setLayoutManager(raceLayoutManager);
        racialRecyclerView.setAdapter(raceAdapter);

        //binding buttons
        btn1 = (Button) findViewById(R.id.basicRules);
        btn2 = (Button) findViewById(R.id.combatRules);
        btn3 = (Button) findViewById(R.id.raceTraits);



    }

    //Code to hide and unhide Tavern Rule content
    public void hide(View view) {
        if(mRecyclerView.getVisibility() == View.GONE) {
            if(combatRecyclerView.getVisibility() == View.VISIBLE || racialRecyclerView.getVisibility() == View.VISIBLE)
            {
                combatRecyclerView.setVisibility(View.GONE);
                racialRecyclerView.setVisibility(View.GONE);
            }
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        else
        {
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    //Code to hide and unhide Combat Rule content
    public void hide2(View view) {
        if(combatRecyclerView.getVisibility() == View.GONE) {
            if(mRecyclerView.getVisibility() == View.VISIBLE || racialRecyclerView.getVisibility() == View.VISIBLE)
            {
                mRecyclerView.setVisibility(View.GONE);
                racialRecyclerView.setVisibility(View.GONE);
            }
            combatRecyclerView.setVisibility(View.VISIBLE);
        }
        else
        {
            combatRecyclerView.setVisibility(View.GONE);
        }
    }

    //Code to hide and unhide Racial Trait content
    public void hide3(View view) {
        if(racialRecyclerView.getVisibility() == View.GONE) {
            if(mRecyclerView.getVisibility() == View.VISIBLE || combatRecyclerView.getVisibility() == View.VISIBLE)
            {
                mRecyclerView.setVisibility(View.GONE);
                combatRecyclerView.setVisibility(View.GONE);
            }
            racialRecyclerView.setVisibility(View.VISIBLE);
        }
        else
        {
            racialRecyclerView.setVisibility(View.GONE);
        }
    }
}