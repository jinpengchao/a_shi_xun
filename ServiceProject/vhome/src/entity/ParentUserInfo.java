package entity;

public class ParentUserInfo {
	private String phone;
	private String id;
	private String nikeName;
	private String sex;
	private String area;
	private String birthday;
	private String acieve;
	private String imei;
	private String status;
	private String personalWord;
	private String headerImg;
	private int type;
	private int closeDays;
	
	
	
	
	public int getCloseDays() {
		return closeDays;
	}
	public void setCloseDays(int closeDays) {
		this.closeDays = closeDays;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getAcieve() {
		return acieve;
	}
	public void setAcieve(String acieve) {
		this.acieve = acieve;
	}
	public String getPersonalWord() {
		return personalWord;
	}
	public void setPersonalWord(String personalWord) {
		this.personalWord = personalWord;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNikeName() {
		return nikeName;
	}
	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
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
	public String getHeaderImg() {
		return headerImg;
	}
	public void setHeaderImg(String headerImg) {
		this.headerImg = headerImg;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "ParentUserInfo [phone=" + phone + ", id=" + id + ", nikeName=" + nikeName + ", sex=" + sex + ", area="
				+ area + ", birthday=" + birthday + ", acieve=" + acieve + ", imei=" + imei + ", status=" + status
				+ ", personalWord=" + personalWord + ", headerImg=" + headerImg + ", type=" + type + ", closeDays="
				+ closeDays + "]";
	}
	
}
