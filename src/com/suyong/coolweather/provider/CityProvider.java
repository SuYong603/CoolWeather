package com.suyong.coolweather.provider;

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

public class CityProvider extends ContentProvider {
	private static final String TAG ="CityProvider";

	private CitySQLiteOpenHelper citySQLiteOpenHelper;
	
	private static final String AUTHORITY ="com.suyong.coolweather.provider.CityProvider";
	public  static final Uri URI =Uri.parse("content://" +AUTHORITY);
	
	private static final int URIKEY_PROVINCE_ALLROWS =1;
	private static final int URIKEY_PROVINCE_SINGLEROW =2;
	private static final int URIKEY_CITY_ALLROWS =3;
	private static final int URIKEY_CITY_SINGLEROW =4;
	private static final int URIKEY_DISTRICT_ALLROWS =5;
	private static final int URIKEY_DISTRICT_SINGLEROW =6;
	
	private static final UriMatcher uriMatcher =new UriMatcher(UriMatcher.NO_MATCH);
	
	static{
		uriMatcher.addURI(AUTHORITY, "Province", URIKEY_PROVINCE_ALLROWS);
		uriMatcher.addURI(AUTHORITY, "Province/#", URIKEY_PROVINCE_SINGLEROW);
		uriMatcher.addURI(AUTHORITY, "City", URIKEY_CITY_ALLROWS);
		uriMatcher.addURI(AUTHORITY, "City/#", URIKEY_CITY_SINGLEROW);
		uriMatcher.addURI(AUTHORITY, "District", URIKEY_DISTRICT_ALLROWS);
		uriMatcher.addURI(AUTHORITY, "District/#", URIKEY_DISTRICT_SINGLEROW);
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db =citySQLiteOpenHelper.getWritableDatabase();
		String tableName;
		List<String> listUri;
		String id;
		
		switch(uriMatcher.match(uri)){
		case URIKEY_PROVINCE_ALLROWS:
			tableName =CitySQLiteOpenHelper.TABLE_PROVINCE;
			break;
		case URIKEY_PROVINCE_SINGLEROW:
			tableName =CitySQLiteOpenHelper.TABLE_PROVINCE;
			listUri =uri.getPathSegments();
			id =listUri.get(1);
			selection =CitySQLiteOpenHelper.COLUMN_PROVINCE_ID +" =" +id +" and " +selection;
			break;
		case URIKEY_CITY_ALLROWS:
			tableName =CitySQLiteOpenHelper.TABLE_CITY;
			break;
		case URIKEY_CITY_SINGLEROW:
			tableName =CitySQLiteOpenHelper.TABLE_CITY;
			listUri =uri.getPathSegments();
			id =listUri.get(1);
			selection =CitySQLiteOpenHelper.COLUMN_CITY_ID +" =" +id +" and " +selection;
			break;
		case URIKEY_DISTRICT_ALLROWS:
			tableName =CitySQLiteOpenHelper.TABEL_DISTRICT;
			break;
		case URIKEY_DISTRICT_SINGLEROW:
			tableName =CitySQLiteOpenHelper.TABEL_DISTRICT;
			listUri =uri.getPathSegments();
			id =listUri.get(1);
			selection =CitySQLiteOpenHelper.COLUMN_DISTRICT_ID +" =" +id +" and " +selection;
			break;
		default:
			throw new IllegalArgumentException("Unsupported Uri:" +uri);
		}
		
		return db.delete(tableName, selection, selectionArgs);
	}

	@Override
	public String getType(Uri uri) {
		switch(uriMatcher.match(uri)){
		case URIKEY_PROVINCE_ALLROWS:
			return "vnd.android.cursor.dir/com.suyong.coolweather.provider.CityProvider.Province";
		case URIKEY_PROVINCE_SINGLEROW:
			return "vnd.android.cursor.item/com.suyong.coolweather.provider.CityProvider.Province";
		case URIKEY_CITY_ALLROWS:
			return "vnd.android.cursor.dir/com.suyong.coolweather.provider.CityProvider.City";
		case URIKEY_CITY_SINGLEROW:
			return "vnd.android.cursor.item/com.suyong.coolweather.provider.CityProvider.City";
		case URIKEY_DISTRICT_ALLROWS:
			return "vnd.android.cursor.dir/com.suyong.coolweather.provider.CityProvider.District";
		case URIKEY_DISTRICT_SINGLEROW:
			return "vnd.android.cursor.item/com.suyong.coolweather.provider.CityProvider.District";
		default:
			throw new IllegalArgumentException("Unsupported Uri:" +uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db =citySQLiteOpenHelper.getWritableDatabase();
		long insertId;
		Uri insertUri;
		String tableName;
		
		switch(uriMatcher.match(uri)){
		case URIKEY_PROVINCE_ALLROWS:
			tableName =CitySQLiteOpenHelper.TABLE_PROVINCE;
			break;
		case URIKEY_CITY_ALLROWS:
			tableName =CitySQLiteOpenHelper.TABLE_CITY;
			break;
		case URIKEY_DISTRICT_ALLROWS:
			tableName =CitySQLiteOpenHelper.TABEL_DISTRICT;
			break;
		default:
			throw new IllegalArgumentException("Unsupported Uri:" +uri);
		}
		
		insertId =db.insert(tableName, null, values);
		insertUri =ContentUris.withAppendedId(uri, insertId);
		
		return insertUri;
	}

	@Override
	public boolean onCreate() {
		citySQLiteOpenHelper =new CitySQLiteOpenHelper(
				getContext(),CitySQLiteOpenHelper.DBNAME, null, CitySQLiteOpenHelper.DBVERSION);
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db =citySQLiteOpenHelper.getWritableDatabase();
		String tableName;
		List<String> listUri;
		String id;
		
		switch(uriMatcher.match(uri)){
		case URIKEY_PROVINCE_ALLROWS:
			tableName =CitySQLiteOpenHelper.TABLE_PROVINCE;
			break;
		case URIKEY_PROVINCE_SINGLEROW:
			tableName =CitySQLiteOpenHelper.TABLE_PROVINCE;
			listUri =uri.getPathSegments();
			id =listUri.get(1);
			selection =CitySQLiteOpenHelper.COLUMN_PROVINCE_ID +" =" +id +" and " +selection;
			break;
		case URIKEY_CITY_ALLROWS:
			tableName =CitySQLiteOpenHelper.TABLE_CITY;
			break;
		case URIKEY_CITY_SINGLEROW:
			tableName =CitySQLiteOpenHelper.TABLE_CITY;
			listUri =uri.getPathSegments();
			id =listUri.get(1);
			selection =CitySQLiteOpenHelper.COLUMN_CITY_ID +" =" +id +" and " +selection;
			break;
		case URIKEY_DISTRICT_ALLROWS:
			tableName =CitySQLiteOpenHelper.TABEL_DISTRICT;
			break;
		case URIKEY_DISTRICT_SINGLEROW:
			tableName =CitySQLiteOpenHelper.TABEL_DISTRICT;
			listUri =uri.getPathSegments();
			id =listUri.get(1);
			selection =CitySQLiteOpenHelper.COLUMN_DISTRICT_ID +" =" +id +" and " +selection;
			break;
		default:
			throw new IllegalArgumentException("Unsupported Uri:" +uri);
		}
		
		return db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db =citySQLiteOpenHelper.getWritableDatabase();
		String tableName;
		List<String> listUri;
		String id;
		
		switch(uriMatcher.match(uri)){
		case URIKEY_PROVINCE_ALLROWS:
			tableName =CitySQLiteOpenHelper.TABLE_PROVINCE;
			break;
		case URIKEY_PROVINCE_SINGLEROW:
			tableName =CitySQLiteOpenHelper.TABLE_PROVINCE;
			listUri =uri.getPathSegments();
			id =listUri.get(1);
			selection =CitySQLiteOpenHelper.COLUMN_PROVINCE_ID +" =" +id +" and " +selection;
			break;
		case URIKEY_CITY_ALLROWS:
			tableName =CitySQLiteOpenHelper.TABLE_CITY;
			break;
		case URIKEY_CITY_SINGLEROW:
			tableName =CitySQLiteOpenHelper.TABLE_CITY;
			listUri =uri.getPathSegments();
			id =listUri.get(1);
			selection =CitySQLiteOpenHelper.COLUMN_CITY_ID +" =" +id +" and " +selection;
			break;
		case URIKEY_DISTRICT_ALLROWS:
			tableName =CitySQLiteOpenHelper.TABEL_DISTRICT;
			break;
		case URIKEY_DISTRICT_SINGLEROW:
			tableName =CitySQLiteOpenHelper.TABEL_DISTRICT;
			listUri =uri.getPathSegments();
			id =listUri.get(1);
			selection =CitySQLiteOpenHelper.COLUMN_DISTRICT_ID +" =" +id +" and " +selection;
			break;
		default:
			throw new IllegalArgumentException("Unsupported Uri:" +uri);
		}
		
		return db.update(tableName, values, selection, selectionArgs);
	}

	private class CitySQLiteOpenHelper extends SQLiteOpenHelper{
		private static final String TAG ="CitySQLiteOpenHelper";
		
		private static final String DBNAME ="city.db";
		private static final int DBVERSION =1;
		
		private static final String TABLE_PROVINCE ="Province";
		private static final String COLUMN_PROVINCE_ID ="province_id";
		private static final String COLUMN_PROVINCE ="province";
		private static final String COLUMN_PROVINCE_CODE ="code";       // Province,City,District三表共享 code
		
		private static final String TABLE_CITY ="City";
		private static final String COLUMN_CITY_ID ="city_id";
		private static final String COLUMN_CITY ="city";
		private static final String COLUMN_CITY_PROVINCE ="province_id";  // City表外键，Province外键
		private static final String COLUMN_CITY_CODE ="code";      // Province,City,District三表共享 code
		
		private static final String TABEL_DISTRICT ="District";
		private static final String COLUMN_DISTRICT_ID ="district_id";
		private static final String COLUMN_DISTRICT ="district";
		private static final String COLUMN_DISTRICT_CITY ="city_id";    // District表外键，City外键
		private static final String COLUMN_DISTRICT_CODE ="code";      // Province,City,District三表共享 code
		
		private static final String SQL_TABLE_PROVINCE_CREATE ="create table " +TABLE_PROVINCE +"(" +
				COLUMN_PROVINCE_ID + " integer primary key autoincrement," +
				COLUMN_PROVINCE + "text not null," +
				COLUMN_PROVINCE_CODE + "integer not null)";
		
		private static final String SQL_TABLE_CITY_CREATE ="create table " +TABLE_CITY +"(" +
				COLUMN_CITY_ID +" integer primary key increment," +
				COLUMN_CITY +" text not null," +
				COLUMN_CITY_PROVINCE +" integer not null," +
				COLUMN_CITY_CODE +" integer not null)";
		
		private static final String SQL_TABLE_DISTRICT_CREATE ="create table " +TABEL_DISTRICT +"(" +
				COLUMN_DISTRICT_ID +" integer primary key autoincrement," +
				COLUMN_DISTRICT +" text not null," +
				COLUMN_DISTRICT_CITY +" integer not null," +
				COLUMN_DISTRICT_CODE +" integer not null)";		
		
		private static final String SQL_TABLE_PROVINCE_DROP ="if exits drop table " +TABLE_PROVINCE;
		private static final String SQL_TABLE_CITY_DROP ="if exits drop table " +TABLE_CITY;
		private static final String SQL_TABLE_DSITRICT_DROP ="if exits drop table " +TABEL_DISTRICT;
		
		public CitySQLiteOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(SQL_TABLE_CITY_CREATE);
			db.execSQL(SQL_TABLE_DISTRICT_CREATE);
			db.execSQL(SQL_TABLE_PROVINCE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d(TAG, "onUpgrade: upgrade from " +oldVersion +" to " +newVersion);
			
			db.execSQL(SQL_TABLE_CITY_DROP);
			db.execSQL(SQL_TABLE_DSITRICT_DROP);
			db.execSQL(SQL_TABLE_PROVINCE_DROP);
			
			onCreate(db);
		}
		
	}
	
}
