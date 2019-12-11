package h.jpc.vhome.user.entity;

public class EventBean {
    private String nickName;
    private String sex;
    private String area;

    public EventBean(){

    }
    public EventBean(String nickName,String sex,String area) {
        this.nickName = nickName;
        this.sex = sex;
        this.area = area;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
