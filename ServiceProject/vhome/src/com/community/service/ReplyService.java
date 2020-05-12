/**
 * @Title:ReplyService.java
 * @Packagecommunity.service
 * @Description: TODO
 * @auther wzw
 * @date 2019年12月10日
 * @version v1.0
 */
package com.community.service;

import java.util.List;

import com.community.dao.ReplyDao;
import com.entity.ReplyDetailBean;


/**
 * @ClassName: ReplyService
 * @Description: TODO
 * @author wzw
 * @date 2019年12月10日
 *
 */
public class ReplyService {
	//通过id修改头像
		public int changeLogo(String logo,String personId) {
			return (new ReplyDao()).changeLogo(logo, personId);
		}
	//通过id修改昵称
	public int changeName(String nickName,String personId) {
		return (new ReplyDao()).changeName(nickName, personId);
	}

	public int saveReply(ReplyDetailBean reply) {
		return (new ReplyDao()).insertReply(reply);
	}
	
	public List<ReplyDetailBean> findReply(int commentId){
		return (new ReplyDao()).queryReply(commentId);
	} 
	
	public int getReplyCount(int commentId) {
		return (new ReplyDao()).queryReplyCount(commentId);
	}
	
	public int delReply(int id) {
		return (new ReplyDao()).delReply(id);
	}
	//通过评论id删除回复
	public int delCommentReply(int commentId) {
		return (new ReplyDao()).delCommentReply(commentId);
	}
}
