package alarm.service;

import java.util.List;

import alarm.dao.AlarmDao;
import entity.AlarmBean;

public class AlarmService {
	//插入新闹钟
	public void insertNewSendAlarm(int alarmId, String alarmTime, String sendPersonId, String receivePersonId, String content) {
		AlarmDao alarmDao = new AlarmDao();
		alarmDao.sendNewAlarm(alarmId, alarmTime, sendPersonId, receivePersonId, content);
	}
	//读取所有闹钟
	public List<AlarmBean> selectAllAlarm(String phone){
		AlarmDao alarmDao = new AlarmDao();
		return alarmDao.allAlarm(phone);
	}
}