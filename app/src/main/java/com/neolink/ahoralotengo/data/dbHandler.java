package com.neolink.ahoralotengo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final  String DATABASE_NAME = "dbName.db";

    public static final String TABLE1_NAME = "CUENTA";

    public static final String TABLE1_COL1 = "_id";
    public static final String TABLE1_COL2 = "_nam";
    public static final String TABLE1_COL3 = "_val";

    public dbHandler(Context context, SQLiteDatabase.CursorFactory factory){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE1 = "CREATE TABLE " + TABLE1_NAME + "(" + TABLE1_COL1 + " INTEGER PRIMARY KEY, " + TABLE1_COL2 + " TEXT, " + TABLE1_COL3 + " TEXT)";

        db.execSQL(CREATE_TABLE1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);

        onCreate(db);
    }

    //FUNCIONES GENERALES
    public int totRegsInTable(String table){
        String query = "SELECT * FROM " + table;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        int r = c.getCount();
        return r;
    }

    //TABLA: CUENTA
    public void setUpAccount(String aid, String uid, String usu, String tip, String cor, String nom, String ape, String lml, String cod, String lmt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TABLE1_COL2, "aid");
        values.put(TABLE1_COL3, aid);
        db.insert(TABLE1_NAME, null, values);
        values = new ContentValues();
        values.put(TABLE1_COL2, "uid");
        values.put(TABLE1_COL3, uid);
        db.insert(TABLE1_NAME, null, values);
        values = new ContentValues();
        values.put(TABLE1_COL2, "usu");
        values.put(TABLE1_COL3, usu);
        db.insert(TABLE1_NAME, null, values);
        values = new ContentValues();
        values.put(TABLE1_COL2, "tip");
        values.put(TABLE1_COL3, tip);
        db.insert(TABLE1_NAME, null, values);
        values = new ContentValues();
        values.put(TABLE1_COL2, "cor");
        values.put(TABLE1_COL3, cor);
        db.insert(TABLE1_NAME, null, values);
        values = new ContentValues();
        values.put(TABLE1_COL2, "nom");
        values.put(TABLE1_COL3, nom);
        db.insert(TABLE1_NAME, null, values);
        values = new ContentValues();
        values.put(TABLE1_COL2, "ape");
        values.put(TABLE1_COL3, ape);
        db.insert(TABLE1_NAME, null, values);
        values = new ContentValues();
        values.put(TABLE1_COL2, "fup");
        values.put(TABLE1_COL3, "");
        db.insert(TABLE1_NAME, null, values);
        values = new ContentValues();
        values.put(TABLE1_COL2, "fbid");
        values.put(TABLE1_COL3, "");
        db.insert(TABLE1_NAME, null, values);
        values = new ContentValues();
        values.put(TABLE1_COL2, "lml");
        values.put(TABLE1_COL3, lml);
        db.insert(TABLE1_NAME, null, values);
        values = new ContentValues();
        values.put(TABLE1_COL2, "cod");
        values.put(TABLE1_COL3, cod);
        db.insert(TABLE1_NAME, null, values);
        values = new ContentValues();
        values.put(TABLE1_COL2, "hts");
        values.put(TABLE1_COL3, "0");
        db.insert(TABLE1_NAME, null, values);
        values = new ContentValues();
        values.put(TABLE1_COL2, "lmt");
        values.put(TABLE1_COL3, lmt);
        db.insert(TABLE1_NAME, null, values);
    }

    public boolean hasAccount(){
        boolean res;
        String query = "SELECT * FROM " + TABLE1_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor r = db.rawQuery(query, null);

        if(r.getCount() > 0){
            res = true;
        } else {
            res = false;
        }

        return res;
    }
    public void removeAccount(){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM " + TABLE1_NAME;
        db.execSQL(query);
    }
    public String getParamByName(String name){
        String r = "NOT_FOUND";
        String query = "SELECT " + TABLE1_COL3 + " FROM " + TABLE1_NAME + " WHERE " + TABLE1_COL2 + " = '" + name + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        if(c.getCount() == 1){
            c.moveToFirst();
            r = c.getString(0);
            c.close();
        }

        return r;
    }
    public void updateParams(String par, String val) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE1_NAME + " SET " + TABLE1_COL3 + " = '" + val + "' WHERE " + TABLE1_COL2 + " = '" + par + "'";
        db.execSQL(query);
    }
}
