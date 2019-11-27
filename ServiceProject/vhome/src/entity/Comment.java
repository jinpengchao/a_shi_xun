/**
 * @Title:Comment.java
 * @Packageentity
 * @Description: TODO
 * @auther wzw
 * @date 2019年11月26日
 * @version v1.0
 */
package entity;

/**
 * @ClassName: Comment
 * @Description: TODO
 * @author wzw
 * @date 2019年11月26日
 *
 */
public class Comment {

	private String id;
	private String postId;
	private String personId;
	private String content;
	private String time;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
