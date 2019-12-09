/**
 * @Title:GoodPostService.java
 * @Packagecommunity.service
 * @Description: TODO
 * @auther wzw
 * @date 2019年12月6日
 * @version v1.0
 */
package community.service;

import java.util.List;

import community.dao.GoodPostDao;
import entity.GoodPostBean;

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
}
