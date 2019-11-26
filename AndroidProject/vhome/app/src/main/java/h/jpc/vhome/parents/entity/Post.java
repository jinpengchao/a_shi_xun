package h.jpc.vhome.parents.entity;

public class Post {
    private int id;
    private String postContent;
    private String postSendPersonId;
    private String postTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
