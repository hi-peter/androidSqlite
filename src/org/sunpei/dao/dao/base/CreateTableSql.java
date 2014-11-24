package org.sunpei.dao.dao.base;

import org.sunpei.dao.CustomerInfo;

/**
 * this is the sqls's class,store every create table sql for you want
 * @author peter.sun 
 * if you have question ,please send to hisp@163.com
 * 
 */
public class CreateTableSql {

	public final static String TABLE_USERINFO_SQL = "create table IF NOT EXISTS "+CustomerInfo.class.getSimpleName()+"("+
			"custId TEXT," +
			"nickName TEXT," +
			"avatarUrl TEXT," +
			"clientNo TEXT," + 
			"partyNo TEXT," +
			"alias TEXT," +
			"name TEXT," +
			"sex TEXT," + 
			"birthDate TEXT," +
			"idType TEXT," + 
			"idNo TEXT," + 
			"address TEXT," + 
			"nation TEXT," + 
			"issue TEXT," + 
			"expire TEXT," +  
			"isPaCustomer TEXT," + 
			"mobileNo TEXT," + 
			"email TEXT," +
			"isFundCustomer TEXT," +
			"isFundRelate TEXT," +
			"imgId TEXT," +
			"CONSTRAINT 'custId' UNIQUE('custId' ASC) ON CONFLICT REPLACE"+ 
			");";
	public final static String TABLE_TREASURE_SQL = "create table IF NOT EXISTS treasure("+
			"userId TEXT," +
			"treasure TEXT," +
			"CONSTRAINT 'userId' UNIQUE('userId' ASC) ON CONFLICT REPLACE"+ 
			");";
	public final static String TABLE_ALARM_SQL = "CREATE TABLE IF NOT EXISTS ALARM ("+
			"id VARCHAR(20)," +
			"userId TEXT," +
			"timeStr TEXT," +
			"title TEXT," +
			"content TEXT," +
			"action TEXT," +
			"targetActivity TEXT," +
			"isRepeatAlarm Text,"+
			"class Text,"+
			"CONSTRAINT 'action' UNIQUE('action' ASC) ON CONFLICT REPLACE)";
	// 网络开关项
	public final static String TABLE_SWITCH_SQL = "CREATE TABLE IF NOT EXISTS SWITCH ("+
			"name TEXT," +
			"state TEXT," +
			"url TEXT," +
			"CONSTRAINT 'name' UNIQUE('name' ASC) ON CONFLICT REPLACE)";
}
