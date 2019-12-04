package h.jpc.vhome.parents.fragment.community_hotspot.entity;

import java.util.List;

public class CommentDetailBean {
    private int id;
    private int postId;
    private String personId;
    private String nickName;
    private String userLogo;
    private String content;
    private String time;
    private List<ReplyDetailBean> replyList;

    public CommentDetailBean() {

    }

    public CommentDetailBean(int id, int postId, String personId, String nickName, String userLogo, String content, String time) {
        this.id = id;
        this.postId = postId;
        this.personId = personId;
        this.nickName = nickName;
        this.userLogo = userLogo;
        this.content = content;
        this.time = time;
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

    public String getUserLogo() {
        return userLogo;
    }

    public void setUserLogo(String userLogo) {
        this.userLogo = userLogo;
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
