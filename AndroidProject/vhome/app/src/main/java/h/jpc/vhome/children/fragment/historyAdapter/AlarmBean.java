package h.jpc.vhome.children.fragment.historyAdapter;

public class AlarmBean {
    private String alarmId;
    private String alarmTime;
    private String sendPerson;
    private String receivePerson;
    private String content;

    public AlarmBean() { }
    public AlarmBean(String alarmId, String alarmTime, String sendPerson, String receivePerson, String content) {
        this.alarmId = alarmId;
        this.alarmTime = alarmTime;
        this.sendPerson = sendPerson;
        this.receivePerson = receivePerson;
        this.content = content;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getSendPerson() {
        return sendPerson;
    }

    public void setSendPerson(String sendPerson) {
        this.sendPerson = sendPerson;
    }

    public String getReceivePerson() {
        return receivePerson;
    }

    public void setReceivePerson(String receivePerson) {
        this.receivePerson = receivePerson;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "AlarmBean{" +
                "alarmId='" + alarmId + '\'' +
                ", alarmTime='" + alarmTime + '\'' +
                ", sendPerson='" + sendPerson + '\'' +
                ", receivePerson='" + receivePerson + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
