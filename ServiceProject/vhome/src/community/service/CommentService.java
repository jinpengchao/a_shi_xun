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
import entity.Comment;

/**
 * @ClassName: CommentService
 * @Description: TODO
 * @author wzw
 * @date 2019年11月27日
 *
 */
public class CommentService {

	/**
	 * 
	 *  @title:saveComment
	 * @Description: 保存
	 * @throws下午9:12:15
	 * returntype:int
	 */
	public int saveComment(Comment comment) {
		return (new CommentDao()).insertComment(comment);
	}
	
	/**
	 * 
	 *  @title:findCommet
	 * @Description: 查询
	 * @throws下午9:13:11
	 * returntype:List<Comment>
	 */
	public List<Comment> findCommet(){
		return (new CommentDao()).queryComment();
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
