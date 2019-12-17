/**
 * @Title:CommentService.java
 * @Packagecommunity.service
 * @Description: TODO
 * @auther wzw
 * @date 2019年11月27日
 * @version v1.0
 */
package community.service;

import java.util.List;

import community.dao.CommentDao;
import entity.CommentDetailBean;

/**
 * @ClassName: CommentService
 * @Description: TODO
 * @author wzw
 * @date 2019年11月27日
 *
 */
public class CommentService {
	//通过id修改头像logo
	public int changeImg(String logo,String personId) {
		return (new CommentDao()).changeImg(logo, personId);
	}
	//通过id修改昵称
	public int changeName(String nickName,String personId) {
		return (new CommentDao()).changeName(nickName, personId);
	}

	/**
	 * 
	 *  @title:saveComment
	 * @Description: 保存
	 * @throws下午9:12:15
	 * returntype:int
	 */
	public int saveComment(CommentDetailBean comment) {
		return (new CommentDao()).insertComment(comment);
	}
	
	/**
	 * 
	 *  @title:findCommet
	 * @Description: 查询
	 * @throws下午9:13:11
	 * returntype:List<Comment>
	 */
	public List<CommentDetailBean> findComment(int postId){
		return (new CommentDao()).queryComment(postId);
	}
	/**
	 * 根据postId查询
	 *  @title:findCommentCount
	 * @Description: todo
	 * @throws下午9:49:37
	 * returntype:int
	 */
	public int findCommentCount(int postId) {
		return (new CommentDao()).queryCommentCount(postId);
	}
	//通过评论人id查找评论ID
	public List<Integer> findComment(String personId){
		return (new CommentDao()).queryComment(personId);
	}
	/**
	 * 
	 *  @title:delComment
	 * @Description:删除
	 * @throws下午9:14:21
	 * returntype:int
	 */
	public int delComment(int id) {
		return (new CommentDao()).delComment(id);
	}
}
