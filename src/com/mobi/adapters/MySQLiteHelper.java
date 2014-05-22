package com.mobi.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

  public static final String TABLE_MYLIST = "mylist";
  public static final String COLUMN_ID = "_id";
  public static final String PRICE = "price";
  public static final String IMG = "img";
  public static final String  MAKE = "make";
  public static final String  Model = "model";
  public static final String  Millage = "millage";
  public static final String YEAR="year";
  private static final String DATABASE_NAME = "cars.db";
  private static final int DATABASE_VERSION = 1;
  public  static final String CID = "cid";
  SQLiteDatabase database=null;
  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_MYLIST + "(" + COLUMN_ID
      + " integer primary key autoincrement, " + PRICE
      + " text not null," +
      MAKE
      + " text not null,"+
      
      Millage
      + " text not null,"+
      Model
      + " text not null,"+
      YEAR
      + " text not null,"+
      IMG
      + " BLOB, "+
      CID
      + " text  UNIQUE"+
      
      
      ");";

  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYLIST);
    onCreate(db);
  }
  
  public long inserDetails(ContentValues values)
  {
	  return database.insert(TABLE_MYLIST, null, values);
	  
  }
  
  /**
   * Car id Delete
   * @param id
   */
  public void deleteCar(String id) {
	    
	    database.delete( TABLE_MYLIST, MySQLiteHelper.CID
	        + " = " + id, null);
	  }
  
  public Cursor getCarDetails(String cid)
  {
	  return   database.query(TABLE_MYLIST,new String[] {PRICE,MAKE,IMG,Millage,Model,YEAR},CID + "= "+cid, null, null, null, null);
	  
  }
  
  public Cursor getCarList()
  {
	  return   database.query(TABLE_MYLIST,new String[] {CID,PRICE,MAKE,IMG,Millage,Model,YEAR},null, null, null, null, null);
	  
  }
  public SQLiteDatabase open() throws SQLException {
	     database = this.getWritableDatabase();
	     return database;
	  }

} 