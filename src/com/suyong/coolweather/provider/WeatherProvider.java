package com.suyong.coolweather.provider;

/*
 * Uri的应用：
 * 数据修改之后，如何处理由此带来的影响
 */

import java.util.List;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class WeatherProvider extends ContentProvider {
	private WeatherSQLiteOpenHelper weatherSQLiteOpenHelper;

	private static final String TAG ="WeatherProvider";
	
	public static final String ATHORITY ="com.suyong.coolweather.provider.WeatherProvider";
	public static final Uri URI =Uri.parse("content://" +ATHORITY);
	
	private static final int URIKEY_WEATHER_ALLROWS =1;
	private static final int URIKEY_WEATHER_SINGLEROW =2;
	private static final UriMatcher uriMatcher =new UriMatcher(UriMatcher.NO_MATCH);
	
	static{
		uriMatcher.addURI(ATHORITY, "Weather", URIKEY_WEATHER_ALLROWS);
		uriMatcher.addURI(ATHORITY, "Weather/#", URIKEY_WEATHER_SINGLEROW);
	}
	
	@Override
	public int delete(Uri uri, String arg1, String[] arg2) {
		SQLiteDatabase db =weatherSQLiteOpenHelper.getWritableDatabase();
		
		switch(uriMatcher.match(uri)){
		case URIKEY_WEATHER_ALLROWS:
			break;
		case URIKEY_WEATHER_SINGLEROW:
			break;
		default:
			throw new IllegalArgumentException("Unsupported Uri:" +uri);
		}
		
		return db.delete(WeatherSQLiteOpenHelper.TABLE_WEATHER, arg1, arg2);
	}

	@Override
	public String getType(Uri uri) {
		switch(uriMatcher.match(uri)){
		case URIKEY_WEATHER_ALLROWS:
			return "vnd.android.cursor.dir/com.suyong.coolweather.provider.WeatherProvider";
		case URIKEY_WEATHER_SINGLEROW:
			return "vnd.android.cursor.item/com.suyong.coolweather.provider.WeatherProvider";
		default:
			throw new IllegalArgumentException("Unsupported Uri:" +uri); 
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db =weatherSQLiteOpenHelper.getWritableDatabase();
		long insertId;
		Uri insertUri;
		
		
		
		switch(uriMatcher.match(uri)){
		case URIKEY_WEATHER_ALLROWS:
			break;
		default:
			throw new IllegalArgumentException("Unsupported Uri:" +uri); 
		}
		
		insertId =db.insert(WeatherSQLiteOpenHelper.TABLE_WEATHER, null, values);
		insertUri =ContentUris.withAppendedId(uri, insertId);
		
		return insertUri;
	}

	@Override
	public boolean onCreate() {
		weatherSQLiteOpenHelper =new WeatherSQLiteOpenHelper(
				getContext(), WeatherSQLiteOpenHelper.DBName, null, WeatherSQLiteOpenHelper.DBVersion);
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db =weatherSQLiteOpenHelper.getWritableDatabase();	
		
		switch(uriMatcher.match(uri)){
		case URIKEY_WEATHER_ALLROWS:
			break;
		case URIKEY_WEATHER_SINGLEROW:
			break;
		default:
			throw new IllegalArgumentException("Unsupported Uri:" +uri); 
		}
		
		return db.query(WeatherSQLiteOpenHelper.TABLE_WEATHER, 
				projection, selection, selectionArgs, null, null, sortOrder);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db =weatherSQLiteOpenHelper.getWritableDatabase();
		String id;
		List<String> listWatch;
		
		switch(uriMatcher.match(uri)){
		case URIKEY_WEATHER_ALLROWS:
			break;
		case URIKEY_WEATHER_SINGLEROW:
			listWatch =uri.getPathSegments();
			id =listWatch.get(1);
			selection =WeatherSQLiteOpenHelper.COLUMN_WEATHER_ID +" =" +id +" and " +selection;
			break;
		default:
			throw new IllegalArgumentException("Unsupported Uri:" +uri); 
		}
		
		return db.update(WeatherSQLiteOpenHelper.TABLE_WEATHER, values, selection, selectionArgs);
	}

	private class WeatherSQLiteOpenHelper extends SQLiteOpenHelper{
		private static final String TAG ="WeatherSQLiteOpenHelper";
		
		private static final String DBName ="weather.db";
		private static final int DBVersion =1;
		
		private static final String TABLE_WEATHER ="Weather";
		private static final String COLUMN_WEATHER_ID ="weather_id";
		private static final String COLUMN_WEATHER ="weahter";

		private static final String SQL_TABLE_WEATHER_CREATE ="create table " +TABLE_WEATHER +"( " +
				COLUMN_WEATHER_ID + " integer primary key autoincrement," +
				COLUMN_WEATHER +" text not null)";
		
		private static final String SQL_TABLE_WEATHER_DROP ="if exits drop table " +TABLE_WEATHER;
				
		
		public WeatherSQLiteOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(TAG, "onCreate");
			
			db.execSQL(SQL_TABLE_WEATHER_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d(TAG, "onUpgrade: upgrade from " +oldVersion +" to " +newVersion);
			
			db.execSQL(SQL_TABLE_WEATHER_DROP);
			onCreate(db);
		}
		
	}
	
}
