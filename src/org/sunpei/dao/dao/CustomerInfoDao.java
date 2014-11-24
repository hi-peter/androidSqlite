package org.sunpei.dao.dao;

import java.util.HashMap;
import java.util.List;

import org.sunpei.dao.CustomerInfo;
import org.sunpei.dao.dao.base.BaseSqlDao;

import android.content.ContentValues;
import android.util.Log;
/**
 * this is the dao of operation
 * @author peter.sun 
 * if you have question ,please send to hisp@163.com
 * 
 */
public class CustomerInfoDao {

	private BaseSqlDao baseDao;
	public CustomerInfoDao() {
		baseDao = BaseSqlDao.getInstance();
	}

	/**insert the bean to db
	 * @param customerInfo
	 * @return
	 */
	public long insertBean(CustomerInfo customerInfo) {
		long result = 0;
		Log.i("start ", "start insert");
		try {
			result = baseDao.insertData(CustomerInfo.class.getSimpleName(), customerInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**update bean
	 * @param values
	 * @param wheres
	 * @return
	 */
	public long updateBean(ContentValues values, ContentValues wheres){
		long result = 0;
		result = baseDao.updateSql(CustomerInfo.class.getSimpleName(), values, wheres);
		return result;
	}

	
	/**
	 * select data from db by custId
	 * @param custId
	 * @return
	 */
	public List queryCustomer(String custId){
		Log.i("start query", "start query");
		List info = null;
		HashMap<String, String> wheres = new HashMap<String, String>();
		wheres.put("custId", custId);
		info = baseDao.selectList(CustomerInfo.class.getSimpleName(), null, wheres, "");
		return info;
	}
	/**
	 * clear all the data of the table
	 */
	public void clearAllTables(){
		baseDao.clearAllTable();
	}
}
