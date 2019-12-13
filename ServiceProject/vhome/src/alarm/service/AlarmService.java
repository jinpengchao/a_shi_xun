package alarm.service;

import java.util.List;

import alarm.dao.AlarmDao;
import entity.AlarmBean;

public class AlarmService {
	//插入新闹钟
	public void insertNewSendAlarm(int alarmId, String alarmTime, String sendPersonId, String receivePersonId, String content,int clocktype) {
		AlarmDao alarmDao = new AlarmDao();
		alarmDao.sendNewAlarm(alarmId, alarmTime, sendPersonId, receivePersonId, content, clocktype);
	}
	//读取所有闹钟
	public List<AlarmBean> selectAllAlarm(String phone){
		AlarmDao alarmDao = new AlarmDao();
		return alarmDao.allAlarm(phone);
	}
	//修改闹钟信息
	public void updateAlarmInfo(int alarmId,String time,String content,int clocktype) {
		AlarmDao alarmDao = new AlarmDao();
		alarmDao.chandeAlarm(alarmId, time, content,clocktype);
	}
	//读取所有闹钟
	public List<AlarmBean> selectAllAlarm(String phone){
		AlarmDao alarmDao = new AlarmDao();
		return alarmDao.allAlarm(phone);
	}
}