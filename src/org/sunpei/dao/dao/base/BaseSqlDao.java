package org.sunpei.dao.dao.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteMisuseException;
import android.util.Log;


/**
 * this is the base sql class for operate the db
 * @author peter.sun 
 * if you have question ,please send to hisp@163.com
 * 
 */
public class BaseSqlDao extends InitDao {
	private static final String TAG = "BaseSqlDao";
	private SQLiteDatabase mDb;

	private static BaseSqlDao baseSqlDao;
	private BaseSqlDao(){
	}
	public SQLiteDatabase getDb(){
		mDb = getReadable();
		return mDb;
	}
	public static BaseSqlDao getInstance(){
		if(baseSqlDao==null){
			baseSqlDao = new BaseSqlDao();
		}
		return baseSqlDao;
	}
	public boolean excuteSql(String sql) {
		try {
			getDb().execSQL(sql);
		} catch (Exception e) {
			Log.e(TAG, "BaseSqlAdapter excuteSql ; sql = " + sql
					+ ";Exception = " + e.getMessage());
			return false;
		}
		return true;
	}

	/** 执行sql */
	public boolean excuteSql(String sql, Object[] pr) {
		try {
			getDb().execSQL(sql, pr);
		} catch (Exception e) {
			Log.e(TAG, "BaseSqlAdapter excuteSql ; sql = " + sql
					+ "; pr=" + pr + ";Exception = " + e.getMessage());
			return false;
		}
		return true;
	}

	/** 执行sql */
	public boolean excuteSql(ArrayList<SQLEntity> sqlList) {
		try {
			getDb().beginTransaction();
			for (SQLEntity entity : sqlList) {
				if (null == entity.getParams())
					getDb().execSQL(entity.getSql());
				else
					getDb().execSQL(entity.getSql(), entity.getParams());
			}
			getDb().setTransactionSuccessful();
		} catch (Exception e) {
			Log.e(TAG, "BaseSqlAdapter excuteSql ; sqlList "
					+ ";Exception = " + e.getMessage());
			return false;
		} finally {
			try {
				getDb().endTransaction();
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(TAG, "BaseSqlAdapter excuteSql ; sqlList "
						+ ";Exception = " + e.getMessage());
			}
		}
		return true;
	}

	private String getSql(String table, Map<String, String> map) {
		String col = "";
		String value = "'";
		if (map.size() > 0) {
			for (String key : map.keySet()) {
				col += key + ",";
				value += map.get(key) + "','";
			}
		}
		col = col.substring(0, col.length() - 1);
		value = value.substring(0, value.length() - 2);
		String sql = "insert or replace into " + table + "(" + col
				+ ") values(" + value + ")";
		return sql;
	}

	public boolean insertOrUpdate(String table, Map<String, String> map) {
		String sql = getSql(table, map);
		return excuteSql(sql);
	}

	public boolean insertOrUpdateTransaction(String table,
			List<Map<String, String>> mapList) {
		try {
			getDb().beginTransaction();
			for (Map<String, String> map2 : mapList) {
				String sql = getSql(table, map2);
				getDb().execSQL(sql);
			}
			getDb().setTransactionSuccessful();
		} catch (Exception e) {
			Log.e(TAG, "BaseSqlAdapter insertSql ; tableName = " + table
					+ ";Exception = " + e.getMessage());
		} finally {
			try {
				getDb().endTransaction();
			} catch (Exception e) {
			}
		}
		return true;
	}

	public long insertSql(String tableName, ContentValues cv,
			boolean isTransaction) {
		long result = 0;
		if (isTransaction) {
			try {
				getDb().beginTransaction();
				getDb().insert(tableName, null, cv);
				getDb().setTransactionSuccessful();
				result = 1;
			} catch (Exception e) {
				Log.e(TAG, "BaseSqlAdapter insertSql ; tableName = "
						+ tableName + ";Exception = " + e.getMessage());
			} finally {
				try {
					getDb().endTransaction();
				} catch (Exception e) {
				}
			}
		} else {
			try {
				result = getDb().insertWithOnConflict(tableName, null, cv,
						SQLiteDatabase.CONFLICT_REPLACE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public boolean insertListBean(String table, List<?> listObj)
			throws IllegalArgumentException, IllegalAccessException {
		try {
			getDb().beginTransaction();
			for (Object object : listObj) {
				insertData(table, object);
			}
			getDb().setTransactionSuccessful();
		} catch (Exception e) {
			Log.e(TAG, "BaseSqlAdapter insertSql ; tableName = " + table
					+ ";Exception = " + e.getMessage());
		} finally {
			try {
				getDb().endTransaction();
			} catch (Exception e) {
			}
		}
		return true;
	}

	public long insertData(String table, Object obj)
			throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = obj.getClass().getDeclaredFields();
		Field.setAccessible(fields, true);
		String col = "";
		String value = "'";
		ContentValues content = new ContentValues();
		for (Field field : fields) {
			if ("id".equals(field.getName()))
				continue;
			if ("serialVersionUID".equals(field.getName()))
				continue;
			col = field.getName();
			if (null == field.get(obj)) {
				value = "";
			} else {
				value = String.valueOf(field.get(obj));
			}
			content.put(col, value);
		}
		// return getDb().insert(table, null, content);
		return getDb().insertWithOnConflict(table, null, content,
				SQLiteDatabase.CONFLICT_REPLACE);
	}

	public boolean delete(String table, String whereClause, String[] whereArgs) {
		try {
			getDb().delete(table, whereClause, whereArgs);
		} catch (Exception e) {
			Log.e(TAG, "BaseSqlAdapter delete ; table = " + table
					+ "; whereClause=" + whereClause + "; whereArgs ="
					+ whereArgs + ";Exception = " + e.getMessage());
			return false;
		} 
		return true;
	}

	public boolean delData(String table, Map<String, String> wheres) {
		String str = "";
		if (null != wheres) {
			str += " where ";
			for (String key : wheres.keySet()) {
				str += key + "='" + wheres.get(key) + "' and ";
			}
			// Iterator iter1 = wheres.entrySet().iterator();
			// while (iter1.hasNext()) {
			// Map.Entry entry1 = (Map.Entry) iter1.next();
			// str += entry1.getKey() + "='" + entry1.getValue() + "' and ";
			// }
			str = str.substring(0, str.length() - 5);
		}
		String sql = "delete from " + table + str;
		return excuteSql(sql);
	}

	public boolean update(String table, ContentValues cv, String whereClause,
			String[] whereArgs) {
		try {
			getDb().update(table, cv, whereClause, whereArgs);
		} catch (Exception e) {
			Log.e(TAG, "BaseSqlAdapter update ; table = " + table
					+ "; whereClause=" + whereClause + "; whereArgs ="
					+ whereArgs + ";Exception = " + e.getMessage());
			return false;
		}
		return true;
	}

	public long updateSql(String tableName, ContentValues cv,
			ContentValues wheres) {
		return updateSql(tableName, cv, wheres, false);
	}

	public long updateSql(String tableName, ContentValues cv,
			ContentValues wheres, boolean isTransaction) {
		long result = 0;
		String whereClause = "";
		Iterator<Entry<String, Object>> iter1 = wheres.valueSet().iterator();
		while (iter1.hasNext()) {
			Entry<String, Object> entry1 = iter1.next();
			whereClause += entry1.getKey() + "='" + entry1.getValue()
					+ "' and ";
		}
		whereClause = whereClause.substring(0, whereClause.length() - 5);
		if (isTransaction) {
			try {
				getDb().beginTransaction();
				result = getDb().update(tableName, cv, whereClause, null);
				getDb().setTransactionSuccessful();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				getDb().endTransaction();
			}
		} else {
			result = getDb().update(tableName, cv, whereClause, null);
		}
		return result;
	}

	/**
	 * 获取游标
	 * */
	public Cursor getCursor(String rawQuery, String[] args) {
		Cursor cursor = null;
		try {
			cursor = getDb().rawQuery(rawQuery, args);
		} catch (SQLiteMisuseException e) {
			e.printStackTrace();
		}
		return cursor;
	}


	public void closeDB() {
		if (getDb() != null && getDb().isOpen())
			try {
				getDb().close();
			} catch (Exception e) {
				Log.e(TAG, "closeDB() : " + e.getMessage());
			}
		mDb = null;
	}
	/**
	 * 关闭数据库
	 * */
	public void closeCursor(Cursor c) {
		try {
			if (c != null && !c.isClosed()) {
				c.close();
				c = null;
			}
		} catch (Exception e) {
			Log.e(TAG, "closeCursor : " + e.getMessage());
		}
	}
	/**
	 * 关闭数据库
	 * */
	public void closeCursorAndDB(Cursor c) {
		try {
			if (c != null && !c.isClosed()) {
				c.close();
				c = null;
			}
		} catch (Exception e) {
			Log.e(TAG, "closeCursorAndDB() : " + e.getMessage());
		}
		closeDB();
	}

	public void closeHelp() {
		closeDB();
		if (mDbHelper != null)
			mDbHelper.close();
	}

	/**
	 * excuteSql实体bean
	 * 
	 * @author simon
	 */
	public class SQLEntity {
		private String sql;
		private String[] params;

		public SQLEntity(String sql, String[] params) {
			super();
			this.sql = sql;
			this.params = params;
		}

		public String getSql() {
			return sql;
		}

		public void setSql(String sql) {
			this.sql = sql;
		}

		public String[] getParams() {
			return params;
		}

		public void setParams(String[] params) {
			this.params = params;
		}

	}

	public boolean tabbleIsExist(String tableName) {
		boolean result = false;
		if (tableName == null) {
			return false;
		}
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"
					+ tableName.trim() + "' ";
			cursor = getDb().rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}
			}

		} catch (Exception e) {
		}
		return result;
	}

	/*
	 * ---------peter's devide------------------
	 */
	public Cursor getCursor(String table, String[] coulums,
			Map<String, String> wheres) {
		if (tabbleIsExist(table)) {
			return getCursor(table, coulums, wheres, "");
		} else {
			return null;
		}
	}

	/**
	 * @param table
	 * @param coulums
	 * @param wheres
	 * @param extraStr
	 *            附加字段，如group by aa,limit 0,1,asc/desc
	 * @return
	 */
	public Cursor getCursor(String table, String[] coulums,
			Map<String, String> wheres, String extraStr) {
		String str = "";
		if (wheres != null) {
			str = " where ";
			for (String key : wheres.keySet()) {
				str += key + "='" + wheres.get(key) + "' and ";
			}
			str = str.substring(0, str.length() - 5);
		}
		String coulumStr = "*";
		if (coulums != null && coulums.length != 0) {
			coulumStr = Arrays.toString(coulums).replace("[", "")
					.replace("]", "");
		}
		// String coulumStr = Arrays.toString(coulums).replace("[",
		// "").replace("]", "");
		// if(TextUtils.isEmpty(coulumStr)||coulums == null)
		// coulumStr = "*";
		String sql = "select " + coulumStr + " from " + table + str + " "
				+ extraStr;
		Cursor c = null;
		try {
			c = getCursor(sql, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	public boolean updateData(String table, Map<String, String> map,
			Map<String, String> wheres) {
		String str = "";
		if (map.size() > 0) {
			for (String key : map.keySet()) {
				str += key + "='" + map.get(key) + "',";
			}
			// Iterator iter = map.entrySet().iterator();
			// while (iter.hasNext()) {
			// Map.Entry entry = (Map.Entry) iter.next();
			// str += entry.getKey() + "='" + entry.getValue() + "',";
			// }
			str = str.substring(0, str.length() - 1);
		}
		str = str + " where ";
		for (String key : wheres.keySet()) {
			str += key + "='" + wheres.get(key) + "' and ";
		}
		// Iterator iter1 = wheres.entrySet().iterator();
		// while (iter1.hasNext()) {
		// Map.Entry entry1 = (Map.Entry) iter1.next();
		// str += entry1.getKey() + "='" + entry1.getValue() + "' and ";
		// }
		str = str.substring(0, str.length() - 5);
		String sql = "update " + table + " SET " + str;
		return excuteSql(sql);
	}

	public Object selectData(String table, Map<String, String> wheres,
			Class<?> entityClass) {
		// 如果多个值，则结果是个List<Object>,单个值就是Object。Object就是class
		String[] coulums = new String[] {};
		Cursor c = getCursor(table, coulums, wheres);
		Object obj = null;
		try {
			if (c != null) {
				List<Object> list = new ArrayList<Object>();
				Object tmpObj = null;
				int count = 0;
				if (c.moveToFirst()) {
					do {
						Object entity = getEntityByCursor(entityClass, c);
						list.add(entity);
						count++;
						tmpObj = entity;
					} while (c.moveToNext());
				}
				if (count > 1) {
					obj = list;
				} else {
					obj = tmpObj;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeCursor(c);
		}
		return obj;
	}

	private Object getEntityByCursor(Class<?> entityClass, Cursor c) {
		Object entity = null;
		try {
			entity = entityClass.newInstance();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		}
		Field[] fields = entity.getClass().getDeclaredFields();
		Field.setAccessible(fields, true);
		for (Field field : fields) {
			String colum = field.getName();
			try {
				int columnIdx = c.getColumnIndex(colum);
				if (columnIdx == -1)
					continue;
				Class<?> type = field.getType();
				if (type == byte.class) {
					field.set(entity, (byte) c.getShort(columnIdx));
				} else if (type == short.class) {
					field.set(entity, c.getShort(columnIdx));
				} else if (type == int.class) {
					field.set(entity, c.getInt(columnIdx));
				} else if (type == long.class) {
					field.set(entity, c.getLong(columnIdx));
				} else if (type == String.class) {
					field.set(entity, c.getString(columnIdx));
				} else if (type == byte[].class) {
					field.set(entity, c.getBlob(columnIdx));
				} else if (type == boolean.class) {
					field.set(entity, c.getInt(columnIdx) == 1);
				} else if (type == float.class) {
					field.set(entity, c.getFloat(columnIdx));
				} else if (type == double.class) {
					field.set(entity, c.getDouble(columnIdx));
				}

			} catch (IllegalArgumentException e) {
				Log.e(TAG, "UserBehavior: Exception = " + e.toString());
			} catch (IllegalAccessException e) {
				Log.e(TAG, "UserBehavior: Exception = " + e.toString());
			}
		}
		return entity;
	}

	public Object selectSingleData(String table, Map<String, String> wheres,
			Class<?> entityClass) {
		String[] coulums = new String[] {};
		Cursor c = getCursor(table, coulums, wheres);
		Object entity = null;
		try {
			if (c != null) {
				if (c.moveToFirst()) {
					do {
						try {
							entity = entityClass.newInstance();
						} catch (IllegalAccessException e1) {
							e1.printStackTrace();
						} catch (InstantiationException e1) {
							e1.printStackTrace();
						}
						Field[] fields = entity.getClass().getDeclaredFields();
						Field.setAccessible(fields, true);
						for (Field field : fields) {
							String colum = field.getName();
							try {
								int columnIdx = c.getColumnIndex(colum);
								if (columnIdx == -1)
									continue;
								Class<?> type = field.getType();
								if (type == byte.class) {
									field.set(entity,
											(byte) c.getShort(columnIdx));
								} else if (type == short.class) {
									field.set(entity, c.getShort(columnIdx));
								} else if (type == int.class) {
									field.set(entity, c.getInt(columnIdx));
								} else if (type == long.class) {
									field.set(entity, c.getLong(columnIdx));
								} else if (type == String.class) {
									field.set(entity, c.getString(columnIdx));
								} else if (type == byte[].class) {
									field.set(entity, c.getBlob(columnIdx));
								} else if (type == boolean.class) {
									field.set(entity, c.getInt(columnIdx) == 1);
								} else if (type == float.class) {
									field.set(entity, c.getFloat(columnIdx));
								} else if (type == double.class) {
									field.set(entity, c.getDouble(columnIdx));
								}

							} catch (IllegalArgumentException e) {
								Log.e(TAG, "UserBehavior: Exception = "
										+ e.toString());
							} catch (IllegalAccessException e) {
								Log.e(TAG, "UserBehavior: Exception = "
										+ e.toString());
							}
						}
					} while (c.moveToNext());
				}
			}
		} finally {
			closeCursor(c);
		}
		return entity;
	}

	public List<Object> selectDatatmp(String table, Map<String, String> wheres,
			Class entityClass) {
		String[] coulums = new String[] {};
		Cursor c = getCursor(table, coulums, wheres);
		List<Object> list = new ArrayList<Object>();
		try {
			if (c != null) {
				if (c.moveToFirst()) {
					do {
						Object entity = null;
						try {
							entity = entityClass.newInstance();
						} catch (IllegalAccessException e1) {
							e1.printStackTrace();
						} catch (InstantiationException e1) {
							e1.printStackTrace();
						}
						Field[] fields = entity.getClass().getDeclaredFields();
						Field.setAccessible(fields, true);
						for (Field field : fields) {
							String colum = field.getName();
							try {
								int columnIdx = c.getColumnIndex(colum);
								if (columnIdx == -1)
									continue;
								Class<?> type = field.getType();
								if (type == byte.class) {
									field.set(entity,
											(byte) c.getShort(columnIdx));
								} else if (type == short.class) {
									field.set(entity, c.getShort(columnIdx));
								} else if (type == int.class) {
									field.set(entity, c.getInt(columnIdx));
								} else if (type == long.class) {
									field.set(entity, c.getLong(columnIdx));
								} else if (type == String.class) {
									field.set(entity, c.getString(columnIdx));
								} else if (type == byte[].class) {
									field.set(entity, c.getBlob(columnIdx));
								} else if (type == boolean.class) {
									field.set(entity, c.getInt(columnIdx) == 1);
								} else if (type == float.class) {
									field.set(entity, c.getFloat(columnIdx));
								} else if (type == double.class) {
									field.set(entity, c.getDouble(columnIdx));
								}

							} catch (IllegalArgumentException e) {
								Log.e(TAG, "UserBehavior: Exception = "
										+ e.toString());
							} catch (IllegalAccessException e) {
								Log.e(TAG, "UserBehavior: Exception = "
										+ e.toString());
							}
						}
						list.add(entity);
					} while (c.moveToNext());
				}
			}
		} finally {
			closeCursor(c);
		}
		return list;
	}

	/**
	 * 查找单条记录
	 * 
	 * @param table
	 * @param coulums
	 * @param wheres
	 * @return
	 */
	public Object selectSingleData(String table, String[] coulums,
			Map<String, String> wheres) {
		Cursor c = getCursor(table, coulums, wheres);
		Object result = "";
		if (c != null) {
			if (c.moveToFirst()) {
				do {
					for (String str : coulums) {
						if (c.getColumnIndex(str) == -1)
							continue;
						if ("id".equals(str)) {
							result = c.getInt(c.getColumnIndex(str));
							break;
						}
						result = c.getString(c.getColumnIndex(str));
					}
				} while (c.moveToNext());

			}
		}
		closeCursor(c);
		return result;
	}

	/**
	 * 查找单条记录的多个字段练成字符串
	 * 
	 * @param table
	 * @param coulums
	 * @param wheres
	 * @return
	 */
	public String selectSingleString(String table, String[] coulums,
			Map<String, String> wheres) {
		Cursor c = getCursor(table, coulums, wheres);
		String result = "";
		if (c != null) {
			if (c.moveToFirst()) {
				do {
					for (String str : coulums) {
						if (c.getColumnIndex(str) == -1)
							continue;
						result += c.getString(c.getColumnIndex(str));
					}
				} while (c.moveToNext());

			}
		}
		closeCursor(c);
		return result;
	}

	/**
	 * 是否在数据表中存在
	 * 
	 * @param table
	 * @param coulums
	 * @param wheres
	 * @return
	 */
	public boolean isExist(String table, Map<String, String> wheres) {
		Cursor c = getCursor(table, null, wheres);
		if (c != null) {
			if (c.moveToFirst()) {
				closeCursor(c);
				return true;
			}
		}
		closeCursor(c);
		return false;
	}

	public List<?> selectList(String table, String[] coulums,
			Map<String, String> wheres, String extraStr) {
		Cursor c = getCursor(table, coulums, wheres, extraStr);
		String result = "";
		List<Map<String, String>> list = null;
		if (c != null) {
			list = new ArrayList<Map<String, String>>();
			if (c.moveToFirst()) {
				int columnCount = c.getColumnCount();
				do {
					Map<String, String> map = new HashMap<String, String>();
					if (coulums != null) {
						for (String str : coulums) {
							if (c.getColumnIndex(str) == -1)
								continue;
							result = c.getString(c.getColumnIndex(str));
							map.put(str, result);
						}
					} else {
						for (int i = 0; i < columnCount; i++) {
							String str = c.getColumnName(i);
							result = c.getString(0);
							map.put(str, result);
						}
					}
					list.add(map);
				} while (c.moveToNext());
			}
		}
		closeCursor(c);
		return list;
	}

	public Object selectMaps(String table, String[] coulums,
			Map<String, String> wheres, String extraStr) {
		Cursor c = getCursor(table, coulums, wheres, extraStr);
		String result = "";
		Object obj = null;
		if (c != null) {
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			Map<String, String> tmpObj = null;
			int count = 0;
			if (c.moveToFirst()) {
				do {
					Map<String, String> map = new HashMap<String, String>();
					for (String str : coulums) {
						if (c.getColumnIndex(str) == -1)
							continue;
						result = c.getString(c.getColumnIndex(str));
						map.put(str, result);
					}
					list.add(map);
					count++;
					if (count == 1)
						tmpObj = map;
				} while (c.moveToNext());
			}
			if (count > 1) {
				obj = list;
			} else {
				obj = tmpObj;
			}
		}
		closeCursor(c);
		return obj;
	}

	public List<Map<String, String>> selectList(String sql) {
		Cursor c = null;
		try {
			c = getCursor(sql, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = "";
		List<Map<String, String>> list = null;
		if (c != null) {
			list = new ArrayList<Map<String, String>>();
			if (c.moveToFirst()) {
				int columnCount = c.getColumnCount();
				do {
					Map<String, String> map = new HashMap<String, String>();
					for (int i = 0; i < columnCount; i++) {
						String str = c.getColumnName(i);
						result = c.getString(0);
						map.put(str, result);
					}
					list.add(map);
				} while (c.moveToNext());
			}
		}
		return list;
	}

	public void clearTable(String table) {
		String sql = "DELETE FROM " + table;
		excuteSql(sql);
	}

	public void clearAllTable() {
		String sql = "SELECT name FROM sqlite_master WHERE type='table' ORDER BY name;";
		List<Map<String, String>> list = selectList(sql);
		String delSql = "";
		for (Map<String, String> map : list) {
			delSql += "DELETE FROM " + map.get("name") + ";";
		}
		excuteSql(delSql);
	}

	public void dropTable(String table) {
		String sql = "DROP TABLE " + table;
		excuteSql(sql);
	}

	public void dropAllTable() {
		String sql = "SELECT name FROM sqlite_master WHERE type='table' ORDER BY name;";
		List<Map<String, String>> list = selectList(sql);
		String delSql = "";
		for (Map<String, String> map : list) {
			delSql += "DROP TABLE " + map.get("name") + ";";
		}
		excuteSql(delSql);
	}
}
