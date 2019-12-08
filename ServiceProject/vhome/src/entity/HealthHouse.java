/**
 * @Title:HealthHouse.java
 * @Packageentity
 * @Description: TODO
 * @auther wzw
 * @date 2019年11月26日
 * @version v1.0
 */
package entity;

/**
 * @ClassName: HealthHouse
 * @Description: TODO
 * @author wzw
 * @date 2019年11月26日
 *
 */
public class HealthHouse {
	private int id;
	private String publishTime;
	private String title;
	private String resource;
	private String viewImg;
	private String address;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getViewImg() {
		return viewImg;
	}
	public void setViewImg(String viewImg) {
		this.viewImg = viewImg;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
