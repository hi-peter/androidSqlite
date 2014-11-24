package org.sunpei.dao.dao.base;

import java.lang.reflect.Field;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
/**
 * this is the DBHelper
 * @author peter.sun 
 * if you have question ,please send to hisp@163.com
 * 
 */
public class DBHelper extends SQLiteOpenHelper {
	private String dbname;// ------------数据库名
	private int dbversion;// ------------数据库版本
	private Context context;
	private SQLiteDatabase db;
	private boolean isInnerDb;

	private String[] sqlArr;
	private Class[] beanArr;
	public DBHelper(Context context, String name, int version,boolean isInnerDb) {
		super(context, name, null, version);
		this.context = context;
		this.dbname = name;
		this.dbversion = version;
		this.isInnerDb = isInnerDb;
		if(Build.VERSION.SDK_INT >= 11){  
			getReadableDatabase().enableWriteAheadLogging();
		}  
	}
	public DBHelper(Context context, String name, int version,boolean isInnerDb,String[] sqlArr) {
		super(context, name, null, version);
		this.context = context;
		this.dbname = name;
		this.dbversion = version;
		this.isInnerDb = isInnerDb;
		this.sqlArr = sqlArr;
		if(Build.VERSION.SDK_INT >= 11){  
			getReadableDatabase().enableWriteAheadLogging();  
		 }
	}
	public DBHelper(Context context, String name, int version,boolean isInnerDb,Class[] beanArr) {
		super(context, name, null, version);
		this.context = context;
		this.dbname = name;
		this.dbversion = version;
		this.isInnerDb = isInnerDb;
		this.beanArr = beanArr;
		if(Build.VERSION.SDK_INT >= 11){  
			getReadableDatabase().enableWriteAheadLogging();  
		 }
	}

	public String getDbname() {
		return dbname;
	}

	public int getDbversion() {
		return dbversion;

	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		if(this.sqlArr!=null&&this.sqlArr.length>0){
			for(String sql :sqlArr){
				db.execSQL(sql);
			}
		}
		if(this.beanArr!=null&&this.beanArr.length>0){
			for(Class bean :beanArr){
				String sqlStr = getSqlByBean(bean);
				db.execSQL(sqlStr);
			}
		}
		if(isInnerDb){
			db.execSQL(CreateTableSql.TABLE_USERINFO_SQL);		
			db.execSQL(CreateTableSql.TABLE_TREASURE_SQL);
			db.execSQL(CreateTableSql.TABLE_ALARM_SQL);
			db.execSQL(CreateTableSql.TABLE_SWITCH_SQL);
		}else{	
		}
	}
	private String getSqlByBean(Class bean) {
		Field[] fields = bean.getDeclaredFields();
		Field.setAccessible(fields, true);
		StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
		sql.append(bean.getSimpleName());
		sql.append("(");
		for (Field field : fields) {
			String colum = field.getName();
			if ("serialVersionUID".equals(colum))
				continue;
			sql.append("'");
			sql.append(colum);
			sql.append("' VARCHAR,");
		}
		String sqlStr = sql.toString();
		return sqlStr.substring(0, sqlStr.length()-2)+")";
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}

	public boolean isDBNotFree() {
		return db != null
				&& (db.isDbLockedByCurrentThread());
	}
}
