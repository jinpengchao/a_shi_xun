/**
 * @Title:PostService.java
 * @Packagecommunity.service
 * @Description: TODO
 * @auther HP
 * @date 2019年11月26日
 * @version v1.0
 */
package community.service;

import java.util.List;

import community.dao.PostDao;
import community.dao.PostExamineDao;
import entity.PostBean;
import entity.PostExamineBean;

/**
 * @ClassName: PostService
 * @Description: TODO
 * @author wzw
 * @date 2019年11月26日
 *
 */
public class PostService {
	/**
	 * @author:章鹏
	 *  @title:findByExamine
	 * @Description: 根据是否评论查找帖子
	 * @throws上午11:03:02
	 * returntype:int
	 */
	public List<PostExamineBean> findBeansByExamine(String examineString){
		return (new PostExamineDao()).findBeansByExamine(examineString);
	}
	//查找私人所有收到举报的帖子
	public List<PostBean> findAll(){
			return (new PostExamineDao()).findAll();
	}
	//根据Id删除收到举报的帖子
	public int delPostReport(int id) {
		return (new PostExamineDao()).delPostByReport(id);
	}
	//根据插入举报的帖子
	public long insertPostReport(PostBean postBean) {
		return (new PostExamineDao()).insertPost1(postBean);
	}
	//查找私人所有审核的帖子
	public List<PostExamineBean> findAll(String personId){
		return (new PostExamineDao()).findAllByPersonId(personId);
	}
	//通过id修改审核情况
	public void changeExamineByPId(String personId,String examineString) {
		PostExamineDao postExamineDao=new PostExamineDao();
		postExamineDao.changeExamineByPId(personId, examineString);
	}
	//通过id删除已审核或审核失败内容
	public int delPost1(int id) {
		return (new PostExamineDao()).delPost(id);
	}
	//通过个人id修改头像
	public int changImgById(String logo,String personId) {
		return (new PostDao()).changeImgByPId(logo, personId);
	}
	//通过个人id修改昵称
	public int changeNameById(String nickName,String personId) {
		return (new PostDao()).changeNameByPId(personId, nickName);
	}
	/**
	 * 
	 *  @title:savePost
	 * @Description: 保存帖子的service
	 * @throws上午10:57:22
	 * returntype:long
	 */
	public long savePost(PostBean post) {
		return (new PostDao()).insertPost(post);
	}
	/**
	 * 
	 *  @title:savePost
	 * @Description: 保存帖子的service
	 * @throws上午10:57:22
	 * returntype:long
	 */
	public long savePost1(PostExamineBean post) {
		return (new PostExamineDao()).insertPost(post);
	}
	/**
	 * 
	 *  @title:findPost
	 * @Description: 查询所有帖子的service
	 * @throws上午11:01:40
	 * returntype:List<Post>
	 */
	public List<PostBean> findPost(){
		return (new PostDao()).queryPosts();
	}
	//通过id查询单个帖子
	public PostBean findPost(int id) {
		return (new PostDao()).queryPosts(id);
	}
	/**
	 * 根据发帖人查询帖子
	 *  @title:findPost
	 * @Description: todo
	 * @throws下午4:48:38
	 * returntype:List<PostBean>
	 */
	public List<PostBean> findPost(String personId){
		return (new PostDao()).queryPosts(personId);
	}
	/**
	 * 
	 *  @title:delPost
	 * @Description: 根据id删除帖子的service
	 * @throws上午11:03:02
	 * returntype:int
	 */
	public int delPost(int id) {
		return (new PostDao()).delPost(id);
	}
	public int delPost2(int id) {
		return (new PostDao()).delPost1(id);
	}
	
}
