/**
 * @Title:CollectionBean.java
 * @Packageentity
 * @Description: TODO
 * @auther wzw
 * @date 2019年12月7日
 * @version v1.0
 */
package entity;

/**
 * @ClassName: CollectionBean
 * @Description: TODO
 * @author wzw
 * @date 2019年12月7日
 *
 */
public class CollectionBean {

	private int id;
	private String personId;
	private int postId;
	private String time;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}
