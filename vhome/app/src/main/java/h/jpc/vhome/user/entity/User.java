package h.jpc.vhome.user.entity;

public class User {
    private String phone;
    private String password;
    private String registerTime;
    private String id;
    private String wechat;
    private String qq;
    int type;
    public User(){}

    public User(String phone, String password, String registerTime, String id, String wechat, String qq, int type){
        this.phone = phone;
        this.password = password;
        this.registerTime = registerTime;
        this.id = id;
        this.wechat = wechat;
        this.qq = qq;
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRegisterTime() {
        return registerTime;
    }
    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getWechat() {
        return wechat;
    }
    public void setWechat(String wechat) {
        this.wechat = wechat;
    }
    public String getQq() {
        return qq;
    }
    public void setQq(String qq) {
        this.qq = qq;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return "User [phone=" + phone + ", password=" + password + ", registerTime=" + registerTime + ", id=" + id
                + ", wechat=" + wechat + ", qq=" + qq + ", type=" + type + "]";
    }
}
