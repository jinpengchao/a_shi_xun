package entity;

public class Post {

	private int postId;
	private String postContent;
	private String postSendPersonId;
	private String postTime;
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public String getPostSendPersonId() {
		return postSendPersonId;
	}
	public void setPostSendPersonId(String postSendPersonId) {
		this.postSendPersonId = postSendPersonId;
	}
	public String getPostTime() {
		return postTime;
	}
	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}
	
	
}
