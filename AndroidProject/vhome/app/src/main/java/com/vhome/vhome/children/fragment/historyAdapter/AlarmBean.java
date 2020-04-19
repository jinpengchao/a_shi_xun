package com.vhome.vhome.children.fragment.historyAdapter;

public class AlarmBean {
    private int alarmId;
    private String alarmTime;
    private String sendPersonId;
    private String receivePersonId;
    private String content;
    private int clocktype;

    public AlarmBean() { }
    public AlarmBean(int alarmId, String alarmTime, String sendPersonId, String receivePersonId, String content,int clocktype) {
        this.alarmId = alarmId;
        this.alarmTime = alarmTime;
        this.sendPersonId = sendPersonId;
        this.receivePersonId = receivePersonId;
        this.content = content;
        this.clocktype = clocktype;
    }

    public int getClocktype() {
        return clocktype;
    }

    public void setClocktype(int clocktype) {
        this.clocktype = clocktype;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getSendPersonId() {
        return sendPersonId;
    }

    public void setSendPersonId(String sendPersonId) {
        this.sendPersonId = sendPersonId;
    }

    public String getReceivePersonId() {
        return receivePersonId;
    }

    public void setReceivePersonId(String receivePersonId) {
        this.receivePersonId = receivePersonId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
