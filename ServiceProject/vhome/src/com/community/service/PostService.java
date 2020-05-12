/**
 * @Title:PostService.java
 * @Packagecommunity.service
 * @Description: TODO
 * @auther HP
 * @date 2019年11月26日
 * @version v1.0
 */
package com.community.service;

import java.util.List;

import com.community.dao.PostDao;
import com.entity.PostBean;


/**
 * @ClassName: PostService
 * @Description: TODO
 * @author wzw
 * @date 2019年11月26日
 *
 */
public class PostService {
	
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
	
	
}
