package entity;

public class PostExamineBean {

		private int id;
	    private String nickName;
	    private String headimg;
	    private String postContent;
	    private String personId;
	    private String phone;
	    private String time;
	    private String imgs;
	    private String examine;
	    private int commentNum;
	    private int likeNum;
	    private int save_status = 0;//标志位判断当前用户是否收藏过本帖
	    private int like_status = 0;//标志位判断当前用户是否点赞过本帖
	    private int attention_status = 0;//标志位判断当前用户是否关注发帖人
	    private String rId;
	    
	    
		
		
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getrId() {
			return rId;
		}
		public void setrId(String rId) {
			this.rId = rId;
		}
		public String getExamine() {
			return examine;
		}
		public void setExamine(String examine) {
			this.examine = examine;
		}
		public String getPostContent() {
			return postContent;
		}
		public void setPostContent(String postContent) {
			this.postContent = postContent;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
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
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getImgs() {
			return imgs;
		}
		public void setImgs(String imgs) {
			this.imgs = imgs;
		}
		public int getCommentNum() {
			return commentNum;
		}
		public void setCommentNum(int commentNum) {
			this.commentNum = commentNum;
		}
		public int getLikeNum() {
			return likeNum;
		}
		public void setLikeNum(int likeNum) {
			this.likeNum = likeNum;
		}
		public int getSave_status() {
			return save_status;
		}
		public void setSave_status(int save_status) {
			this.save_status = save_status;
		}
		public int getLike_status() {
			return like_status;
		}
		public void setLike_status(int like_status) {
			this.like_status = like_status;
		}
		public int getAttention_status() {
			return attention_status;
		}
		public void setAttention_status(int attention_status) {
			this.attention_status = attention_status;
		}
		@Override
		public String toString() {
			return "PostExamineBean [id=" + id + ", nickName=" + nickName + ", headimg=" + headimg + ", postContent="
					+ postContent + ", personId=" + personId + ", time=" + time + ", imgs=" + imgs + ", examineString="
					+ examine + ", commentNum=" + commentNum + ", likeNum=" + likeNum + ", save_status="
					+ save_status + ", like_status=" + like_status + ", attention_status=" + attention_status + "]";
		}
	
		
		

}
