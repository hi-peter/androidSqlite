package org.sunpei.dao;

import org.sunpei.dao.dao.CustomerInfoDao;
import org.sunpei.dao.dao.base.InitDao;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//init db operation
		InitDao af =InitDao.init("abc", getApplicationContext());
		Class[] beanArr = {CustomerInfo.class};
		//create table
		af.getInnerDBHelper(beanArr);
		
		findViewById(R.id.clickBt).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				test();
				
			}
		});
	}
	private void test() {
		CustomerInfoDao dao = new CustomerInfoDao();
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setAvatarUrl("aaaaaaaaaa");
		customerInfo.setCustId("1666");
		for(int i=0;i<10;i++)
			dao.insertBean(customerInfo);
	}
	private void test1() {
		CustomerInfoDao dao = new CustomerInfoDao();
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setAvatarUrl("aaaaaaaaaa");
		customerInfo.setCustId("1666");
		for(int i=0;i<10;i++)
			dao.insertBean(customerInfo);
		
		new Thread(){
			public void run(){
				Log.i("xian1", Thread.currentThread().getName());
				CustomerInfoDao dao = new CustomerInfoDao();
				try {
					CustomerInfo customerInfo = new CustomerInfo();
					customerInfo.setAvatarUrl("aaaaaaaaaa");
					customerInfo.setCustId("1666");
					for(int i=0;i<100000;i++)
						dao.insertBean(customerInfo);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}.start();
		new Thread(){
			public void run(){
				Log.i("xian2", Thread.currentThread().getName());
				CustomerInfoDao dao = new CustomerInfoDao();
				try {
					CustomerInfo customerInfo = new CustomerInfo();
					customerInfo.setAvatarUrl("yyyyyyy");
					customerInfo.setCustId("1666");
					for(int i=0;i<100000;i++)
						dao.insertBean(customerInfo);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}.start();
		new Thread(){
			public void run(){
				Log.i("xian3", Thread.currentThread().getName());
				CustomerInfoDao dao = new CustomerInfoDao();
				for (int i = 0; i < 100000; i++) {
					dao.queryCustomer("1666");
				}
			}
		}.start();
	}

}
