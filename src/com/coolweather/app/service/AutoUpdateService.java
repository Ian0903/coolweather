package com.coolweather.app.service;

import com.coolweather.app.receiver.AutoUpdateReceiver;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

public class AutoUpdateService extends Service{
	
	public IBinder onBind(Intent intent){
		return null;
	}
	
	public int onStartCommand(Intent intent,int flags,int startId){
		new Thread(new Runnable(){
			public void run(){
				updateWeather();
			}
		}).start();
		AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
		int anHour = 8 * 60 * 60 * 1000;//����8Сʱ
		long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
		Intent i = new Intent(this,AutoUpdateReceiver.class);
		PendingIntent p = PendingIntent.getBroadcast(this,0,i,0);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, p);
		return super.onStartCommand(intent, flags, startId);
	}
	
	/**
	 * ����������Ϣ
	 */
	private void updateWeather(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String weatherCode = prefs.getString("weather_code","");
		String address = "http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener(){
			public void onFinish(String response){
				Utility.handleWeatherResponse(AutoUpdateService.this, response);
				
			}
			
			public void onError(Exception e){
				e.printStackTrace();
			}
		});
	}

}