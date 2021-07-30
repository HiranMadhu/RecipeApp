package com.example.cookingrecipe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME="StoringDatabase.db";
    public static final int DATABASE_VERSION=1;

    public static final String TABLE_NAME="showing_recipe";
    public static final String COLUMN_TITLE="recipe_title";
    public static final String COLUMN_NUTRITION="nutrition";
    public static final String COLUMN_PREPARE="prepare_time";
    public static final String COLUMN_COOK="cook_time";
    public static final String COLUMN_INGREDIENTS="ingredients";
    public static final String COLUMN_SCALE_NO="scales_no";
    public static final String COLUMN_SCALE_TYPE="scales_type";
    public static final String COLUMN_HOW="how_to";
    public static final String COLUMN_NOTE="notes";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        String query="CREATE TABLE "+TABLE_NAME+
                " ("+COLUMN_TITLE+" TEXT PRIMARY KEY, "+
                COLUMN_NUTRITION+" TEXT, "+
                COLUMN_PREPARE+" INTEGER, "+
                COLUMN_COOK+" INTEGER, "+
                COLUMN_INGREDIENTS+" TEXT, "+
                COLUMN_SCALE_NO+" TEXT, "+
                COLUMN_SCALE_TYPE+" TEXT, "+
                COLUMN_HOW+" TEXT, "+
                COLUMN_NOTE+" TEXT);";
        DB.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(DB);
    }

    public Boolean inertData(String name, String nutrition, Integer prep, Integer cook, String ingre,String scale,String how,String extr) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE,name );
        contentValues.put(COLUMN_NUTRITION,nutrition );
        contentValues.put(COLUMN_PREPARE,prep );
        contentValues.put(COLUMN_COOK,cook );
        contentValues.put(COLUMN_INGREDIENTS,ingre );
        contentValues.put(COLUMN_SCALE_NO,scale );
        contentValues.put(COLUMN_HOW,how );
        contentValues.put(COLUMN_NOTE,extr );
        long result = DB.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public Boolean updateUserData(String name2,String name, String nutrition, Integer prep, Integer cook, String ingre,String scale,String how,String extr) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE,name );
        contentValues.put(COLUMN_NUTRITION,nutrition );
        contentValues.put(COLUMN_PREPARE,prep );
        contentValues.put(COLUMN_COOK,cook );
        contentValues.put(COLUMN_INGREDIENTS,ingre );
        contentValues.put(COLUMN_SCALE_NO,scale );
        contentValues.put(COLUMN_HOW,how );
        contentValues.put(COLUMN_NOTE,extr );

        Cursor cursor = DB.rawQuery("Select * from "+TABLE_NAME+" where "+COLUMN_TITLE+" = ?", new String[]{name2});
        if (cursor.getCount() > 0) {
            long result = DB.update(TABLE_NAME, contentValues, COLUMN_TITLE+" =?", new String[]{name2});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else{
            return false;
        }
    }

    public Boolean deleteData(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from "+TABLE_NAME+" where "+COLUMN_TITLE+" = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.delete(TABLE_NAME, COLUMN_TITLE+" =?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else{
            return false;
        }
    }



    public Cursor getData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select "+COLUMN_TITLE+" from "+TABLE_NAME,null);
        return cursor;
    }
    public Cursor getAllData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from "+TABLE_NAME,null);
        return cursor;
    }
    public Cursor getRecipeData(String title) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from "+TABLE_NAME+" where "+COLUMN_TITLE+" = ?", new String[]{title});
        return cursor;
    }
    public Cursor getNutrition(String title) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from "+TABLE_NAME+" where "+COLUMN_TITLE+" = ?", new String[]{title});
        return cursor;
    }
}

