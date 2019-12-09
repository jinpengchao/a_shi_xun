package alarm.service;

import alarm.dao.AlarmDao;

public class AlarmService {
	public void insertNewSendAlarm(int alarmId, String alarmTime, String sendPersonId, String receivePersonId, String content) {
		AlarmDao alarmDao = new AlarmDao();
		alarmDao.sendNewAlarm(alarmId, alarmTime, sendPersonId, receivePersonId, content);
	}
}