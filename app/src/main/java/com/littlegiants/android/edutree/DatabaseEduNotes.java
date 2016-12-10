package com.littlegiants.android.edutree;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.littlegiants.android.edutree.NotesDataModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseEduNotes extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserNotes.db";
    public static final String TABLE_NAME = "User_Notes";
    public static final String Col_USER_ID = "Data_Id";
    public static final String Col_NOTES_TITTLE = "Data_Tittle";
    public static final String Col_NOTES_NOTES = "Data_Notes";
    public static final String Col_NOTES_DATE = "Data_Date";
    public static final String Col_NOTES_TIME = "Data_Time";

    public DatabaseEduNotes(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (Data_Id INTEGER PRIMARY KEY AUTOINCREMENT, Data_Tittle TEXT, Data_Notes TEXT, Data_Date TEXT, Data_Time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String Data_Tittle ,String Data_Notes,String Data_Date ,String Data_Time){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_NOTES_TITTLE, Data_Tittle);
        contentValues.put(Col_NOTES_NOTES, Data_Notes);
        contentValues.put(Col_NOTES_DATE, Data_Date);
        contentValues.put(Col_NOTES_TIME, Data_Time);
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

    public boolean updateData(String Data_Id, String Data_Tittle ,String Data_Notes,String Data_Date ,String Data_Time ){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_USER_ID, Data_Id);
        contentValues.put(Col_NOTES_TITTLE, Data_Tittle);
        contentValues.put(Col_NOTES_NOTES, Data_Notes);
        contentValues.put(Col_NOTES_DATE, Data_Date);
        contentValues.put(Col_NOTES_TIME, Data_Time);
        db.update(TABLE_NAME, contentValues, "Data_Id = ?",new String[] {Data_Id});
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

    public ArrayList<NotesDataModel> getdata(){
        // DataModel dataModel = new DataModel();
        ArrayList<NotesDataModel> data=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME+" ;",null);
        StringBuffer stringBuffer = new StringBuffer();
        NotesDataModel dataModel = null;
        while (cursor.moveToNext()) {
            dataModel= new NotesDataModel();
            String name = cursor.getString(cursor.getColumnIndexOrThrow("Data_Tittle"));
            String country = cursor.getString(cursor.getColumnIndexOrThrow("Data_Date"));
            String city = cursor.getString(cursor.getColumnIndexOrThrow("Data_Notes"));
            dataModel.setTittle(name);
            dataModel.setNotes(city);
            dataModel.setDate(country);
            stringBuffer.append(dataModel);
            // stringBuffer.append(dataModel);
            data.add(dataModel);
        }

        for (NotesDataModel mo:data ) {

            Log.i("Hellomo",""+mo.getDate());
        }
        return data;
    }

}
