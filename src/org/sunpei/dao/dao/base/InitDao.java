package org.sunpei.dao.dao.base;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * this is the sqls's class,store every create table sql for you want
 * @author peter.sun 
 * if you have question ,please send to hisp@163.com
 * 
 */
public class InitDao{
	public static String innerDbName;
	public static String outerDbName;
	public static int innerDBVersion = 1;
	public static int outerDBVersion = 1;
	public static Context context;
	public static InitDao initDao=null;
	public static SQLiteOpenHelper mDbHelper;
	private SQLiteDatabase mDb;
	//1.init
	//2.getHelper
	//3.create sql
	public static InitDao init(String dbName,Context ctx) {
		if(initDao==null){
			initDao = new InitDao();
		}
		InitDao.innerDbName = dbName;
		context = ctx;
		return initDao;
	}
	public static InitDao init(String dbName,Context ctx,boolean isInnerDb) {
		if(initDao==null){
			initDao = new InitDao();
		}
		if(isInnerDb){
			InitDao.innerDbName = dbName;
		}else{
			InitDao.outerDbName = dbName;
		}
		context = ctx;
		return initDao;
	}
	public static InitDao init(String dbName,int dbVersion,Context ctx,boolean isInnerDb) {
		if(initDao==null){
			initDao = new InitDao();
		}
		if(isInnerDb){
			InitDao.innerDbName = dbName;
			InitDao.innerDBVersion = dbVersion;
		}else{
			InitDao.outerDbName = dbName;
			InitDao.outerDBVersion = dbVersion;
		}
		context = ctx;
		return initDao;
	}
	private static DBHelper sdCardHelper; 
	private static DBHelper localHelper;// ---------inner db
	public DBHelper getOuterDBHelper() { 
		if (sdCardHelper == null) {
			sdCardHelper = new DBHelper(context,InitDao.outerDbName, InitDao.outerDBVersion,false);
		}
		mDbHelper = sdCardHelper;
		getReadable();
		return sdCardHelper;
	}
	public  DBHelper getInnerDBHelper() { 
		if (localHelper == null) {
			localHelper = new DBHelper(context,InitDao.innerDbName,
					InitDao.innerDBVersion, true);
		}
		mDbHelper = localHelper;
		getReadable();
		return localHelper;
	}
	public  DBHelper getInnerDBHelper(String[] sqlArr) { 
		if (localHelper == null) {
			localHelper = new DBHelper(context,InitDao.innerDbName,
					InitDao.innerDBVersion, true,sqlArr);
		}
		mDbHelper = localHelper;
		getReadable();
		return localHelper;
	}
	public  DBHelper getInnerDBHelper(Class[] beanArr) { 
		if (localHelper == null) {
			localHelper = new DBHelper(context,InitDao.innerDbName,
					InitDao.innerDBVersion, true,beanArr);
		}
		mDbHelper = localHelper;
		getReadable();
		return localHelper;
	}
	SQLiteDatabase getReadable() {
		if (mDb == null || !mDb.isOpen()) {
			while (mDbHelper.getReadableDatabase().isDbLockedByCurrentThread()) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			mDb = mDbHelper.getReadableDatabase();
		}
		return mDb;
	}

	SQLiteDatabase getWrittable() {
		if (mDb == null || !mDb.isOpen()) {
			while (mDbHelper.getWritableDatabase().isDbLockedByCurrentThread()) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			mDb = mDbHelper.getWritableDatabase();
		}
		return mDb;
	}
}
