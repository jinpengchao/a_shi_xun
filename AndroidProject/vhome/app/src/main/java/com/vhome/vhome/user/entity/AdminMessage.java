package com.vhome.vhome.user.entity;

public class AdminMessage {
    private int id;
	private String content;
	private int postId;
	private String phone;
	private String personId;
    private int readable;
    private String content_answer;

    public String getContent_answer() {
        return content_answer;
    }

    public void setContent_answer(String content_answer) {
        this.content_answer = content_answer;
    }
    public int getReadable() {
        return readable;
    }
    public void setReadable(int readable) {
        this.readable = readable;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
