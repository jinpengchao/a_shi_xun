package com.vhome.vhome.parents.fragment.community_hotspot.entity;

import java.util.List;

public class CommentDetailBean {
    private int id;
    private int postId;
    private String personId;
    private String nickName;
    private String headimg;
    private String content;
    private String time;
    private List<ReplyDetailBean> replyList;

    public CommentDetailBean() {

    }

    public CommentDetailBean(int id, int postId, String personId, String nickName, String headimg, String content, String time, List<ReplyDetailBean> replyList) {
        this.id = id;
        this.postId = postId;
        this.personId = personId;
        this.nickName = nickName;
        this.headimg = headimg;
        this.content = content;
        this.time = time;
        this.replyList = replyList;
    }

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

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
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

    public List<ReplyDetailBean> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<ReplyDetailBean> replyList) {
        this.replyList = replyList;
    }
}
