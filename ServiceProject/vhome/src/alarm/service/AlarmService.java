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
	//插入常用闹钟列表
	public void insertNormalAlarm(String content,String phone) {
		AlarmDao alarmDao = new AlarmDao();
		alarmDao.saveNormalAlarm(content,phone);
	}
	//读取所有闹钟
	public List<AlarmBean> selectAllAlarm(String phone){
		AlarmDao alarmDao = new AlarmDao();
		return alarmDao.allAlarm(phone);
	}
	//读取我的常用列表
	public List<String> selectMyNormalAlarm(String phone){
		AlarmDao alarmDao = new AlarmDao();
		return alarmDao.queryNormalAlarm(phone);
	}
	//读取我发送的闹钟
	public List<AlarmBean> selectMySendedAlarm(String phone){
		AlarmDao alarmDao = new AlarmDao();
		return alarmDao.mySendedAlarm(phone);
	}
	//删除我发送的闹钟
	public void deleteSendedAlarm(String content ) {
		AlarmDao alarmDao = new AlarmDao();
		alarmDao.removeSendedAlarm(content);
	}
	//删除我发送的闹钟
		public void deleteNormalAlarm(String content ) {
			AlarmDao alarmDao = new AlarmDao();
			alarmDao.removeNormalAlarm(content);
		}
	//修改闹钟信息
	public void updateAlarmInfo(int alarmId,String time,String content,int clocktype) {
		AlarmDao alarmDao = new AlarmDao();
		alarmDao.chandeAlarm(alarmId, time, content,clocktype);
	}
}