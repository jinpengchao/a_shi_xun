/**
 * @Title:AttentionBean.java
 * @Packageentity
 * @Description: TODO
 * @auther wzw
 * @date 2019年12月11日
 * @version v1.0
 */
package com.entity;

/**
 * @ClassName: AttentionBean
 * @Description: TODO
 * @author wzw
 * @date 2019年12月11日
 *
 */
public class AttentionBean {

	private int id;
	private String attentionPersonId;
	private String personId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAttentionPersonId() {
		return attentionPersonId;
	}
	public void setAttentionPersonId(String attentionPersonId) {
		this.attentionPersonId = attentionPersonId;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
}
