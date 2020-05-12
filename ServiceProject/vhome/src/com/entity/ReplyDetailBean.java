/**
 * @Title:ReplyDetailBean.java
 * @Packageentity
 * @Description: TODO
 * @auther wzw
 * @date 2019年12月9日
 * @version v1.0
 */
package com.entity;

/**
 * @ClassName: ReplyDetailBean
 * @Description: TODO
 * @author wzw
 * @date 2019年12月9日
 *
 */
public class ReplyDetailBean {
	private int id;
    private int commentId;
    private String nickName;
    private String headimg;
    private String personId;
    private int replyTotal;
    private String content;
    private String time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public int getReplyTotal() {
		return replyTotal;
	}
	public void setReplyTotal(int replyTotal) {
		this.replyTotal = replyTotal;
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
