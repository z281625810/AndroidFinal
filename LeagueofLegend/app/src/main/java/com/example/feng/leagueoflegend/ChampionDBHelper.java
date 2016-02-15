package com.example.feng.leagueoflegend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Feng on 12/2/2015.
 */
public class ChampionDBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "Champion.db";
    public static final String CHAMPION_TABLE_NAME = "champions";
    public static final String CHAMPION_COLUMN_ID = "id";
    public static final String CHAMPION_COLUMN_NAME = "name";
    public static final String CHAMPION_COLUMN_TITLE = "title";
    public static final String CHAMPION_COLUMN_DESCRIPTION = "description";
    public static final String CHAMPION_COLUMN_TAGONE = "tagOne";
    public static final String CHAMPION_COLUMN_TAGTWO = "tagTwo";
    public static final String CHAMPION_COLUMN_HEALTH = "health";
    public static final String CHAMPION_COLUMN_HEALTHREGEN = "healthRegen";
    public static final String CHAMPION_COLUMN_MANA = "mana";
    public static final String CHAMPION_COLUMN_MANAREGEN = "manaRegen";
    public static final String CHAMPION_COLUMN_MOVESPEED = "moveSpeed";
    public static final String CHAMPION_COLUMN_ATTACKDAMAGE = "attackDamage";
    public static final String CHAMPION_COLUMN_ATTCKSPEED = "attackSpeed";
    public static final String CHAMPION_COLUMN_ATTACKRANGE = "attackRange";
    public static final String CHAMPION_COLUMN_ARMOR = "armor";
    public static final String CHAMPION_COLUMN_MR = "MR";
    public static final String CHAMPION_COLUMN_IMAGE = "image";
    public static final int DATABASE_VERSION = 1;

    public ChampionDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + CHAMPION_TABLE_NAME + "("
                + CHAMPION_COLUMN_ID + " text primary key, " +
                CHAMPION_COLUMN_NAME + " text, " +
                CHAMPION_COLUMN_TITLE + " text, " +
                CHAMPION_COLUMN_DESCRIPTION + " text, " +
                CHAMPION_COLUMN_TAGONE + " text, " +
                CHAMPION_COLUMN_TAGTWO + " text, " +
                CHAMPION_COLUMN_HEALTH + " text, " +
                CHAMPION_COLUMN_HEALTHREGEN + " text, " +
                CHAMPION_COLUMN_MANA + " text, " +
                CHAMPION_COLUMN_MANAREGEN + " text, " +
                CHAMPION_COLUMN_MOVESPEED + " text, " +
                CHAMPION_COLUMN_ATTACKDAMAGE + " text, " +
                CHAMPION_COLUMN_ATTCKSPEED + " text, " +
                CHAMPION_COLUMN_ATTACKRANGE + " text, " +
                CHAMPION_COLUMN_ARMOR + " text, " +
                CHAMPION_COLUMN_MR + " text, " +
                CHAMPION_COLUMN_IMAGE + " text)");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS champions");
        onCreate(db);
    }

    public boolean insertChampion(String id, String name, String title, String description, String tagOne, String tagTwo, String health,
                                 String healthRegen, String mana, String manaRegen, String moveSpeed, String attackDamage, String attackSpeed,
                                 String attackRange, String armor, String MR, String image)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("tagOne", tagOne);
        contentValues.put("tagTwo", tagTwo);
        contentValues.put("health", health);
        contentValues.put("healthRegen", healthRegen);
        contentValues.put("mana", mana);
        contentValues.put("manaRegen", manaRegen);
        contentValues.put("moveSpeed", moveSpeed);
        contentValues.put("attackDamage", attackDamage);
        contentValues.put("attackSpeed", attackSpeed);
        contentValues.put("attackRange", attackRange);
        contentValues.put("armor", armor);
        contentValues.put("MR", MR);
        contentValues.put("image", image);

        SQLiteDatabase rdb = this.getReadableDatabase();
        Cursor cursor = rdb.rawQuery("select * from " + CHAMPION_TABLE_NAME + " where id = " + id, null);


        if(cursor.getCount() < 1) {
            db.insert("champions", null, contentValues);
        }
        else{
            return false;
        }

        return true;
    }

    public ArrayList<Champion> getAllChampions()
    {
        ArrayList<Champion> mList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from champions", null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            String id = res.getString(res.getColumnIndex(CHAMPION_COLUMN_ID));
            String name = res.getString(res.getColumnIndex(CHAMPION_COLUMN_NAME));
            String title = res.getString(res.getColumnIndex(CHAMPION_COLUMN_TITLE));
            String description = res.getString(res.getColumnIndex(CHAMPION_COLUMN_DESCRIPTION));
            String tagOne = res.getString(res.getColumnIndex(CHAMPION_COLUMN_TAGONE));
            String tagTwo = res.getString(res.getColumnIndex(CHAMPION_COLUMN_TAGTWO));
            String health = res.getString(res.getColumnIndex(CHAMPION_COLUMN_HEALTH));
            String healthRegen = res.getString(res.getColumnIndex(CHAMPION_COLUMN_HEALTHREGEN));
            String mana = res.getString(res.getColumnIndex(CHAMPION_COLUMN_MANA));
            String manaRegen = res.getString(res.getColumnIndex(CHAMPION_COLUMN_MANAREGEN));
            String moveSpeed = res.getString(res.getColumnIndex(CHAMPION_COLUMN_MOVESPEED));
            String attackDamage = res.getString(res.getColumnIndex(CHAMPION_COLUMN_ATTACKDAMAGE));
            String attackSpeed = res.getString(res.getColumnIndex(CHAMPION_COLUMN_ATTCKSPEED));
            String attackRange = res.getString(res.getColumnIndex(CHAMPION_COLUMN_ATTACKRANGE));
            String armor = res.getString(res.getColumnIndex(CHAMPION_COLUMN_ARMOR));
            String MR = res.getString(res.getColumnIndex(CHAMPION_COLUMN_MR));
            String image = res.getString(res.getColumnIndex(CHAMPION_COLUMN_IMAGE));

            Champion champion = new Champion(id, name,title, description, tagOne, tagTwo,
                    health, healthRegen, mana, manaRegen, moveSpeed,  attackDamage, attackSpeed, attackRange, armor, MR,  image);
            mList.add(champion);
            res.moveToNext();
        }

        return mList;

    }

}
