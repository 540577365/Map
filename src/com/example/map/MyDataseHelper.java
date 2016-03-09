package com.example.map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 数据库，以防以后使用
 * @author ly12974
 *
 */
public class MyDataseHelper extends SQLiteOpenHelper {
	
	public static final String CREATE_MAP = "create table Map (" +
											"id integer primary key autoincrement," +
											"lat text," +
											"lon text";
	
	private Context context;

	public MyDataseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_MAP);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	
	private MyDataseHelper dbHelper;
	/**
	 * 添加数据
	 */
	private void add(){
		dbHelper = new MyDataseHelper(context, "Street.db", null, 1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("lat", "1111.11");
		values.put("lon", "1112.22");
		db.insert("Map", null, values);
		values.clear();
	}
	
	private void query(){
		dbHelper = new MyDataseHelper(context,"Street.db",null,1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query("Book", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				String lat = cursor.getString(cursor.getColumnIndex("lat"));
				String lon = cursor.getString(cursor.getColumnIndex("lon"));
				/**
				 * 添加数据
				 */
			} while (cursor.moveToNext());
		}
		cursor.close();
	}

}
