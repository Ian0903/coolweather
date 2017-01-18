package db;

import android.database.sqlite.SQLiteOpenHelper;

public class CoolWeatherOpenHelper extends SQLiteOpenHelper{
	/**
	 * 	Province表建表语句
	 */
	public static final String CREATE_PROVINCE="create table Province ("
			+ "id integer primary key autoincrement,"
			+ "province_name text,"
			+ "province_code text)";
	/**
	 * City表建表语句
	 */
	public static final String CREATE_CITY = "create table City ("
			+ "id integer primary key autoincrement,"
			+ "city_name text,"
			+ "city_code text)";

}
