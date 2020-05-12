/**
 * @Title:GoodPostService.java
 * @Packagecommunity.service
 * @Description: TODO
 * @auther wzw
 * @date 2019年12月6日
 * @version v1.0
 */
package com.community.service;

import java.util.List;

import com.community.dao.GoodPostDao;
import com.entity.GoodPostBean;


/**
 * @ClassName: GoodPostService
 * @Description: TODO
 * @author wzw
 * @date 2019年12月6日
 *
 */
public class GoodPostService {

	/**
	 * 
	 *  @title:saveGoodPost
	 * @Description: 保存信息
	 * @throws下午8:24:55
	 * returntype:int
	 */
	public int saveGoodPost(GoodPostBean goodPost) {
		return (new GoodPostDao()).insertGoodPost(goodPost);
	}
	/**
	 * 
	 *  @title:findGoodPost
	 * @Description: 通过帖子id查询点赞信息
	 * @throws下午8:26:27
	 * returntype:List<GoodPostBean>
	 */
	public List<GoodPostBean> findGoodPost(int postId){
		return (new GoodPostDao()).queryGoodPosts(postId);
	
	}
	//通过点赞人id查询
	public List<GoodPostBean> findGoodPost(String personId){
		return (new GoodPostDao()).queryGoodPosts(personId);
	
	}
	//通过发布人id查找点赞人id
	public List<String> findGoodPersonId(String publishPersonId){
		return (new GoodPostDao()).queryGoodPerson(publishPersonId);
	}
	
	//通过帖子id和点赞人id查询一条点赞信息
	public GoodPostBean findGoodPost(String personId,int postId) {
		return (new GoodPostDao()).queryGoodPosts(personId, postId);
	}
	/**
	 * 通过postId查询数量
	 *  @title:findGoodPostCount
	 * @Description: todo
	 * @throws下午8:45:14
	 * returntype:int
	 */
	public int findGoodPostCount(int postId) {
		return (new GoodPostDao()).queryGoodPostCount(postId);
	}
	//通过id删除点赞信息
	public int removeGoodPost(int id) {
		return (new GoodPostDao()).delGoodPost(id);
	}
	//通过点赞人id和帖子id删除点赞信息
	public int removeGoodPost(String goodPersonId,int postId) {
		return (new GoodPostDao()).delGoodPost(goodPersonId,postId);
	}
}
