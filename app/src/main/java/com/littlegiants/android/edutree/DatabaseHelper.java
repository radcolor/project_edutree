package com.littlegiants.android.edutree;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserContent.db";
    public static final String TABLE_NAME = "User_Data";
    public static final String Col_USER_ID = "Data_Id";
    public static final String Col_USER_NAME = "Data_Name";
    public static final String COL_USER_EMAIL = "Data_Email";
    public static final String Col_USER_CLASS = "Data_Class";
    public static final String COL_USER_SCHOOL_NAME = "School_Name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (Data_Id TEXT, Data_Name TEXT, Data_email TEXT, Data_Class TEXT, School_Name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String Data_Id, String fb_name, String fb_email, String fb_class, String fb_school_name){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_USER_ID, Data_Id);
        contentValues.put(Col_USER_NAME, fb_name);
        contentValues.put(COL_USER_EMAIL, fb_email);
        contentValues.put(Col_USER_CLASS, fb_class);
        contentValues.put(COL_USER_SCHOOL_NAME, fb_school_name);
        long result = db.insert(TABLE_NAME, null , contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }

    }

    public Cursor getAllData(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);

        return res;
    }

    public Cursor getOneData(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select Col_USER_ID from "+TABLE_NAME,null);

        return res;
    }

    public boolean updateData(String id, String name,String email, String fb_class, String fb_school_name){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_USER_ID, id);
        contentValues.put(Col_USER_NAME, name);
        contentValues.put(COL_USER_EMAIL, email);
        contentValues.put(Col_USER_CLASS, fb_class);
        contentValues.put(COL_USER_SCHOOL_NAME, fb_school_name);

        db.update(TABLE_NAME, contentValues, "Data_Id = ?",new String[] {id});
        return true;

    }


    public boolean updateOneData(String id,String columnName,String entryRecord){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(columnName, entryRecord);
        db.update(TABLE_NAME, contentValues, "Data_Id = ?",new String[] {id});
        return true;

    }


    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "Data_Id = ?", new String[] {id});
    }

    public void dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "drop table " + TABLE_NAME;
        db.execSQL(sql);
    }

}
