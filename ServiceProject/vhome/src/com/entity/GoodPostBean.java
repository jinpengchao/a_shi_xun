/**
 * @Title:GoodPostBean.java
 * @Packageentity
 * @Description: TODO
 * @auther wzw
 * @date 2019年12月6日
 * @version v1.0
 */
package com.entity;

/**
 * @ClassName: GoodPostBean
 * @Description: TODO
 * @author wzw
 * @date 2019年12月6日
 *
 */
public class GoodPostBean {
	private int id;
	private int postId;
	private String goodPersonId;
	private String publishPersonId;
	private String time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getGoodPersonId() {
		return goodPersonId;
	}
	public void setGoodPersonId(String goodPersonId) {
		this.goodPersonId = goodPersonId;
	}
	public String getPublishPersonId() {
		return publishPersonId;
	}
	public void setPublishPersonId(String publishPersonId) {
		this.publishPersonId = publishPersonId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}
